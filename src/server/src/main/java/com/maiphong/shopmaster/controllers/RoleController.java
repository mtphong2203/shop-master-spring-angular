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

import com.maiphong.shopmaster.dtos.role.RoleCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.role.RoleMasterDTO;
import com.maiphong.shopmaster.response.CustomPageResponse;
import com.maiphong.shopmaster.services.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;
    private final PagedResourcesAssembler<RoleMasterDTO> pagedResource;

    public RoleController(RoleService roleService,
            PagedResourcesAssembler<RoleMasterDTO> pagedResource) {
        this.roleService = roleService;
        this.pagedResource = pagedResource;
    }

    @GetMapping
    public ResponseEntity<List<RoleMasterDTO>> getAllRole() {
        List<RoleMasterDTO> roleMasters = roleService.getAll();
        return ResponseEntity.ok(roleMasters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleMasterDTO> getRoleById(@PathVariable UUID id) {
        RoleMasterDTO roleMaster = roleService.getById(id);
        return ResponseEntity.ok(roleMaster);
    }

    @GetMapping("/search")
    public ResponseEntity<List<RoleMasterDTO>> searchRole(@RequestParam(required = false) String keyword) {
        List<RoleMasterDTO> roleMasters = roleService.searchRole(keyword);
        return ResponseEntity.ok(roleMasters);
    }

    @GetMapping("/search-paginated")
    public ResponseEntity<?> searchPaginatedRole(
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

        Page<RoleMasterDTO> roleMasters = roleService.searchPaginated(keyword, pageable);

        var pageModel = pagedResource.toModel(roleMasters);

        Collection<EntityModel<RoleMasterDTO>> data = pageModel.getContent();

        Links links = pageModel.getLinks();

        var response = new CustomPageResponse<EntityModel<RoleMasterDTO>>(data, links, pageModel.getMetadata());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createRole(@Valid @RequestBody RoleCreateUpdateDTO roleDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        RoleMasterDTO roleMaster = roleService.create(roleDTO);

        return ResponseEntity.status(201).body(roleMaster);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRole(@PathVariable UUID id,
            @Valid @RequestBody RoleCreateUpdateDTO roleDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        RoleMasterDTO roleMaster = roleService.update(id, roleDTO);

        return ResponseEntity.ok(roleMaster);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        boolean isDeleted = roleService.delete(id);
        return ResponseEntity.ok(isDeleted);
    }

}
