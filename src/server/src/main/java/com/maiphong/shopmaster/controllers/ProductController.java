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

import com.maiphong.shopmaster.dtos.product.ProductCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.product.ProductMasterDTO;
import com.maiphong.shopmaster.response.CustomPageResponse;
import com.maiphong.shopmaster.services.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;
    private final PagedResourcesAssembler<ProductMasterDTO> pagedResource;

    public ProductController(ProductService productService,
            PagedResourcesAssembler<ProductMasterDTO> pagedResource) {
        this.productService = productService;
        this.pagedResource = pagedResource;
    }

    @GetMapping
    public ResponseEntity<List<ProductMasterDTO>> getAllProduct() {
        List<ProductMasterDTO> productMasters = productService.getAll();
        return ResponseEntity.ok(productMasters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductMasterDTO> getProductById(@PathVariable UUID id) {
        ProductMasterDTO productMaster = productService.getById(id);
        return ResponseEntity.ok(productMaster);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductMasterDTO>> searchProduct(@RequestParam(required = false) String keyword) {
        List<ProductMasterDTO> productMasters = productService.searchProduct(keyword);
        return ResponseEntity.ok(productMasters);
    }

    @GetMapping("/search-paginated")
    public ResponseEntity<?> searchPaginatedProduct(
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

        Page<ProductMasterDTO> productMasters = productService.searchPaginated(keyword, pageable);

        var pageModel = pagedResource.toModel(productMasters);

        Collection<EntityModel<ProductMasterDTO>> data = pageModel.getContent();

        Links links = pageModel.getLinks();

        var response = new CustomPageResponse<EntityModel<ProductMasterDTO>>(data, links, pageModel.getMetadata());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductCreateUpdateDTO productDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        ProductMasterDTO productMaster = productService.create(productDTO);

        return ResponseEntity.status(201).body(productMaster);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable UUID id,
            @Valid @RequestBody ProductCreateUpdateDTO productDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        ProductMasterDTO productMaster = productService.update(id, productDTO);

        return ResponseEntity.ok(productMaster);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        boolean isDeleted = productService.delete(id);
        return ResponseEntity.ok(isDeleted);
    }

}
