package com.maiphong.shopmaster.dtos.user;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class UserCreateUpdateDTO {

    @NotNull(message = "Account is required")
    @Length(max = 50, message = "Maximum is 50 characters")
    private String account;

    @NotNull(message = "Email is required")
    @Length(max = 50, message = "Maximum is 50 characters")
    private String email;

    @NotNull(message = "Phone is required")
    @Length(max = 50, message = "Maximum is 50 characters")
    private String phoneNumber;

    @Length(max = 50, message = "Maximum is 50 characters")
    private String address;

    private String thumbnailUrl;

    @NotNull(message = "Password is required")
    private String password;

    @NotNull(message = "Confirm password")
    private String confirmPassword;

    private boolean isActive;

}
