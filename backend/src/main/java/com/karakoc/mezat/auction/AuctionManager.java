package com.karakoc.mezat.auction;

import com.karakoc.mezat.exceptions.general.BadRequestException;
import com.karakoc.mezat.exceptions.general.NotfoundException;
import com.karakoc.mezat.product.Product;
import com.karakoc.mezat.product.ProductRepository;
import com.karakoc.mezat.user.User;
import com.karakoc.mezat.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.karakoc.mezat.auction.Auction.*;

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
        AuctionDTO dto = auctionToDTO(auction);
        auctionRepository.save(auction);
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

    public List<AuctionDTO> getCreatedAuctions(String adminToken) {
        User.onlyAdminAndUserIsPresentValidation(userRepository.findUserByToken(adminToken));

        List<Auction> auctions = auctionRepository.findAll();
        return getAllAuctionsByStatus(EAuctionStatus.CREATED, auctions);
    }

    public List<AuctionDTO> getReadyAuctions() {
        List<Auction> auctions = auctionRepository.findAll();
        return getAllAuctionsByStatus(EAuctionStatus.READY, auctions);
    }

    public List<AuctionDTO> getEndedAuctions(String adminToken) {
        User.onlyAdminAndUserIsPresentValidation(userRepository.findUserByToken(adminToken));
        List<Auction> auctions = auctionRepository.findAll();
        return getAllAuctionsByStatus(EAuctionStatus.ENDED, auctions);
    }

    public List<AuctionDTO> getAll() {
        return auctionsToDTO(auctionRepository.findAll());
    }

    @Transactional
    public AuctionDTO deleteAuctionById(String auctionId, String adminToken) {
        User.onlyAdminAndUserIsPresentValidation(userRepository.findUserByToken(adminToken));
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new NotfoundException("Auction not found."));
        auctionRepository.delete(auction);
        return auctionToDTO(auction);
    }

}
