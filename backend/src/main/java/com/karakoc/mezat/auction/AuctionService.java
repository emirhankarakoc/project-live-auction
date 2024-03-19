package com.karakoc.mezat.auction;

import java.util.List;

public interface AuctionService {
    AuctionDTO createAuction(CreateAuctionRequest request);

    List<AuctionDTO> getAll();
}
