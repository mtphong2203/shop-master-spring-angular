package com.maiphong.shopmaster.models;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "User_Role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}
