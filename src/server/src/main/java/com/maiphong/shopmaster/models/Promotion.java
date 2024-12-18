package com.maiphong.shopmaster.models;

import java.time.ZonedDateTime;

import org.hibernate.annotations.TimeZoneStorage;
import org.hibernate.annotations.TimeZoneStorageType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "promotions")
public class Promotion extends MasterModel {
    @Column(columnDefinition = "NVARCHAR(50)")
    private String code;

    @Column(nullable = false)
    private double discountAmount;

    @TimeZoneStorage(TimeZoneStorageType.NATIVE)
    @Column(columnDefinition = "DATETIMEOFFSET")
    private ZonedDateTime endDate;

}
