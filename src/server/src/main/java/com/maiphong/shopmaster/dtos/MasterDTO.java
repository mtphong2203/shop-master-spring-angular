package com.maiphong.shopmaster.dtos;

import java.time.ZonedDateTime;

import lombok.*;

@Getter
@Setter
public class MasterDTO extends BaseDTO {

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    private ZonedDateTime deletedAt;

    private boolean isActive;
}
