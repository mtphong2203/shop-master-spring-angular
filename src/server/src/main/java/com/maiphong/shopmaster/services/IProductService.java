package com.maiphong.shopmaster.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.maiphong.shopmaster.dtos.product.ProductCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.product.ProductMasterDTO;

public interface IProductService {
    List<ProductMasterDTO> getAll();

    ProductMasterDTO getById(UUID id);

    List<ProductMasterDTO> searchProduct(String name);

    Page<ProductMasterDTO> searchPaginated(String name, Pageable pageable);

    ProductMasterDTO create(ProductCreateUpdateDTO productDTO);

    ProductMasterDTO update(UUID id, ProductCreateUpdateDTO productDTO);

    boolean delete(UUID id);
}
