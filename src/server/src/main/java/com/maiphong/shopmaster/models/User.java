package com.maiphong.shopmaster.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends MasterModel {
    @Column(nullable = false, unique = true, columnDefinition = "NVARCHAR(50)")
    private String account;

    @Column(nullable = false, columnDefinition = "NVARCHAR(50)")
    private String email;

    @Column(nullable = false, columnDefinition = "NVARCHAR(50)")
    private String phoneNumber;

    @Column(nullable = false, columnDefinition = "NVARCHAR(50)")
    private String address;

    private String thumbnailUrl;

    @Column(nullable = false, columnDefinition = "NVARCHAR(50)")
    private String password;

}
