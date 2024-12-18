package com.maiphong.shopmaster.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.maiphong.shopmaster.models.Role;

public interface RoleRepository extends JpaRepository<Role, UUID>, JpaSpecificationExecutor<Role> {
    Role findByName(String name);
}
