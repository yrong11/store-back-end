package com.yurong.store.service;

import com.yurong.store.domain.Product;
import com.yurong.store.dto.ProductDto;
import com.yurong.store.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StoreService {
    final ProductRepository productRepository;

    public StoreService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public List<Product> getProductList(Integer page, Integer size) {
        List<ProductDto> products;
        if (page != null && size != null && page > 0 && size > 0){
            Pageable pageable = PageRequest.of(page - 1, size);
            products = productRepository.findAll(pageable).getContent();
        }else
            products = productRepository.findAll();

        return products.stream().map(
                item -> Product.builder().name(item.getName()).price(item.getPrice())
                        .unit(item.getUnit()).picURL(item.getPicURL()).build()).collect(Collectors.toList());

    }

    public void addProduct(Product product){
        ProductDto productDto = proToDto(product);
        productRepository.save(productDto);
    }


    public Product dtoToPro(ProductDto productDto){
        return Product.builder().price(productDto.getPrice())
                .name(productDto.getName())
                .picURL(productDto.getPicURL())
                .unit(productDto.getUnit()).build();
    }

    public ProductDto proToDto(Product product){
        return ProductDto.builder().name(product.getName()).price(product.getPrice())
                .unit(product.getUnit()).picURL(product.getPicURL()).build();
    }
}
