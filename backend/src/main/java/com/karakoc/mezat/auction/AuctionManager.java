package com.karakoc.mezat.auction;
import com.karakoc.mezat.exceptions.general.BadRequestException;
import com.karakoc.mezat.exceptions.general.NotfoundException;
import com.karakoc.mezat.product.Product;
import com.karakoc.mezat.product.ProductAuctionStatus;
import com.karakoc.mezat.product.ProductRepository;
import com.karakoc.mezat.user.User;
import com.karakoc.mezat.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.karakoc.mezat.auction.ArchiveEntity.archivesToDTO;
import static com.karakoc.mezat.auction.Auction.*;
import static com.karakoc.mezat.product.Product.productToDTO;

@Service
@AllArgsConstructor
@Slf4j
public class AuctionManager implements AuctionService{
    private final AuctionRepository auctionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final Archive archive;



    public AuctionDTO createAuction(CreateAuctionRequest request) {
        User.onlyAdminAndUserIsPresentValidation(userRepository.findUserByToken(request.getAdminToken()));
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new BadRequestException("Product not found."));
        //validations
        Auction auction = Auction.createAuction(request);
      try{
          auction.setProduct(product);
          auctionRepository.save(auction);}
      catch (Exception e){
          throw new BadRequestException("An error occured. Must be duplicate entry.");}

        AuctionDTO dto = auctionToDTO(auction);
        dto.setProduct(productToDTO(product));
        return dto;
    }

    @Transactional
    public AuctionDTO setAuctionStatusToOpen(String auctionId, String adminToken) {
        User.onlyAdminAndUserIsPresentValidation(userRepository.findUserByToken(adminToken));
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new NotfoundException("Auction not found."));
        Product pro = auction.getProduct();
        if (pro.getProductStatus().equals(ProductAuctionStatus.YAYINDA)){ throw new BadRequestException("You can't create an auction for this product. Because there is already an open auction.");}

        log.info("bi tane auctionu ready yaptik.");
        auction.setAuctionStatus(EAuctionStatus.READY);
        pro.setProductStatus(ProductAuctionStatus.YAYINDA);
        auctionRepository.save(auction);
        productRepository.save(pro);
        return auctionToDTO(auction);
    }


    @Transactional
    //sell product.
    public AuctionDTO closeAuction(String auctionId,String adminToken){
        User.onlyAdminAndUserIsPresentValidation(userRepository.findUserByToken(adminToken));
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new NotfoundException("Auction not found."));
        log.info("bi tane auctionu bitirdik. haneye hayirli olsun.");
        auction.setAuctionStatus(EAuctionStatus.ENDED);
        Product prod = auction.getProduct();
        prod.setProductStatus(ProductAuctionStatus.HAZIR);
        productRepository.save(prod);


        ArchiveEntity archiveEntity = new ArchiveEntity();
        archiveEntity.setId(auction.getId());
        archiveEntity.setProductTitle(prod.getProductTitle());
        archiveEntity.setPhotoPath(prod.getPhotoPath());
        archiveEntity.setOwner(prod.getOwner());
        archiveEntity.setCreateddatetime(auction.getCreateddatetime());
        archiveEntity.setEndDate(auction.getEndDate());
        archiveEntity.setPrice(auction.getPrice());
        archiveEntity.setDescription(auction.getDescription());
        archiveEntity.setAuctionStatus(auction.getAuctionStatus());
        log.info("Saving to archive: {}", archiveEntity);
        archive.save(archiveEntity);

        auctionRepository.delete(auction);
        return auctionToDTO(auction);

    }

    public AuctionDTO getAuction(String auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new NotfoundException("Auction not found."));
        return auctionToDTO(auction);
    }

    public Page<AuctionDTO> getCreatedAuctions(String adminToken,int page, int size) {
        User.onlyAdminAndUserIsPresentValidation(userRepository.findUserByToken(adminToken));
        Pageable pageable = PageRequest.of(page, size, Sort.by("createddatetime").ascending());
        Page<Auction> auctionPage = auctionRepository.findAllByAuctionStatus(EAuctionStatus.CREATED,pageable);
        List<AuctionDTO> auctionDTOList = auctionsToDTO(auctionPage.getContent());
        return new PageImpl<>(auctionDTOList,pageable,auctionPage.getTotalElements());
    }

    public Page<AuctionDTO> getReadyAuctions(int page,int size) throws InterruptedException {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createddatetime").ascending());
        Page<Auction> auctionPage = auctionRepository.findAllByAuctionStatus(EAuctionStatus.READY,pageable);
        List<AuctionDTO> auctionDTOList = auctionsToDTO(auctionPage.getContent());
        return new PageImpl<>(auctionDTOList,pageable,auctionPage.getTotalElements());
    }

    public Page<AuctionDTO> getEndedAuctions(String adminToken,int page,int size) {
        User.onlyAdminAndUserIsPresentValidation(userRepository.findUserByToken(adminToken));
//        List<Auction> auctions = auctionRepository.findAll();
//        return getAllAuctionsByStatus(EAuctionStatus.ENDED, auctions);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createddatetime").ascending());
        Page<Auction> auctionPage = auctionRepository.findAllByAuctionStatus(EAuctionStatus.ENDED,pageable);
        List<AuctionDTO> auctionDTOList = auctionsToDTO(auctionPage.getContent());
        return new PageImpl<>(auctionDTOList,pageable,auctionPage.getTotalElements());



    }

    public Page<AuctionDTO> getAll(int page, int size) {
        try{
            Pageable pageable = PageRequest.of(page, size, Sort.by("createddatetime").ascending());
            Page<Auction> auctionPage = auctionRepository.findAll(pageable);
            List<AuctionDTO> auctionsDTO = auctionsToDTO(auctionPage.getContent());
            return new PageImpl<>(auctionsDTO,pageable,auctionPage.getTotalElements());
        }
        catch (Exception e){
            throw new BadRequestException("An error occured.");
        }
    }
    public List<ArchiveEntityDTO> getAllFromArchive() {
        List<ArchiveEntity> archiveEntities = archive.findAll();
        return archivesToDTO(archiveEntities);
    }
    public Page<AuctionDTO> getAllBySearchbox(int page,int size, String keyword){
        Pageable pageable = PageRequest.of(page, size, Sort.by("createddatetime").ascending());
        Page<Auction> auctionPage = auctionRepository.findByProductProductTitleContains(pageable,keyword);
        List<AuctionDTO> auctionsDTO = auctionsToDTO(auctionPage.getContent());
        return new PageImpl<>(auctionsDTO,pageable,auctionPage.getTotalElements());
    }

    @Transactional
    public AuctionDTO deleteAuctionById(String auctionId, String adminToken) {
        User.onlyAdminAndUserIsPresentValidation(userRepository.findUserByToken(adminToken));
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new NotfoundException("Auction not found."));
        AuctionDTO dto = auctionToDTO(auction);
        auctionRepository.delete(auction);
        return dto;
    }
    public void deleteAll(){
        auctionRepository.deleteAll();
    }

}
