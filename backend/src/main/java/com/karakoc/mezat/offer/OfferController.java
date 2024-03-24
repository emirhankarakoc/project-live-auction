package com.karakoc.mezat.offer;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/offers")
@Tag(name = "Offers Controller")
@AllArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @PostMapping()
    @Operation(summary = "Teklif ver", description = "Müzayedeye teklif verir.")

    public OfferDTO createOffer(@RequestParam("price") double price,
                                @RequestParam("auctionId") String auctionId,
                                @RequestParam("userToken") String userToken) {

        return offerService.createOffer(price,auctionId,userToken);
    }

    @GetMapping
    @Operation(summary = "Tüm teklifler. (Kaldırılacak)", description = "Tüm teklifler")

    public List<OfferDTO> getAll(){return offerService.getAll();}

    @GetMapping("/auction/{auctionId}")
    public List<OfferDTO> getOffersByAuctionId(@PathVariable String auctionId){
        return offerService.getOffersByAuctionId(auctionId);
    }
}
