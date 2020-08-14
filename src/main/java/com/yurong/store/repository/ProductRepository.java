package com.yurong.store.repository;

import com.yurong.store.domain.Product;
import com.yurong.store.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends PagingAndSortingRepository<ProductDto, Integer> {
    @Override
    List<ProductDto> findAll();

    @Override
    Page<ProductDto> findAll(Pageable pageable);
}
