package com.maiphong.shopmaster.services;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maiphong.shopmaster.dtos.product.ProductCreateUpdateDTO;
import com.maiphong.shopmaster.dtos.product.ProductMasterDTO;
import com.maiphong.shopmaster.exceptions.ResourceNotFoundException;
import com.maiphong.shopmaster.mappers.ProductMapper;
import com.maiphong.shopmaster.models.Product;
import com.maiphong.shopmaster.repositories.ProductRepository;

@Service
@Transactional
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public List<ProductMasterDTO> getAll() {
        // Get all product
        List<Product> categories = productRepository.findAll();

        // convert all to dto
        List<ProductMasterDTO> productMasters = categories.stream().map(product -> {
            ProductMasterDTO productMaster = productMapper.toMasterDTO(product);
            return productMaster;
        }).toList();

        return productMasters;
    }

    @Override
    public ProductMasterDTO getById(UUID id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new ResourceNotFoundException("Product is not found");
        }
        ProductMasterDTO productMaster = productMapper.toMasterDTO(product);
        return productMaster;
    }

    @Override
    public List<ProductMasterDTO> searchProduct(String keyword) {
        Specification<Product> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.or(
                    cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"),
                    cb.like(root.get("description"), "%" + keyword + "%"));
        };

        List<Product> categories = productRepository.findAll(spec);

        List<ProductMasterDTO> productMasters = categories.stream().map(product -> {
            ProductMasterDTO productMaster = productMapper.toMasterDTO(product);
            return productMaster;
        }).toList();

        return productMasters;
    }

    @Override
    public Page<ProductMasterDTO> searchPaginated(String keyword, Pageable pageable) {
        Specification<Product> spec = (root, _, cb) -> {
            if (keyword == null) {
                return null;
            }

            return cb.or(
                    cb.like(cb.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"),
                    cb.like(root.get("description"), "%" + keyword + "%"));
        };

        Page<Product> categories = productRepository.findAll(spec, pageable);

        Page<ProductMasterDTO> productMasters = categories.map(product -> {
            ProductMasterDTO productMaster = productMapper.toMasterDTO(product);
            return productMaster;
        });

        return productMasters;
    }

    @Override
    public ProductMasterDTO create(ProductCreateUpdateDTO productDTO) {
        if (productDTO == null) {
            throw new IllegalArgumentException("Product is required");
        }

        Product newProduct = productMapper.toEntity(productDTO);

        newProduct = productRepository.save(newProduct);

        return productMapper.toMasterDTO(newProduct);
    }

    @Override
    public ProductMasterDTO update(UUID id, ProductCreateUpdateDTO productDTO) {
        if (productDTO == null) {
            throw new IllegalArgumentException("Product is required");
        }

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new ResourceNotFoundException("Product is not exist!");
        }

        product = productMapper.toEntity(productDTO, product);
        product.setUpdatedAt(ZonedDateTime.now());

        product = productRepository.save(product);

        return productMapper.toMasterDTO(product);
    }

    @Override
    public boolean delete(UUID id) {
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            throw new ResourceNotFoundException("Product is not exist!");
        }
        productRepository.delete(product);

        return !productRepository.existsById(id);
    }

}
