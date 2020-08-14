package com.yurong.store.api;

import com.yurong.store.domain.Order;
import com.yurong.store.repository.OrderRepository;
import com.yurong.store.repository.ProductRepository;
import com.yurong.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrderController {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderService orderService;

    @PostMapping("/order")
    public ResponseEntity addOrder(@RequestBody @Valid Order order){
        boolean flag = orderService.addOrder(order);
        return flag ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

}
