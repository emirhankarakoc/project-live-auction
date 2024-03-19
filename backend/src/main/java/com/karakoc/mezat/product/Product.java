package com.karakoc.mezat.product;


import com.karakoc.mezat.user.User;
import com.karakoc.mezat.user.UserDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Product {

    @Id
    private String id;
    private String photoPath;
    private String productTitle;
    private double price;
    private String imageName;
    private String imageUrl;
    private String imageCloudId;



    public static ProductDTO productToDTO(Product product){
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setPrice(product.getPrice());
        dto.setProductTitle(product.getProductTitle());
        dto.setPhotoPath(product.getPhotoPath());
        return dto;
    }


    public static List<ProductDTO> productDTOS(List<Product> productList){
        List<ProductDTO> response = new ArrayList<>();
        for (Product product : productList) {
            response.add(productToDTO(product));
        }
        return response;
    }
}
