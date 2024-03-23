package com.karakoc.mezat.auction;

import com.karakoc.mezat.offer.Offer;
import com.karakoc.mezat.product.Product;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class AuctionDTO {
    private String id;
    private Product product;
    private double startPrice;
    private LocalDateTime endDate;
    private List<Offer> offers;
    private String description;
    private EAuctionStatus auctionStatus;

}
