package com.maiphong.shopmaster.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.maiphong.shopmaster.dtos.promotion.PromotionCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.promotion.PromotionMasterDTO;
import com.maiphong.shopmaster.models.Promotion;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PromotionMapper {

    Promotion toEntity(PromotionCreateUpdateDTO DTO);

    Promotion toEntity(PromotionCreateUpdateDTO DTO, @MappingTarget Promotion promotion);

    PromotionMasterDTO toMasterDTO(Promotion promotion);

}
