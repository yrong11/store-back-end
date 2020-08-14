package com.yurong.store.repository;

import com.yurong.store.dto.OrderDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface OrderRepository extends PagingAndSortingRepository<OrderDto, Integer> {

    @Override
    List<OrderDto> findAll();

    @Override
    Page<OrderDto> findAll(Pageable pageable);
}
