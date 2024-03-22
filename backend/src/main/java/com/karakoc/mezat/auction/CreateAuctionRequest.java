package com.karakoc.mezat.auction;

import com.karakoc.mezat.product.CreateProductRequest;
import com.karakoc.mezat.product.Product;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CreateAuctionRequest {
    private String productId;
    private LocalDateTime startDate;

    private double startPrice;

    private String adminToken;
}
