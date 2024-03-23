package com.karakoc.mezat.auction;

import org.springframework.data.domain.Page;

import java.util.List;

public interface AuctionService {
    AuctionDTO createAuction(CreateAuctionRequest request);


    Page<AuctionDTO> getAll(int page, int size);

    AuctionDTO getAuction(String auctionId);

    Page<AuctionDTO> getEndedAuctions(String adminToken,int page,int size);
    Page<AuctionDTO> getCreatedAuctions(String adminToken, int page, int size);

    Page<AuctionDTO> getReadyAuctions(int page,int size);
    AuctionDTO setAuctionStatusToOpen(String auctionId, String adminToken);

    AuctionDTO deleteAuctionById(String auctionId, String adminToken);
}
