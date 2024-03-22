package com.karakoc.mezat.auction;

import com.karakoc.mezat.offer.Offer;
import com.karakoc.mezat.product.Product;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class AuctionDTO {
    private String id;
    private Product product;
    private double startPrice;
    private LocalDateTime startDate;
    private List<Offer> offers;
}
