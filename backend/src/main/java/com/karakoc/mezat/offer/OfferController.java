package com.karakoc.mezat.offer;

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

    @PostMapping
    public OfferDTO createOffer(@RequestBody CreateOfferRequest request){
        return offerService.createOffer(request);
    }

    @GetMapping
    public List<OfferDTO> getAll(){return offerService.getAll();}
}
