package com.maiphong.shopmaster.dtos.role;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleCreateUpdateDTO {
    @NotNull(message = "Name is required")
    @Length(max = 50, message = "Maximum is 50 characters")
    private String name;

    @Length(max = 100, message = "Maximum is 100 characters")
    private String description;

    @NotNull(message = "Active is required")
    private boolean isActive;
}
