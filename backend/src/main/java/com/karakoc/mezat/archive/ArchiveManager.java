package com.karakoc.mezat.archive;


import com.karakoc.mezat.auction.AuctionDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.karakoc.mezat.auction.Auction.auctionsToDTO;

@Service
@AllArgsConstructor
public class ArchiveManager implements ArchiveService{

    private final ArchiveRepository archive;



    public List<Archive> getAllArchive(){
        return archive.findAll();
    }
}
