package com.karakoc.mezat.auction;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auctions")
@Tag(name = "Auctions Controller")
@AllArgsConstructor
public class AuctionController {
    private final AuctionService service;


    @PostMapping
    public AuctionDTO createAuction(@RequestBody CreateAuctionRequest request){
       return service.createAuction(request);
    }

    @GetMapping
    public List<AuctionDTO> getAll(){
        return service.getAll();
    }
}
