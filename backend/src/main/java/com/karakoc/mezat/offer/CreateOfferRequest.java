package com.karakoc.mezat.offer;


import lombok.Data;

@Data
public class CreateOfferRequest {
    private double price;
    private String auctionId;
    private String userToken;
}
