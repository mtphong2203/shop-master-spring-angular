package com.maiphong.shopmaster.services;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.shopmaster.dtos.role.RoleCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.role.RoleMasterDTO;
import com.maiphong.shopmaster.exceptions.ResourceNotFoundException;
import com.maiphong.shopmaster.mappers.RoleMapper;
import com.maiphong.shopmaster.models.Role;
import com.maiphong.shopmaster.repositories.RoleRepository;

@Service
@Transactional
public class RoleService implements IRoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleService(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleMasterDTO> getAll() {
        // Get all role
        List<Role> categories = roleRepository.findAll();

        // convert all to dto
        List<RoleMasterDTO> roleMasters = categories.stream().map(role -> {
            RoleMasterDTO roleMaster = roleMapper.toMasterDTO(role);
            return roleMaster;
        }).toList();

        return roleMasters;
    }

    @Override
    public RoleMasterDTO getById(UUID id) {
        Role role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            throw new ResourceNotFoundException("Role is not found");
        }
        RoleMasterDTO roleMaster = roleMapper.toMasterDTO(role);
        return roleMaster;
    }

    @Override
    public List<RoleMasterDTO> searchRole(String keyword) {
        Specification<Role> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.or(
                    cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"),
                    cb.like(root.get("description"), "%" + keyword + "%"));
        };

        List<Role> roles = roleRepository.findAll(spec);

        List<RoleMasterDTO> roleMasters = roles.stream().map(role -> {
            RoleMasterDTO roleMaster = roleMapper.toMasterDTO(role);
            return roleMaster;
        }).toList();

        return roleMasters;
    }

    @Override
    public Page<RoleMasterDTO> searchPaginated(String keyword, Pageable pageable) {
        Specification<Role> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.or(
                    cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"),
                    cb.like(root.get("description"), "%" + keyword + "%"));
        };

        Page<Role> roles = roleRepository.findAll(spec, pageable);

        Page<RoleMasterDTO> roleMasters = roles.map(role -> {
            RoleMasterDTO roleMaster = roleMapper.toMasterDTO(role);
            return roleMaster;
        });

        return roleMasters;
    }

    @Override
    public RoleMasterDTO create(RoleCreateUpdateDTO roleDTO) {
        if (roleDTO == null) {
            throw new IllegalArgumentException("Role is required");
        }

        Role existRole = roleRepository.findByName(roleDTO.getName());

        if (existRole != null) {
            throw new IllegalArgumentException("Role name is already exist!");
        }

        Role newRole = roleMapper.toEntity(roleDTO);

        newRole = roleRepository.save(newRole);

        return roleMapper.toMasterDTO(newRole);
    }

    @Override
    public RoleMasterDTO update(UUID id, RoleCreateUpdateDTO roleDTO) {
        if (roleDTO == null) {
            throw new IllegalArgumentException("Role is required");
        }

        Role role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            throw new ResourceNotFoundException("Role is not exist!");
        }

        role = roleMapper.toEntity(roleDTO, role);
        role.setUpdatedAt(ZonedDateTime.now());

        role = roleRepository.save(role);

        return roleMapper.toMasterDTO(role);
    }

    @Override
    public boolean delete(UUID id) {
        Role role = roleRepository.findById(id).orElse(null);

        if (role == null) {
            throw new ResourceNotFoundException("Role is not exist!");
        }
        roleRepository.delete(role);

        return !roleRepository.existsById(id);
    }

}
