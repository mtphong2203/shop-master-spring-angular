package com.maiphong.shopmaster.services;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.shopmaster.dtos.user.UserCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.user.UserMasterDTO;
import com.maiphong.shopmaster.exceptions.ResourceNotFoundException;
import com.maiphong.shopmaster.mappers.UserMapper;
import com.maiphong.shopmaster.models.Role;
import com.maiphong.shopmaster.models.User;
import com.maiphong.shopmaster.repositories.RoleRepository;
import com.maiphong.shopmaster.repositories.UserRepository;

@Service
@Transactional
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserMasterDTO> getAll() {
        // Get all user
        List<User> categories = userRepository.findAll();

        // convert all to dto
        List<UserMasterDTO> userMasters = categories.stream().map(user -> {
            UserMasterDTO userMaster = userMapper.toMasterDTO(user);
            if (user.getRoles() != null) {
                userMaster.setRole(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
            }
            return userMaster;
        }).toList();

        return userMasters;
    }

    @Override
    public UserMasterDTO getById(UUID id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User is not found");
        }
        UserMasterDTO userMaster = userMapper.toMasterDTO(user);
        if (user.getRoles() != null) {
            userMaster.setRole(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
        }
        return userMaster;
    }

    @Override
    public List<UserMasterDTO> searchUser(String keyword) {
        Specification<User> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.or(
                    cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"),
                    cb.like(root.get("description"), "%" + keyword + "%"));
        };

        List<User> categories = userRepository.findAll(spec);

        List<UserMasterDTO> userMasters = categories.stream().map(user -> {
            UserMasterDTO userMaster = userMapper.toMasterDTO(user);
            if (user.getRoles() != null) {
                userMaster.setRole(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
            }
            return userMaster;
        }).toList();

        return userMasters;
    }

    @Override
    public Page<UserMasterDTO> searchPaginated(String keyword, Pageable pageable) {
        Specification<User> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.or(
                    cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"),
                    cb.like(root.get("description"), "%" + keyword + "%"));
        };

        Page<User> categories = userRepository.findAll(spec, pageable);

        Page<UserMasterDTO> userMasters = categories.map(user -> {
            UserMasterDTO userMaster = userMapper.toMasterDTO(user);
            if (user.getRoles() != null) {
                userMaster.setRole(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));
            }
            return userMaster;
        });

        return userMasters;
    }

    @Override
    public UserMasterDTO create(UserCreateUpdateDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User is required");
        }

        User existUser = userRepository.findByAccount(userDTO.getAccount());

        if (existUser != null) {
            throw new IllegalArgumentException("User name is already exist!");
        }

        User newUser = userMapper.toEntity(userDTO);

        if (userDTO.getRoleId() != null) {
            var roles = roleRepository.findById(userDTO.getRoleId());
            if (roles.isPresent()) {
                newUser.setRoles(Collections.singleton(roles.get()));
            }
        }
        newUser = userRepository.save(newUser);

        UserMasterDTO userMasterDTO = userMapper.toMasterDTO(newUser);

        userMasterDTO.setRole(newUser.getRoles().stream().map(
                role -> role.getName()).collect(Collectors.toSet()));

        return userMasterDTO;
    }

    @Override
    public UserMasterDTO update(UUID id, UserCreateUpdateDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("User is required");
        }

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User is not exist!");
        }

        user = userMapper.toEntity(userDTO, user);
        user.setUpdatedAt(ZonedDateTime.now());

        if (userDTO.getRoleId() != null) {
            Optional<Role> role = roleRepository.findById(userDTO.getRoleId());
            if (role.isPresent()) {
                user.setRoles(Collections.singleton(role.get()));
            }
        }

        user = userRepository.save(user);

        UserMasterDTO userMasterDTO = userMapper.toMasterDTO(user);
        userMasterDTO.setRole(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet()));

        return userMasterDTO;
    }

    @Override
    public boolean delete(UUID id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            throw new ResourceNotFoundException("User is not exist!");
        }
        userRepository.delete(user);

        return !userRepository.existsById(id);
    }

}
