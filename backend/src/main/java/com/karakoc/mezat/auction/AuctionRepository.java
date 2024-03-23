package com.karakoc.mezat.auction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuctionRepository extends JpaRepository<Auction,String> {

    Page<Auction> findAllByAuctionStatus(EAuctionStatus status, Pageable pageable);

}
