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
    @GetMapping("/{id}")
    public AuctionDTO getAuction(@PathVariable String id){
        return service.getAuction(id);
    }

    @GetMapping
    public List<AuctionDTO> getAll(){
        return service.getAll();
    }

    @GetMapping("/filter/closed/{adminToken}")
    public List<AuctionDTO> getClosedAuctions(@PathVariable String adminToken){
        return service.getClosedAuctions(adminToken);
    }

    @GetMapping("/filter/ready/{adminToken}")
    public List<AuctionDTO> getReadyAuctions(@PathVariable String adminToken){
        return service.getReadyAuctions(adminToken);
    }

    @GetMapping("/filter/open")
    public List<AuctionDTO> getOpenedAuctions(){
        return service.getOpenedAuctions();
    }

}
