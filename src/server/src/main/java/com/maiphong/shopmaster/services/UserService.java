package com.maiphong.shopmaster.services;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.shopmaster.dtos.user.UserCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.user.UserMasterDTO;
import com.maiphong.shopmaster.exceptions.ResourceNotFoundException;
import com.maiphong.shopmaster.mappers.UserMapper;
import com.maiphong.shopmaster.models.User;
import com.maiphong.shopmaster.repositories.UserRepository;

@Service
@Transactional
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserMasterDTO> getAll() {
        // Get all user
        List<User> categories = userRepository.findAll();

        // convert all to dto
        List<UserMasterDTO> userMasters = categories.stream().map(user -> {
            UserMasterDTO userMaster = userMapper.toMasterDTO(user);
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

        newUser = userRepository.save(newUser);

        return userMapper.toMasterDTO(newUser);
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

        user = userRepository.save(user);

        return userMapper.toMasterDTO(user);
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
