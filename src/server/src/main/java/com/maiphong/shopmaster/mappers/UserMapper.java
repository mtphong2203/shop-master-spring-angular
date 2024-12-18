package com.maiphong.shopmaster.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.maiphong.shopmaster.dtos.user.UserCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.user.UserMasterDTO;
import com.maiphong.shopmaster.models.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {

    User toEntity(UserCreateUpdateDTO DTO);

    User toEntity(UserCreateUpdateDTO DTO, @MappingTarget User user);

    UserMasterDTO toMasterDTO(User user);

}
