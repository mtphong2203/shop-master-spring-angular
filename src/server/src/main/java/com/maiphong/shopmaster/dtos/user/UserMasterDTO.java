package com.maiphong.shopmaster.dtos.user;

import java.util.Set;

import com.maiphong.shopmaster.dtos.MasterDTO;
import com.maiphong.shopmaster.models.Role;

import lombok.*;

@Getter
@Setter
public class UserMasterDTO extends MasterDTO {
    private String account;

    private String email;

    private String phoneNumber;

    private String address;

    private Set<Role> roles;
}
