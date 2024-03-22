package com.karakoc.mezat.auction;

import java.util.List;

public interface AuctionService {
    AuctionDTO createAuction(CreateAuctionRequest request);


    List<AuctionDTO> getAll();
    AuctionDTO getAuction(String id);
    public List<AuctionDTO> getClosedAuctions(String adminToken);
    public List<AuctionDTO> getReadyAuctions(String adminToken);
    public List<AuctionDTO> getOpenedAuctions();

}
