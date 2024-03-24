package com.karakoc.mezat.product;

import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductDTO createProduct(CreateProductRequest request);
    ProductDTO getProductById(String productId);

    Page<ProductDTO> getAllProductsPageable(int page, int size);
    List<ProductDTO> getAll();
    ProductDTO deleteProductById(String id, String adminToken);
    void deleteAll();
    int getAllProductsCount();
}