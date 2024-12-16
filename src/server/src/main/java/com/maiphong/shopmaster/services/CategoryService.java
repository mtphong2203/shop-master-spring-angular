package com.maiphong.shopmaster.services;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.shopmaster.dtos.category.CategoryCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.category.CategoryMasterDTO;
import com.maiphong.shopmaster.exceptions.ResourceNotFoundException;
import com.maiphong.shopmaster.mappers.CategoryMapper;
import com.maiphong.shopmaster.models.Category;
import com.maiphong.shopmaster.repositories.CategoryRepository;

@Service
@Transactional
public class CategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryMasterDTO> getAll() {
        // Get all category
        List<Category> categories = categoryRepository.findAll();

        // convert all to dto
        List<CategoryMasterDTO> categoryMasters = categories.stream().map(category -> {
            CategoryMasterDTO categoryMaster = categoryMapper.toMasterDTO(category);
            return categoryMaster;
        }).toList();

        return categoryMasters;
    }

    @Override
    public CategoryMasterDTO getById(UUID id) {
        Category category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            throw new ResourceNotFoundException("Category is not found");
        }
        CategoryMasterDTO categoryMaster = categoryMapper.toMasterDTO(category);
        return categoryMaster;
    }

    @Override
    public List<CategoryMasterDTO> searchCategory(String keyword) {
        Specification<Category> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.or(
                    cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"),
                    cb.like(root.get("description"), "%" + keyword + "%"));
        };

        List<Category> categories = categoryRepository.findAll(spec);

        List<CategoryMasterDTO> categoryMasters = categories.stream().map(category -> {
            CategoryMasterDTO categoryMaster = categoryMapper.toMasterDTO(category);
            return categoryMaster;
        }).toList();

        return categoryMasters;
    }

    @Override
    public Page<CategoryMasterDTO> searchPaginated(String keyword, Pageable pageable) {
        Specification<Category> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.or(
                    cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"),
                    cb.like(root.get("description"), "%" + keyword + "%"));
        };

        Page<Category> categories = categoryRepository.findAll(spec, pageable);

        Page<CategoryMasterDTO> categoryMasters = categories.map(category -> {
            CategoryMasterDTO categoryMaster = categoryMapper.toMasterDTO(category);
            return categoryMaster;
        });

        return categoryMasters;
    }

    @Override
    public CategoryMasterDTO create(CategoryCreateUpdateDTO categoryDTO) {
        if (categoryDTO == null) {
            throw new IllegalArgumentException("Category is required");
        }

        Category existCategory = categoryRepository.findByName(categoryDTO.getName());

        if (existCategory != null) {
            throw new IllegalArgumentException("Category name is already exist!");
        }

        Category newCategory = categoryMapper.toEntity(categoryDTO);

        newCategory = categoryRepository.save(newCategory);

        return categoryMapper.toMasterDTO(newCategory);
    }

    @Override
    public CategoryMasterDTO update(UUID id, CategoryCreateUpdateDTO categoryDTO) {
        if (categoryDTO == null) {
            throw new IllegalArgumentException("Category is required");
        }

        Category category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            throw new ResourceNotFoundException("Category is not exist!");
        }

        category = categoryMapper.toEntity(categoryDTO, category);
        category.setUpdatedAt(ZonedDateTime.now());

        category = categoryRepository.save(category);

        return categoryMapper.toMasterDTO(category);
    }

    @Override
    public boolean delete(UUID id) {
        Category category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            throw new ResourceNotFoundException("Category is not exist!");
        }
        categoryRepository.delete(category);

        return !categoryRepository.existsById(id);
    }

}
