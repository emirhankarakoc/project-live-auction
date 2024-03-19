package com.karakoc.mezat.product;

import java.util.List;

public interface ProductService {

    ProductDTO createProduct(CreateProductRequest request);
    ProductDTO getProductById(String productId);

    List<ProductDTO> getAllProducts();
    ProductDTO deleteProductById(String id, String adminToken);
}