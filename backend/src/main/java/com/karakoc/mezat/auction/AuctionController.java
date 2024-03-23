package com.karakoc.mezat.auction;


import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Müzayede oluştur.", description = "Müzayede oluşturur.")

    public AuctionDTO createAuction(@RequestBody CreateAuctionRequest request) {
        return service.createAuction(request);
    }

    @GetMapping("/{auctionId}")
    @Operation(summary = "ID'ye göre müzayede getir", description = "Verilen ID'ye sahip olan müzayedeyi bulur getirir. Yoksa hata döndürür.")

    public AuctionDTO getAuction(@PathVariable String auctionId) {
        return service.getAuction(auctionId);
    }

    @GetMapping
    @Operation(summary = "Tüm müzayedeleri getir (Kaldırılacak)", description = "Tüm müzayedeleri getirir.")

    public List<AuctionDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/filter/closed/{adminToken}")
    @Operation(summary = "Bitmiş müzayedeler", description = "Süresi bitmiş müzayedeleri getirir.")

    public List<AuctionDTO> getEndedAuctions(@PathVariable String adminToken) {
        return service.getEndedAuctions(adminToken);
    }

    @GetMapping("/filter/created/{adminToken}")
    @Operation(summary = "Yayınlanmamış müzayedeler", description = "Daha başlamamış müzayedeleri getirir.")

    public List<AuctionDTO> getCreatedAuctions(@PathVariable String adminToken) {
        return service.getCreatedAuctions(adminToken);
    }

    @GetMapping("/filter/ready")
    @Operation(summary = "Yayındaki müzayedeler", description = "Kullanıcının sisteme giriş yapmasını sağlar.")

    public List<AuctionDTO> getReadyAuctions() {
        return service.getReadyAuctions();
    }

    @PutMapping("/filter/ready/auction/{auctionId}/token/{adminToken}")
    @Operation(summary = "Müzayedeyi başlatır.", description = "Teklfiflere açık hale getirir, normalde kapalıdır. ")

    public AuctionDTO setAuctionStatusToOpen(@PathVariable String auctionId, @PathVariable String adminToken) {
        return service.setAuctionStatusToOpen(auctionId, adminToken);
    }

    @DeleteMapping("/{auctionId}/token/{adminToken}")
    @Operation(summary = "Müzayede silme", description = "Admin tokeni ile müzayede siler.")

    public AuctionDTO deleteAuctionById(@PathVariable String auctionId, @PathVariable String adminToken) {
        return service.deleteAuctionById(auctionId, adminToken);
    }
}
