package com.maiphong.shopmaster.dtos.category;

import com.maiphong.shopmaster.dtos.BaseDTO;

import lombok.*;

@Getter
@Setter
public class CategoryDTO extends BaseDTO {
    private String name;

    private String description;
}
