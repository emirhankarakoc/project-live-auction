package com.karakoc.mezat.auction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Archive extends JpaRepository<Auction,String> {
}
