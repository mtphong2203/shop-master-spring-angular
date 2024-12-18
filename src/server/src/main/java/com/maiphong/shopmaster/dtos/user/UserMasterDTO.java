package com.maiphong.shopmaster.dtos.user;

import java.util.Set;

import com.maiphong.shopmaster.dtos.MasterDTO;
import lombok.*;

@Getter
@Setter
public class UserMasterDTO extends MasterDTO {
    private String account;

    private String email;

    private String phoneNumber;

    private String address;

    private Set<String> role;
}
