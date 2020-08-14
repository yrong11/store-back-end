package com.yurong.store.service;

import com.yurong.store.domain.Order;
import com.yurong.store.domain.Product;
import com.yurong.store.dto.OrderDto;
import com.yurong.store.dto.ProductDto;
import com.yurong.store.repository.OrderRepository;
import com.yurong.store.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Order> getOrderList(Integer page, Integer size) {
        List<OrderDto> orderDtos;
        if (page != null && size != null && page > 0 && size > 0){
            Pageable pageable = PageRequest.of(page - 1, size);
            orderDtos = orderRepository.findAll(pageable).getContent();
        }else
            orderDtos = orderRepository.findAll();

        return orderDtos.stream().map(
                item -> dtoToOrder(item)).collect(Collectors.toList());

    }

    public void deleteOrder(int orderId){
        orderRepository.deleteById(orderId);
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
