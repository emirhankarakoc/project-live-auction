package com.karakoc.mezat.auction;

import com.karakoc.mezat.user.User;
import com.karakoc.mezat.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.karakoc.mezat.auction.Auction.auctionToDTO;
import static com.karakoc.mezat.auction.Auction.auctionsToDTO;

@Service
@AllArgsConstructor
public class AuctionManager implements AuctionService{
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    public AuctionDTO createAuction(CreateAuctionRequest request){
        User.onlyAdminAndUserIsPresentValidation(userRepository.findUserByToken(request.getAdminToken()));

        //validations
        Auction auction = Auction.createAuction(request);
       AuctionDTO dto =  auctionToDTO(auction);
       auctionRepository.save(auction);
       return dto;
    }

    public List<AuctionDTO> getAll(){
        return auctionsToDTO(auctionRepository.findAll());
    }

}
