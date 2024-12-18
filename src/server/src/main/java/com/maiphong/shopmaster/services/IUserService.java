package com.maiphong.shopmaster.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maiphong.shopmaster.dtos.user.UserCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.user.UserMasterDTO;

public interface IUserService {
    List<UserMasterDTO> getAll();

    UserMasterDTO getById(UUID id);

    List<UserMasterDTO> searchUser(String account);

    Page<UserMasterDTO> searchPaginated(String account, Pageable pageable);

    UserMasterDTO create(UserCreateUpdateDTO userDTO);

    UserMasterDTO update(UUID id, UserCreateUpdateDTO userDTO);

    boolean delete(UUID id);
}
