package com.karakoc.mezat.auction;

import com.karakoc.mezat.exceptions.general.BadRequestException;
import com.karakoc.mezat.offer.CreateOfferRequest;
import com.karakoc.mezat.offer.Offer;
import com.karakoc.mezat.product.Product;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Auction {
    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "productId")
    private Product product;;
    private LocalDateTime startDate;
    private double price;
    @OneToMany
    @JoinColumn(name = "offerId")
    private List<Offer> offers;


    @Enumerated
    private EAuctionStatus auctionStatus;



    public static Auction createAuction(CreateAuctionRequest request){
        if (request.getStartDate().isBefore(LocalDateTime.now())){
            throw new BadRequestException("Auction start date must be in future.");
        }
        Auction auction = new Auction();
        auction.setAuctionStatus(EAuctionStatus.READY);
        auction.setId(UUID.randomUUID().toString());
        auction.setStartDate(request.getStartDate());
        auction.setPrice(request.getStartPrice());
        auction.setOffers(new ArrayList<>());
        return auction;
    }


    public static void auctionValidationsForNewOffers(Auction auction, CreateOfferRequest request){
        if (auction.getPrice() > request.getPrice()) {
            throw new BadRequestException("New offer must be higher than old price.");
        }
        if (auction.getStartDate().isBefore(LocalDateTime.now())){
            throw new BadRequestException("This auction is not active.");
        }

    }

    public static AuctionDTO auctionToDTO(Auction auction){
        AuctionDTO dto = new AuctionDTO();
        dto.setId(auction.getId());
        dto.setProduct(auction.getProduct());
        dto.setOffers(auction.getOffers());
        dto.setStartDate(auction.getStartDate());
        dto.setStartPrice(auction.getPrice());
        return dto;
    }

    public static List<AuctionDTO> auctionsToDTO(List<Auction> auctions){
        List<AuctionDTO> auctionDTOS = new ArrayList<>();
        for(Auction auction : auctions){
            auctionDTOS.add(auctionToDTO(auction));
        }
        return auctionDTOS;
    }

    public  static List<AuctionDTO> getAllAuctionsByStatus(EAuctionStatus status,List<Auction> auctions){
        List<Auction> statusAuctions = new ArrayList<>();
        for (Auction auction : auctions){
            if (auction.getAuctionStatus().equals(status)) statusAuctions.add(auction);
        }
        return auctionsToDTO(statusAuctions);
    }
}
