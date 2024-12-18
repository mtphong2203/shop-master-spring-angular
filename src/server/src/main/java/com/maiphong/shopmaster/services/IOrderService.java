package com.maiphong.shopmaster.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maiphong.shopmaster.dtos.order.OrderCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.order.OrderMasterDTO;
import com.maiphong.shopmaster.models.OrderStatus;

public interface IOrderService {

    List<OrderMasterDTO> getAll();

    OrderMasterDTO getById(UUID id);

    Page<OrderMasterDTO> searchPaginated(OrderStatus orderStatus, Pageable pageable);

    OrderMasterDTO create(OrderCreateUpdateDTO orderDTO);

    OrderMasterDTO update(UUID id, OrderCreateUpdateDTO orderDTO);

    boolean delete(UUID id);
}
