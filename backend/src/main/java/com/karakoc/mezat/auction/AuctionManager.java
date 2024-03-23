package com.karakoc.mezat.auction;

import com.karakoc.mezat.exceptions.general.BadRequestException;
import com.karakoc.mezat.exceptions.general.NotfoundException;
import com.karakoc.mezat.product.Product;
import com.karakoc.mezat.product.ProductDTO;
import com.karakoc.mezat.product.ProductRepository;
import com.karakoc.mezat.user.User;
import com.karakoc.mezat.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.karakoc.mezat.auction.Auction.*;
import static com.karakoc.mezat.product.Product.productDTOS;
import static com.karakoc.mezat.product.Product.productToDTO;

@Service
@AllArgsConstructor
public class AuctionManager implements AuctionService{
    private final AuctionRepository auctionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    public AuctionDTO createAuction(CreateAuctionRequest request) {
        User.onlyAdminAndUserIsPresentValidation(userRepository.findUserByToken(request.getAdminToken()));
        Product product = productRepository.findById(request.getProductId()).orElseThrow(() -> new BadRequestException("Product not found."));
        //validations
        Auction auction = Auction.createAuction(request);
        auction.setProduct(product);
        auctionRepository.save(auction);
        AuctionDTO dto = auctionToDTO(auction);
        dto.setProductDTO(productToDTO(product));
        return dto;
    }

    public AuctionDTO setAuctionStatusToOpen(String auctionId, String adminToken) {
        User.onlyAdminAndUserIsPresentValidation(userRepository.findUserByToken(adminToken));
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new NotfoundException("Auction not found."));
        auction.setAuctionStatus(EAuctionStatus.READY);
        auctionRepository.save(auction);
        return auctionToDTO(auction);
    }

    public AuctionDTO getAuction(String auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new NotfoundException("Auction not found."));
        return auctionToDTO(auction);
    }

    public Page<AuctionDTO> getCreatedAuctions(String adminToken,int page, int size) {
        User.onlyAdminAndUserIsPresentValidation(userRepository.findUserByToken(adminToken));
        Pageable pageable = PageRequest.of(page, size, Sort.by("createddatetime").descending());
        Page<Auction> auctionPage = auctionRepository.findAllByAuctionStatus(EAuctionStatus.CREATED,pageable);
        List<AuctionDTO> auctionDTOList = auctionsToDTO(auctionPage.getContent());
        return new PageImpl<>(auctionDTOList,pageable,auctionPage.getTotalElements());
    }

    public Page<AuctionDTO> getReadyAuctions(int page,int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createddatetime").descending());
        Page<Auction> auctionPage = auctionRepository.findAllByAuctionStatus(EAuctionStatus.READY,pageable);
        List<AuctionDTO> auctionDTOList = auctionsToDTO(auctionPage.getContent());
        return new PageImpl<>(auctionDTOList,pageable,auctionPage.getTotalElements());
    }

    public Page<AuctionDTO> getEndedAuctions(String adminToken,int page,int size) {
        User.onlyAdminAndUserIsPresentValidation(userRepository.findUserByToken(adminToken));
//        List<Auction> auctions = auctionRepository.findAll();
//        return getAllAuctionsByStatus(EAuctionStatus.ENDED, auctions);
        Pageable pageable = PageRequest.of(page, size, Sort.by("createddatetime").descending());
        Page<Auction> auctionPage = auctionRepository.findAllByAuctionStatus(EAuctionStatus.ENDED,pageable);
        List<AuctionDTO> auctionDTOList = auctionsToDTO(auctionPage.getContent());
        return new PageImpl<>(auctionDTOList,pageable,auctionPage.getTotalElements());



    }

    public Page<AuctionDTO> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createddatetime").descending());
        Page<Auction> auctionPage = auctionRepository.findAll(pageable);
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

}
