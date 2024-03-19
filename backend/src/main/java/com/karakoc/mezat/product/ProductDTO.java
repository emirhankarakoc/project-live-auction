package com.karakoc.mezat.product;


import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ProductDTO {

    private String id;
    private String photoPath;
    private String productTitle;
    private double price;
}
