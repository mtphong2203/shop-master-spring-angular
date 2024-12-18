package com.maiphong.shopmaster.services;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.shopmaster.dtos.order.OrderCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.order.OrderMasterDTO;
import com.maiphong.shopmaster.exceptions.ResourceNotFoundException;
import com.maiphong.shopmaster.mappers.OrderMapper;
import com.maiphong.shopmaster.models.Order;
import com.maiphong.shopmaster.models.OrderStatus;
import com.maiphong.shopmaster.repositories.OrderRepository;

@Service
@Transactional
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public List<OrderMasterDTO> getAll() {
        // Get all order
        List<Order> categories = orderRepository.findAll();

        // convert all to dto
        List<OrderMasterDTO> orderMasters = categories.stream().map(order -> {
            OrderMasterDTO orderMaster = orderMapper.toMasterDTO(order);
            return orderMaster;
        }).toList();

        return orderMasters;
    }

    @Override
    public OrderMasterDTO getById(UUID id) {
        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            throw new ResourceNotFoundException("Order is not found");
        }
        OrderMasterDTO orderMaster = orderMapper.toMasterDTO(order);
        return orderMaster;
    }

    @Override
    public Page<OrderMasterDTO> searchPaginated(OrderStatus orderStatus, Pageable pageable) {
        Specification<Order> spec = (root, _, cb) -> {
            if (orderStatus == null) {
                return null;
            }

            return cb.equal(root.get("orderStatus"), orderStatus);
        };

        Page<Order> categories = orderRepository.findAll(spec, pageable);

        Page<OrderMasterDTO> orderMasters = categories.map(order -> {
            OrderMasterDTO orderMaster = orderMapper.toMasterDTO(order);
            return orderMaster;
        });

        return orderMasters;
    }

    @Override
    public OrderMasterDTO create(OrderCreateUpdateDTO orderDTO) {
        if (orderDTO == null) {
            throw new IllegalArgumentException("Order is required");
        }

        Order newOrder = orderMapper.toEntity(orderDTO);

        newOrder = orderRepository.save(newOrder);

        return orderMapper.toMasterDTO(newOrder);
    }

    @Override
    public OrderMasterDTO update(UUID id, OrderCreateUpdateDTO orderDTO) {
        if (orderDTO == null) {
            throw new IllegalArgumentException("Order is required");
        }

        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            throw new ResourceNotFoundException("Order is not exist!");
        }

        order = orderMapper.toEntity(orderDTO, order);
        order.setUpdatedAt(ZonedDateTime.now());

        order = orderRepository.save(order);

        return orderMapper.toMasterDTO(order);
    }

    @Override
    public boolean delete(UUID id) {
        Order order = orderRepository.findById(id).orElse(null);

        if (order == null) {
            throw new ResourceNotFoundException("Order is not exist!");
        }
        orderRepository.delete(order);

        return !orderRepository.existsById(id);
    }

}
