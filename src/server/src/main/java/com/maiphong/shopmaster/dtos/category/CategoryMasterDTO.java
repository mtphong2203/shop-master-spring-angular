package com.maiphong.shopmaster.dtos.category;

import com.maiphong.shopmaster.dtos.MasterDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryMasterDTO extends MasterDTO {
    private String name;

    private String description;
}
