package com.karakoc.mezat.auction;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ArchiveEntityDTO {
    private String id;
    private String photoPath;
    private String productTitle;
    private String owner;
    private LocalDateTime endDate;
    private LocalDateTime createddatetime;
    private double price;
    private String description;
    private EAuctionStatus auctionStatus;
}
