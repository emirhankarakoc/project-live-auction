package com.karakoc.mezat.product;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateProductRequest {
    private MultipartFile multipartFile;
    private String productTitle;
    private String adminToken;

}
