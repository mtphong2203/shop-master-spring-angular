package com.maiphong.shopmaster.dtos.category;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryCreateUpdateDTO {

    @NotNull(message = "Name is required")
    @Length(min = 3, max = 50, message = "Maximum is between 3 to 50 characters")
    private String name;

    @Length(max = 500, message = "Maximum is 500 characters")
    private String description;

    @NotNull(message = "Active is required")
    private boolean isActive;
}
