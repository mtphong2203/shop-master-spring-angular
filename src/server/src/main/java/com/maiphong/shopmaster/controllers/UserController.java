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

import com.maiphong.shopmaster.dtos.user.UserCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.user.UserMasterDTO;
import com.maiphong.shopmaster.response.CustomPageResponse;
import com.maiphong.shopmaster.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final PagedResourcesAssembler<UserMasterDTO> pagedResource;

    public UserController(UserService userService,
            PagedResourcesAssembler<UserMasterDTO> pagedResource) {
        this.userService = userService;
        this.pagedResource = pagedResource;
    }

    @GetMapping
    public ResponseEntity<List<UserMasterDTO>> getAllUser() {
        List<UserMasterDTO> userMasters = userService.getAll();
        return ResponseEntity.ok(userMasters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserMasterDTO> getUserById(@PathVariable UUID id) {
        UserMasterDTO userMaster = userService.getById(id);
        return ResponseEntity.ok(userMaster);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserMasterDTO>> searchUser(@RequestParam(required = false) String keyword) {
        List<UserMasterDTO> userMasters = userService.searchUser(keyword);
        return ResponseEntity.ok(userMasters);
    }

    @GetMapping("/search-paginated")
    public ResponseEntity<?> searchPaginatedUser(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "account") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String orderBy,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size) {
        Pageable pageable = null;

        if (orderBy.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        Page<UserMasterDTO> userMasters = userService.searchPaginated(keyword, pageable);

        var pageModel = pagedResource.toModel(userMasters);

        Collection<EntityModel<UserMasterDTO>> data = pageModel.getContent();

        Links links = pageModel.getLinks();

        var response = new CustomPageResponse<EntityModel<UserMasterDTO>>(data, links, pageModel.getMetadata());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateUpdateDTO userDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        UserMasterDTO userMaster = userService.create(userDTO);

        return ResponseEntity.status(201).body(userMaster);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable UUID id,
            @Valid @RequestBody UserCreateUpdateDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        UserMasterDTO userMaster = userService.update(id, userDTO);

        return ResponseEntity.ok(userMaster);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        boolean isDeleted = userService.delete(id);
        return ResponseEntity.ok(isDeleted);
    }

}
