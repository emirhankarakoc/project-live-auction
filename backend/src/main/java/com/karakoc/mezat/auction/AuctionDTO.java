package com.karakoc.mezat.auction;

import com.karakoc.mezat.offer.Offer;
import com.karakoc.mezat.product.Product;
import com.karakoc.mezat.product.ProductDTO;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class AuctionDTO {
    private String id;
    private ProductDTO product;
    private double startPrice;
    private LocalDateTime endDate;
    private LocalDateTime createddatetime;
    private String description;
    private EAuctionStatus status;

}
