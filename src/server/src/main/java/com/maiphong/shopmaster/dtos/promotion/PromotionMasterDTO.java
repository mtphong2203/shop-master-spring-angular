package com.maiphong.shopmaster.dtos.promotion;

import java.time.ZonedDateTime;

import com.maiphong.shopmaster.dtos.MasterDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PromotionMasterDTO extends MasterDTO {
    private String code;

    private double discountAmount;

    private ZonedDateTime endDate;
}
