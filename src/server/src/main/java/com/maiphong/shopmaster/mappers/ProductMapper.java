package com.maiphong.shopmaster.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.maiphong.shopmaster.dtos.product.ProductCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.product.ProductMasterDTO;
import com.maiphong.shopmaster.models.Product;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProductMapper {

    Product toEntity(ProductCreateUpdateDTO productDTO);

    Product toEntity(ProductCreateUpdateDTO productDTO, @MappingTarget Product product);

    ProductMasterDTO toMasterDTO(Product product);

}
