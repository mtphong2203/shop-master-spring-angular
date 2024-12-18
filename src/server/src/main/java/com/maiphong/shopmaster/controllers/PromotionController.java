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

import com.maiphong.shopmaster.dtos.promotion.PromotionCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.promotion.PromotionMasterDTO;
import com.maiphong.shopmaster.response.CustomPageResponse;
import com.maiphong.shopmaster.services.PromotionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/promotions")
public class PromotionController {

    private final PromotionService promotionService;
    private final PagedResourcesAssembler<PromotionMasterDTO> pagedResource;

    public PromotionController(PromotionService promotionService,
            PagedResourcesAssembler<PromotionMasterDTO> pagedResource) {
        this.promotionService = promotionService;
        this.pagedResource = pagedResource;
    }

    @GetMapping
    public ResponseEntity<List<PromotionMasterDTO>> getAllPromotion() {
        List<PromotionMasterDTO> promotionMasters = promotionService.getAll();
        return ResponseEntity.ok(promotionMasters);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionMasterDTO> getPromotionById(@PathVariable UUID id) {
        PromotionMasterDTO promotionMaster = promotionService.getById(id);
        return ResponseEntity.ok(promotionMaster);
    }

    @GetMapping("/search-paginated")
    public ResponseEntity<?> searchPainatedPromotion(
            @RequestParam(required = false) double discountAmount,
            @RequestParam(required = false, defaultValue = "endDate") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String promotionBy,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "5") int size) {
        Pageable pageable = null;

        if (promotionBy.equals("asc")) {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        } else {
            pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());
        }

        Page<PromotionMasterDTO> promotionMasters = promotionService.searchPaginated(discountAmount, pageable);

        var pageModel = pagedResource.toModel(promotionMasters);

        Collection<EntityModel<PromotionMasterDTO>> data = pageModel.getContent();

        Links links = pageModel.getLinks();

        var response = new CustomPageResponse<EntityModel<PromotionMasterDTO>>(data, links, pageModel.getMetadata());

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createPromotion(@Valid @RequestBody PromotionCreateUpdateDTO promotionDTO,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        PromotionMasterDTO promotionMaster = promotionService.create(promotionDTO);

        return ResponseEntity.status(201).body(promotionMaster);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePromotion(@PathVariable UUID id,
            @Valid @RequestBody PromotionCreateUpdateDTO promotionDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        PromotionMasterDTO promotionMaster = promotionService.update(id, promotionDTO);

        return ResponseEntity.ok(promotionMaster);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> delete(@PathVariable UUID id) {
        boolean isDeleted = promotionService.delete(id);
        return ResponseEntity.ok(isDeleted);
    }

}
