package com.maiphong.shopmaster.dtos.product;

import com.maiphong.shopmaster.dtos.MasterDTO;

import lombok.*;

@Getter
@Setter
public class ProductMasterDTO extends MasterDTO {
    private String name;

    private String description;

    private double price;

    private double quantity;

    private String brand;

    private String thumbnailUrl;
}
