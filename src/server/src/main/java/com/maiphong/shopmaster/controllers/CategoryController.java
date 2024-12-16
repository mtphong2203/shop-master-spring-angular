package com.maiphong.shopmaster.controllers;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.*;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Links;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.maiphong.shopmaster.dtos.category.CategoryCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.category.CategoryMasterDTO;
import com.maiphong.shopmaster.response.CustomPageResponse;
import com.maiphong.shopmaster.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final PagedResourcesAssembler<CategoryMasterDTO> pagedResource;

    public CategoryController(CategoryService categoryService,
            PagedResourcesAssembler<CategoryMasterDTO> pagedResource) {
        this.categoryService = categoryService;
        this.pagedResource = pagedResource;
    }

    @GetMapping
    public ResponseEntity<List<CategoryMasterDTO>> getAllCategory() {
        List<CategoryMasterDTO> categoryMasters = categoryService.getAll();
        return ResponseEntity.ok(categoryMasters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryMasterDTO> getCategoryById(@PathVariable UUID id) {
        CategoryMasterDTO categoryMaster = categoryService.getById(id);
        return ResponseEntity.ok(categoryMaster);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryMasterDTO>> searchCategory(@RequestParam(required = false) String keyword) {
        List<CategoryMasterDTO> categoryMasters = categoryService.searchCategory(keyword);
        return ResponseEntity.ok(categoryMasters);
    }

    @GetMapping("/search-paginated")
    public ResponseEntity<?> searchPainatedCategory(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "name") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String orderBy,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size) {
        Pageable pageable = null;

        if (orderBy.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        Page<CategoryMasterDTO> categoryMasters = categoryService.searchPaginated(keyword, pageable);

        var pageModel = pagedResource.toModel(categoryMasters);

        Collection<EntityModel<CategoryMasterDTO>> data = pageModel.getContent();

        Links links = pageModel.getLinks();

        var response = new CustomPageResponse<EntityModel<CategoryMasterDTO>>(data, links, pageModel.getMetadata());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryCreateUpdateDTO categoryDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        CategoryMasterDTO categoryMaster = categoryService.create(categoryDTO);

        return ResponseEntity.status(201).body(categoryMaster);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable UUID id,
            @Valid @RequestBody CategoryCreateUpdateDTO categoryDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        CategoryMasterDTO categoryMaster = categoryService.update(id, categoryDTO);

        return ResponseEntity.ok(categoryMaster);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        boolean isDeleted = categoryService.delete(id);
        return ResponseEntity.ok(isDeleted);
    }

}
