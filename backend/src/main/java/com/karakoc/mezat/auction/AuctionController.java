package com.karakoc.mezat.auction;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
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

    public Page<AuctionDTO> getAll(int page, int size) {
        return service.getAll(page,size);
    }

    @GetMapping("/filter/closed/token/{adminToken}/page/{page}/size{size}")
    @Operation(summary = "Bitmiş müzayedeler", description = "Süresi bitmiş müzayedeleri getirir.")

    public Page<AuctionDTO> getEndedAuctions(@PathVariable String adminToken, @PathVariable int page, @PathVariable int size) {
        return service.getEndedAuctions(adminToken,page,size);
    }

    @GetMapping("/filter/created/token/{adminToken}/page/{page}/size/{size}")
    @Operation(summary = "Yayınlanmamış müzayedeler", description = "Daha başlamamış müzayedeleri getirir.")

    public Page<AuctionDTO> getCreatedAuctions(@PathVariable String adminToken, @PathVariable int page, @PathVariable int size) {
        return service.getCreatedAuctions(adminToken,page,size);
    }

    @GetMapping("/filter/ready/page/{page}/size/{size}")
    @Operation(summary = "Yayındaki müzayedeler", description = "Kullanıcının sisteme giriş yapmasını sağlar.")

    public Page<AuctionDTO> getReadyAuctions(@PathVariable int page, @PathVariable int size) {
        return service.getReadyAuctions(page,size);
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
