package com.karakoc.mezat.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product Controller")
@AllArgsConstructor
@CrossOrigin
public class ProductController {
    private final ProductService productService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Ürün oluştur", description = "Müzayede'ye bağlamak için ürün oluştur.")

    public ProductDTO createProduct(@ModelAttribute CreateProductRequest request){
        return productService.createProduct(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Ürün getir", description = "")

    public ProductDTO getProductById(@PathVariable String id){
        return productService.getProductById(id);
    }

    @GetMapping("/page/{page}/{size}")
    @Operation(summary = "Tüm ürünleri getir (pageable)", description = "")

    public Page<ProductDTO> getAllProductsPageable(@PathVariable int page, @PathVariable int size) {
        return productService.getAllProductsPageable(page, size);
    }

    @GetMapping()
    @Operation(summary = "Tüm ürünleri getir(kaldırılacak)", description = "")

    public List<ProductDTO> getAll() {
        return productService.getAll();
    }

    @DeleteMapping("/{id}/token/{adminToken}")
    @Operation(summary = "Ürün sil", description = "")

    public ProductDTO deleteProductById(@PathVariable String id, @PathVariable String adminToken) {
        return productService.deleteProductById(id, adminToken);
    }

    @DeleteMapping()
    @Operation(summary = "tüm ürünleri sil (kaldırılıcak)", description = "")

    public void deleteAllProducts() {
        productService.deleteAll();
    }

}
