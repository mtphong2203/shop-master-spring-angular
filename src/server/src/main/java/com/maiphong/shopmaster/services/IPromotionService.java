package com.maiphong.shopmaster.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maiphong.shopmaster.dtos.promotion.PromotionCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.promotion.PromotionMasterDTO;

public interface IPromotionService {
    List<PromotionMasterDTO> getAll();

    PromotionMasterDTO getById(UUID id);

    Page<PromotionMasterDTO> searchPaginated(double discountAmount, Pageable pageable);

    PromotionMasterDTO create(PromotionCreateUpdateDTO promotionDTO);

    PromotionMasterDTO update(UUID id, PromotionCreateUpdateDTO promotionDTO);

    boolean delete(UUID id);
}
