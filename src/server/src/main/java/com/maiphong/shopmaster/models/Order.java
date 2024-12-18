package com.maiphong.shopmaster.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends MasterModel {

    @Column(nullable = false)
    private OrderStatus orderStatus;

    @Column(nullable = false)
    private double totalAmount;

}
