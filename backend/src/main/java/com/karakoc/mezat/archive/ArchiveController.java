package com.karakoc.mezat.archive;

import com.karakoc.mezat.auction.AuctionDTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.karakoc.mezat.auction.Auction.auctionsToDTO;

@RestController
@RequestMapping("/archive")
@AllArgsConstructor
public class ArchiveController {
    private final ArchiveService service;




    @GetMapping
    public List<Archive> getAllArchive(){
        return service.getAllArchive();
    }
}
