package com.maiphong.shopmaster.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product extends MasterModel {
    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "NVARCHAR(500)")
    private String description;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private double quantity;

    private String brand;

    private String thumbnailUrl;

}
