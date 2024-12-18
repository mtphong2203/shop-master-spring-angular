package com.maiphong.shopmaster.dtos.user;

import com.maiphong.shopmaster.dtos.MasterDTO;

import lombok.*;

@Getter
@Setter
public class UserMasterDTO extends MasterDTO {
    private String account;

    private String email;

    private String phoneNumber;

    private String address;
}
