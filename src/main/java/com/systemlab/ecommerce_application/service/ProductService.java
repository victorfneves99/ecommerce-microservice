package com.systemlab.ecommerce_application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.systemlab.ecommerce_application.dto.ProductRequest;
import com.systemlab.ecommerce_application.dto.ProductResponse;
import com.systemlab.ecommerce_application.model.Product;

import com.systemlab.ecommerce_application.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    public List<ProductResponse> findAllActiveProducts() {
        List<Product> products = productRepository.findByActiveTrue();
        return products.stream().map(this::mapToProductResponse).toList();
    }

    public Optional<ProductResponse> findById(Long id) {
        return productRepository.findById(id)
                .map(this::mapToProductResponse);
    }

    public ProductResponse save(ProductRequest productRequest) {

        var product = new Product();
        updateProductFromRequest(product, productRequest);
        var save = productRepository.save(product);
        return mapToProductResponse(save);
    }

    public Optional<ProductResponse> update(Long id, ProductRequest productRequest) {
        return productRepository.findById(id)
                .map(product -> {
                    updateProductFromRequest(product, productRequest);
                    var existingProduct = productRepository.save(product);
                    return mapToProductResponse(existingProduct);
                });
    }

    
    public boolean delete(Long id) {
        return productRepository.findById(id)
        .map(product -> {
            product.setActive(false);
            productRepository.save(product);
            return true;
        }).orElse(false);
    }

    private ProductResponse mapToProductResponse(Product save) {

        var productResponse = new ProductResponse();
        productResponse.setId(save.getId());
        productResponse.setName(save.getName());
        productResponse.setDescription(save.getDescription());
        productResponse.setQuantity(save.getQuantity());
        productResponse.setPrice(save.getPrice());
        productResponse.setStockQuantity(save.getStockQuantity());
        productResponse.setCategory(save.getCategory());
        productResponse.setImageUrl(save.getImageUrl());
        productResponse.setActive(save.getActive());
        return productResponse;

    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setQuantity(productRequest.getQuantity());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(productRequest.getCategory());
        product.setImageUrl(productRequest.getImageUrl());
    }

    
    public List<ProductResponse> searchProductsCustom(String keyword) {
        return productRepository.searchProducts(keyword)
                .stream()
                .map(this::mapToProductResponse)
                .toList();
    }

}
