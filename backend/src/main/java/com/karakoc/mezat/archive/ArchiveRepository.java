package com.karakoc.mezat.archive;

import com.karakoc.mezat.auction.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArchiveRepository extends JpaRepository<Archive,String> {
}
