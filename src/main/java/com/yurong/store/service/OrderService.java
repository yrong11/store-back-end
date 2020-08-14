package com.yurong.store.service;

import com.yurong.store.domain.Order;
import com.yurong.store.dto.OrderDto;
import com.yurong.store.dto.ProductDto;
import com.yurong.store.repository.OrderRepository;
import com.yurong.store.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {
    final OrderRepository orderRepository;
    final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository){
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public boolean addOrder(Order order){
        Integer proId = new Integer(order.getProId());
        if(productRepository.findById(proId).isPresent()){
            OrderDto orderDto = orderToDto(order);
            orderRepository.save(orderDto);
            return true;
        }

        return false;
    }

    public Order dtoToOrder(OrderDto orderDto){
        return Order.builder()
                .number(orderDto.getNumber())
                .proId(orderDto.getProId())
                .proName(orderDto.getProName())
                .proPrice(orderDto.getProPrice())
                .proUnit(orderDto.getProUnit())
                .build();
    }

    public OrderDto orderToDto(Order order){
        return OrderDto.builder().number(order.getNumber())
                .proId(order.getProId())
                .proName(order.getProName())
                .proPrice(order.getProPrice())
                .proUnit(order.getProUnit())
                .build();

    }
}
