package com.maiphong.shopmaster.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maiphong.shopmaster.dtos.category.CategoryCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.category.CategoryMasterDTO;

public interface ICategoryService {

    List<CategoryMasterDTO> getAll();

    CategoryMasterDTO getById(UUID id);

    List<CategoryMasterDTO> searchCategory(String name);

    Page<CategoryMasterDTO> searchPaginated(String name, Pageable pageable);

    CategoryMasterDTO create(CategoryCreateUpdateDTO categoryDTO);

    CategoryMasterDTO update(UUID id, CategoryCreateUpdateDTO categoryDTO);

    boolean delete(UUID id);
}
