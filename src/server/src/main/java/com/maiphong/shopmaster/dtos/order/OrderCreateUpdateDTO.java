package com.maiphong.shopmaster.dtos.order;

import com.maiphong.shopmaster.models.OrderStatus;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
public class OrderCreateUpdateDTO {

    @NotNull(message = "Order status is required")
    private OrderStatus orderStatus;

    @PositiveOrZero(message = "Amount greater or equal zero")
    private double totalAmount;

}
