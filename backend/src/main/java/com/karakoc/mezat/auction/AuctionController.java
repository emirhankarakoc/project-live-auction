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
    private final AuctionRepository repository;



    @PostMapping
    @Operation(summary = "Müzayede oluştur.", description = "Müzayede oluşturur.")
    public AuctionDTO createAuction(CreateAuctionRequest request) {
        return service.createAuction(request);
    }
    @GetMapping("/size")
    public int getAuctionsSize(){
        return repository.findAll().size();
    }
    @GetMapping("/filter/ready/size")
    public int getReadyAuctionsSize() throws InterruptedException {

        return repository.countAllByAuctionStatus(EAuctionStatus.READY);
    }

    @GetMapping("/{auctionId}")
    @Operation(summary = "ID'ye göre müzayede getir", description = "Verilen ID'ye sahip olan müzayedeyi bulur getirir. Yoksa hata döndürür.")

    public AuctionDTO getAuction(@PathVariable String auctionId) {
        return service.getAuction(auctionId);
    }


    @GetMapping("/pageable")
    @Operation(summary = "Tüm müzayedeleri getir (Kaldırılacak)", description = "Tüm müzayedeleri getirir.")
    public Page<AuctionDTO> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return service.getAll(page,size);
    }
    @GetMapping("/archive")
    public List<ArchiveEntityDTO> getAllFromArchive(){
        return service.getAllFromArchive();
    }
    @GetMapping("/page/{page}/size/{size}/search/{keyword}")
   public Page<AuctionDTO> getAllBySearchbox(@PathVariable int page,@PathVariable int size,@PathVariable String keyword){
        return service.getAllBySearchbox(page,size,keyword);
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

    @GetMapping("/filter/ready/pageable")
    @Operation(summary = "Yayındaki müzayedeler", description = "Kullanıcının sisteme giriş yapmasını sağlar.")

    public Page<AuctionDTO> getReadyAuctions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) throws InterruptedException {
        return service.getReadyAuctions(page,size);
    }

    @PutMapping("/filter/ready/auction/{auctionId}/token/{adminToken}")
    @Operation(summary = "Müzayedeyi başlatır.", description = "Teklfiflere açık hale getirir, normalde kapalıdır. ")

    public AuctionDTO setAuctionStatusToOpen(@PathVariable String auctionId, @PathVariable String adminToken) {
        return service.setAuctionStatusToOpen(auctionId, adminToken);
    }
    @PutMapping("/{auctionId}/{adminToken}/sell")
    public AuctionDTO closeAuction(@PathVariable String auctionId, @PathVariable  String adminToken){
        return service.closeAuction(auctionId,adminToken);
    }

    @DeleteMapping("/{auctionId}/token/{adminToken}")
    @Operation(summary = "Müzayede silme", description = "Admin tokeni ile müzayede siler.")

    public AuctionDTO deleteAuctionById(@PathVariable String auctionId, @PathVariable String adminToken) {
        return service.deleteAuctionById(auctionId, adminToken);
    }

    @DeleteMapping("/all")
    public void deleteAllAuctions(){
         service.deleteAll();
    }
}
