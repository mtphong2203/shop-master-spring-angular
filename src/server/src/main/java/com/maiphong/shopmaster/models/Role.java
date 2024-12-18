package com.maiphong.shopmaster.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role extends MasterModel {

    @Column(nullable = false, unique = true, columnDefinition = "NVARCHAR(50)")
    private String name;

    @Column(columnDefinition = "NVARCHAR(100)")
    private String description;
}
