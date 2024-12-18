package com.maiphong.shopmaster.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.maiphong.shopmaster.dtos.order.OrderCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.order.OrderMasterDTO;
import com.maiphong.shopmaster.models.Order;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {

    Order toEntity(OrderCreateUpdateDTO DTO);

    Order toEntity(OrderCreateUpdateDTO DTO, @MappingTarget Order order);

    OrderMasterDTO toMasterDTO(Order order);

}
