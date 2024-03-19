package com.karakoc.mezat.offer;

import lombok.Data;

@Data
public class OfferDTO {
    private String id;

    private double price;
    private String auctionId;
    private String userToken;

}
