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

import static com.karakoc.mezat.product.Product.productToDTO;

@Entity
@Data
public class Auction {
    @Id
    private String id;

    @OneToOne
    @JoinColumn(name = "productId")
    private Product product;
    private LocalDateTime endDate;
    private LocalDateTime createddatetime;
    private double price;

    @OneToMany
    @JoinColumn(name = "offerId")
    private List<Offer> offers;

    private String description;

    @Enumerated
    private EAuctionStatus auctionStatus;



    public static Auction createAuction(CreateAuctionRequest request) {
        if (request.getEndDate().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Auction start date must be in future.");
        }
        Auction auction = new Auction();
        auction.setCreateddatetime(LocalDateTime.now());
        auction.setAuctionStatus(EAuctionStatus.CREATED);
        auction.setId(UUID.randomUUID().toString());
        auction.setEndDate(request.getEndDate());
        auction.setDescription(request.getDescription());
        auction.setPrice(request.getStartPrice());
        auction.setOffers(new ArrayList<>());
        return auction;
    }


    public static void auctionValidationsForNewOffers(Auction auction, double price) {
        if (auction.getPrice() > price) {
            throw new BadRequestException("New offer must be higher than old price.");
        }
        if (auction.getEndDate().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Sorry, this auction is not active.");
        }

    }

    public static AuctionDTO auctionToDTO(Auction auction) {
        AuctionDTO dto = new AuctionDTO();
        dto.setCreateddatetime(auction.getCreateddatetime());
        dto.setProduct(productToDTO(auction.getProduct()));
        dto.setId(auction.getId());
        dto.setDescription(auction.getDescription());
        dto.setEndDate(auction.getEndDate());
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
}
