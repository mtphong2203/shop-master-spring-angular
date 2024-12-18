package com.maiphong.shopmaster.dtos.role;

import com.maiphong.shopmaster.dtos.MasterDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleMasterDTO extends MasterDTO {

    private String name;

    private String description;
}
