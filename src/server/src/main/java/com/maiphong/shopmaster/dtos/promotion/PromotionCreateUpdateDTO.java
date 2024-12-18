package com.maiphong.shopmaster.dtos.promotion;

import java.time.ZonedDateTime;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Getter
@Setter
public class PromotionCreateUpdateDTO {
    @Length(max = 50, message = "Maximum is 50 characters")
    private String code;

    @PositiveOrZero(message = "Greater or equal to zero")
    private double discountAmount;

    @NotNull(message = "End date is required")
    private ZonedDateTime endDate;

    @NotNull(message = "Active is required")
    private boolean isActive;
}
