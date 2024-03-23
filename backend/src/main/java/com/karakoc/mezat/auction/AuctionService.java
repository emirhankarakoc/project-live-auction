package com.karakoc.mezat.auction;

import java.util.List;

public interface AuctionService {
    AuctionDTO createAuction(CreateAuctionRequest request);


    List<AuctionDTO> getAll();

    AuctionDTO getAuction(String auctionId);

    public List<AuctionDTO> getEndedAuctions(String adminToken);

    public List<AuctionDTO> getCreatedAuctions(String adminToken);

    public List<AuctionDTO> getReadyAuctions();

    AuctionDTO setAuctionStatusToOpen(String auctionId, String adminToken);

    AuctionDTO deleteAuctionById(String auctionId, String adminToken);
}
