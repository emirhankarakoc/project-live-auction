package com.karakoc.mezat.offer;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Offer {
    @Id
    private String id;

    private String auctionId;
    private double price;

    private String userToken;




    public static Offer createOffer(CreateOfferRequest request){
        Offer offer = new Offer();
        offer.setId(UUID.randomUUID().toString());
        offer.setPrice(request.getPrice());
        offer.setAuctionId(request.getAuctionId());
        offer.setUserToken(request.getUserToken());
        return offer;
    }
    public static OfferDTO offerToDTO(Offer offer){
        OfferDTO dto = new OfferDTO();
        dto.setId(offer.getId());
        dto.setAuctionId(offer.getAuctionId());
        dto.setPrice(offer.getPrice());
        dto.setUserToken(offer.getUserToken());
        return dto;
    }

    public static List<OfferDTO> offersToDTO(List<Offer> offers){
        List<OfferDTO> offerDTOS = new ArrayList<>();
        for (Offer offer : offers){
            offerDTOS.add(offerToDTO(offer));
        }
        return offerDTOS;
    }



}
