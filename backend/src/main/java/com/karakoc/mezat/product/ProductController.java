package com.karakoc.mezat.product;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
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


    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ProductDTO createProduct(@ModelAttribute CreateProductRequest request){
        return productService.createProduct(request);
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable String id){
        return productService.getProductById(id);
    }

    @GetMapping()
    public List<ProductDTO> getAllProducts(){
        return productService.getAllProducts();
    }
    @DeleteMapping("/{id}/{adminToken}")
    public ProductDTO deleteProductById(@PathVariable String id, @PathVariable String adminToken){
    return productService.deleteProductById(id,adminToken);
    }

}
