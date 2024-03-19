package com.karakoc.mezat.product;


import lombok.Data;

@Data
public class DeleteProductByIdRequest {
    private String id;
    private String adminToken;
}
