package com.maiphong.shopmaster.services;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.shopmaster.dtos.promotion.PromotionCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.promotion.PromotionMasterDTO;
import com.maiphong.shopmaster.exceptions.ResourceNotFoundException;
import com.maiphong.shopmaster.mappers.PromotionMapper;
import com.maiphong.shopmaster.models.Promotion;
import com.maiphong.shopmaster.repositories.PromotionRepository;

@Service
@Transactional
public class PromotionService implements IPromotionService {

    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;

    public PromotionService(PromotionRepository promotionRepository, PromotionMapper promotionMapper) {
        this.promotionRepository = promotionRepository;
        this.promotionMapper = promotionMapper;
    }

    @Override
    public List<PromotionMasterDTO> getAll() {
        // Get all promotion
        List<Promotion> promotions = promotionRepository.findAll();

        // convert all to dto
        List<PromotionMasterDTO> promotionMasters = promotions.stream().map(promotion -> {
            PromotionMasterDTO promotionMaster = promotionMapper.toMasterDTO(promotion);
            return promotionMaster;
        }).toList();

        return promotionMasters;
    }

    @Override
    public PromotionMasterDTO getById(UUID id) {
        Promotion promotion = promotionRepository.findById(id).orElse(null);

        if (promotion == null) {
            throw new ResourceNotFoundException("Promotion is not found");
        }
        PromotionMasterDTO promotionMaster = promotionMapper.toMasterDTO(promotion);
        return promotionMaster;
    }

    @Override
    public Page<PromotionMasterDTO> searchPaginated(double discountAmount, Pageable pageable) {
        Specification<Promotion> spec = (root, _, cb) -> {
            if (discountAmount < 0) {
                return null;
            }

            return cb.equal(root.get("discountAmount"), discountAmount);
        };

        Page<Promotion> categories = promotionRepository.findAll(spec, pageable);

        Page<PromotionMasterDTO> promotionMasters = categories.map(promotion -> {
            PromotionMasterDTO promotionMaster = promotionMapper.toMasterDTO(promotion);
            return promotionMaster;
        });

        return promotionMasters;
    }

    @Override
    public PromotionMasterDTO create(PromotionCreateUpdateDTO promotionDTO) {
        if (promotionDTO == null) {
            throw new IllegalArgumentException("Promotion is required");
        }

        Promotion newPromotion = promotionMapper.toEntity(promotionDTO);

        newPromotion = promotionRepository.save(newPromotion);

        return promotionMapper.toMasterDTO(newPromotion);
    }

    @Override
    public PromotionMasterDTO update(UUID id, PromotionCreateUpdateDTO promotionDTO) {
        if (promotionDTO == null) {
            throw new IllegalArgumentException("Promotion is required");
        }

        Promotion promotion = promotionRepository.findById(id).orElse(null);

        if (promotion == null) {
            throw new ResourceNotFoundException("Promotion is not exist!");
        }

        promotion = promotionMapper.toEntity(promotionDTO, promotion);
        promotion.setUpdatedAt(ZonedDateTime.now());

        promotion = promotionRepository.save(promotion);

        return promotionMapper.toMasterDTO(promotion);
    }

    @Override
    public boolean delete(UUID id) {
        Promotion promotion = promotionRepository.findById(id).orElse(null);

        if (promotion == null) {
            throw new ResourceNotFoundException("Promotion is not exist!");
        }
        promotionRepository.delete(promotion);

        return !promotionRepository.existsById(id);
    }

}
