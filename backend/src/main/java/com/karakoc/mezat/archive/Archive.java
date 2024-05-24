package com.karakoc.mezat.archive;

import com.karakoc.mezat.auction.Auction;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Archive {
    @Id
    private String id;

    @OneToMany
    @JoinColumn(name = "auctionId")
    private List<Auction> auctions;
}
