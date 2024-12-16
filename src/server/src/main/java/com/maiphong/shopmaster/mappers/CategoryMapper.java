package com.maiphong.shopmaster.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.maiphong.shopmaster.dtos.category.CategoryCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.category.CategoryMasterDTO;
import com.maiphong.shopmaster.models.Category;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CategoryMapper {
    Category toEntity(CategoryCreateUpdateDTO categoryDTO);

    Category toEntity(CategoryCreateUpdateDTO categoryDTO, @MappingTarget Category category);

    CategoryMasterDTO toMasterDTO(Category category);

}
