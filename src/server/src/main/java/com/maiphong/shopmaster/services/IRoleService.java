package com.maiphong.shopmaster.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maiphong.shopmaster.dtos.role.RoleCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.role.RoleMasterDTO;

public interface IRoleService {

    List<RoleMasterDTO> getAll();

    RoleMasterDTO getById(UUID id);

    List<RoleMasterDTO> searchRole(String name);

    Page<RoleMasterDTO> searchPaginated(String name, Pageable pageable);

    RoleMasterDTO create(RoleCreateUpdateDTO roleDTO);

    RoleMasterDTO update(UUID id, RoleCreateUpdateDTO roleDTO);

    boolean delete(UUID id);
}
