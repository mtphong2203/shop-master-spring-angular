package com.maiphong.shopmaster.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.maiphong.shopmaster.dtos.role.RoleCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.role.RoleMasterDTO;
import com.maiphong.shopmaster.models.Role;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    Role toEntity(RoleCreateUpdateDTO DTO);

    Role toEntity(RoleCreateUpdateDTO DTO, @MappingTarget Role role);

    RoleMasterDTO toMasterDTO(Role role);
}
