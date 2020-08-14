package com.yurong.store.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yurong.store.domain.Product;
import com.yurong.store.dto.ProductDto;
import com.yurong.store.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StoreControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    private ProductDto productDto;
    private Product product;


    @BeforeEach
    void setup() {
        productRepository.deleteAll();
        product = Product.builder().name("可乐").unit("瓶").picURL("xxx").price(2.5f).build();
        addProductData();

    }

    public int addProductData(){
        productDto = ProductDto.builder().name(product.getName())
                .picURL(product.getPicURL()).price(product.getPrice()).unit(product.getUnit()).build();
        productRepository.save(productDto);
        return productDto.getId();
    }

    @Test
    public void should_return_json_when_get_products() throws Exception {
        addProductData();
        mockMvc.perform(get("/store"))
                .andExpect(status().isOk());
    }

    @Test
    public void should_return_json_when_have_page_and_size() throws Exception {
        addProductData();
        mockMvc.perform(get("/store").param("page", "1").param("size","1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    public void add_product() throws Exception {
        product.setName("雪碧");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(product);
        mockMvc.perform(post("/product").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        List<ProductDto> productDtos = productRepository.findAll();
        assertEquals( 2 ,productDtos.size());

    }

    @Test
    public void should_bad_request_when_product_invalid() throws Exception {
        product.setName(null);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(product);
        mockMvc.perform(post("/product").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}