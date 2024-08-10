package com.freecodebucket.service;

import com.freecodebucket.common.dto.OrderRequestDTO;
import com.freecodebucket.common.mapper.OrderDTOtoEntityMapper;
import com.freecodebucket.common.mapper.OrderEntityToOutboxEntityMapper;
import com.freecodebucket.entity.Order;
import com.freecodebucket.entity.Outbox;
import com.freecodebucket.repository.OrderRepository;
import com.freecodebucket.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    OrderDTOtoEntityMapper orderDTOtoEntityMapper;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderEntityToOutboxEntityMapper orderEntityToOutboxEntityMapper;
    @Autowired
    private OutboxRepository outboxRepository;

    @Transactional
    public Order createOrder(OrderRequestDTO orderRequestDTO) {
        Order order = orderDTOtoEntityMapper.map(orderRequestDTO);
        order = orderRepository.save(order);
        Outbox outbox = orderEntityToOutboxEntityMapper.map(order);
        outboxRepository.save(outbox);
        return order;
    }
}
