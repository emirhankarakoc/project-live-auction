package com.karakoc.mezat.product;


import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductDTO {

    private String id;
    private String photoPath;
    private String productTitle;
    private String owner;
    private LocalDateTime createddatetime;

}
