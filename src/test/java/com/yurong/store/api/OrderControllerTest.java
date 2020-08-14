package com.yurong.store.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yurong.store.domain.Order;
import com.yurong.store.domain.Product;
import com.yurong.store.dto.OrderDto;
import com.yurong.store.dto.ProductDto;
import com.yurong.store.repository.OrderRepository;
import com.yurong.store.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    private Order order;
    private OrderDto orderDto;
    private ProductDto productDto;
    private Product product;

    @BeforeEach
    void setup(){
        orderRepository.deleteAll();
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

    public int addOrder() {
        OrderDto orderDto = OrderDto.builder().proId(productDto.getId())
                .proUnit(productDto.getUnit())
                .proPrice(productDto.getPrice())
                .proName(productDto.getName())
                .number(2)
                .build();
        orderRepository.save(orderDto);
        return orderDto.getId();
    }

    @Test
    public void should_add_order_when_product_exisit() throws Exception {
        int proId = addProductData();
        Order order = Order.builder().proId(proId)
                .proUnit(productDto.getUnit())
                .proPrice(productDto.getPrice())
                .proName(productDto.getName())
                .number(2)
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(order);
        mockMvc.perform(post("/order").content(jsonString).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        List<OrderDto> orderDtos = orderRepository.findAll();
        assertEquals( 1 ,orderDtos.size());

    }

    @Test
    public void get_order_list() throws Exception {
        addOrder();
        addOrder();
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)));

    }

    @Test
    public void delete_order() throws Exception {
        addOrder();
        int id = addOrder();
        mockMvc.perform(delete("/order/"+id))
                .andExpect(status().isOk());
        List<OrderDto> orderDtos = orderRepository.findAll();
        assertEquals( 1 ,orderDtos.size());

    }

}