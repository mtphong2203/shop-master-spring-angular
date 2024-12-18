package com.maiphong.shopmaster.dtos.order;

import com.maiphong.shopmaster.dtos.MasterDTO;
import com.maiphong.shopmaster.models.OrderStatus;

import lombok.*;

@Getter
@Setter
public class OrderMasterDTO extends MasterDTO {

    private OrderStatus orderStatus;

    private double totalAmount;

}
