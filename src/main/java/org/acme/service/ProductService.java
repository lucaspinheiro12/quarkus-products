package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.acme.dto.ProductDTO;
import org.acme.entity.ProductEntity;
import org.acme.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream().map(
                this::convertToDTO
        ).toList();
    }

    public void create(ProductDTO productDTO){
            ProductEntity res = convertToEntity(productDTO);
            productRepository.persist(res);
    }

    public ProductDTO findById(Long id) {
        var res =  productRepository.findById(id);
        return res != null ? convertToDTO(res) : null;
    }

    public void update(Long id, ProductDTO productDTO) {
            ProductEntity productEntity = productRepository.findById(id);
            productEntity.setName(productDTO.getName());
            productEntity.setDescription(productDTO.getDescription());
            productEntity.setCategory(productDTO.getCategory());
            productEntity.setModel(productDTO.getModel());
            productEntity.setPrice(productDTO.getPrice());
            productRepository.persist(productEntity);
    }

    public void delete(Long id){
            ProductEntity productEntity = productRepository.findById(id);
            productRepository.delete(productEntity);
    }

    private ProductDTO convertToDTO(ProductEntity productEntity) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName(productEntity.getName());
        productDTO.setDescription(productEntity.getDescription());
        productDTO.setCategory(productEntity.getCategory());
        productDTO.setModel(productEntity.getModel());
        productDTO.setPrice(productEntity.getPrice());
        return productDTO;
    }

    private ProductEntity convertToEntity(ProductDTO productDTO) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(productDTO.getName());
        productEntity.setDescription(productDTO.getDescription());
        productEntity.setCategory(productDTO.getCategory());
        productEntity.setModel(productDTO.getModel());
        productEntity.setPrice(productDTO.getPrice());
        return productEntity;
    }
}
