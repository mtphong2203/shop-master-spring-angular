package com.maiphong.shopmaster.dtos.product;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
public class ProductCreateUpdateDTO {
    @NotNull(message = "Name is required")
    @Length(min = 2, max = 50, message = "Maximum is between 2 to 50 characters")
    private String name;

    @Length(max = 500, message = "Maximum is 500 characters")
    private String description;

    @PositiveOrZero(message = "Price greater or equal to zero")
    private double price;

    @PositiveOrZero(message = "Quantity greater or equal to zero")
    private double quantity;

    @Length(max = 50, message = "Maximum is 50 characters")
    private String brand;

    private String thumbnailUrl;

    private boolean isActive;
}
