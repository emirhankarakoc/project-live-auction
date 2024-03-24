package com.karakoc.mezat.offer;

import java.util.List;

public interface OfferService {
    OfferDTO createOffer(double price, String auctionId,String userToken);
    List<OfferDTO> getAll();
    List<OfferDTO> getOffersByAuctionId(String auctionId);
}
