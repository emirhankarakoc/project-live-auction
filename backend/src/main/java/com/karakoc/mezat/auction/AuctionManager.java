package com.karakoc.mezat.auction;

import com.karakoc.mezat.exceptions.general.BadRequestException;
import com.karakoc.mezat.exceptions.general.NotfoundException;
import com.karakoc.mezat.product.Product;
import com.karakoc.mezat.product.ProductRepository;
import com.karakoc.mezat.user.User;
import com.karakoc.mezat.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.karakoc.mezat.auction.Auction.*;

@Service
@AllArgsConstructor
public class AuctionManager implements AuctionService{
    private final AuctionRepository auctionRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public AuctionDTO createAuction(CreateAuctionRequest request){
        User.onlyAdminAndUserIsPresentValidation(userRepository.findUserByToken(request.getAdminToken()));
        Product product = productRepository.findById(request.getProductId()).orElseThrow(()-> new BadRequestException("Product not found."));
        //validations
        Auction auction = Auction.createAuction(request);
        auction.setProduct(product);
       AuctionDTO dto =  auctionToDTO(auction);
       auctionRepository.save(auction);
       return dto;
    }
    public AuctionDTO getAuction(String id){
        Auction auction = auctionRepository.findById(id).orElseThrow(()-> new NotfoundException("Auction not found."));
        return auctionToDTO(auction);
    }
    public List<AuctionDTO> getReadyAuctions(String adminToken){
        User.onlyAdminAndUserIsPresentValidation(userRepository.findUserByToken(adminToken));

        List<Auction> auctions = auctionRepository.findAll();
        return getAllAuctionsByStatus(EAuctionStatus.READY,auctions);
    }
    public List<AuctionDTO> getOpenedAuctions(){
        List<Auction> auctions = auctionRepository.findAll();
        return getAllAuctionsByStatus(EAuctionStatus.OPEN,auctions);
    }
    public List<AuctionDTO> getClosedAuctions(String adminToken){
        User.onlyAdminAndUserIsPresentValidation(userRepository.findUserByToken(adminToken));
        List<Auction> auctions = auctionRepository.findAll();
        return getAllAuctionsByStatus(EAuctionStatus.ENDED,auctions);
    }



    public List<AuctionDTO> getAll(){
        return auctionsToDTO(auctionRepository.findAll());
    }

}
