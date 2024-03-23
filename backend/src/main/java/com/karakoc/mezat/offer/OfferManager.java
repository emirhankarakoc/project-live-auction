package com.karakoc.mezat.offer;

import com.corundumstudio.socketio.SocketIOServer;
import com.karakoc.mezat.auction.Auction;
import com.karakoc.mezat.auction.AuctionRepository;
import com.karakoc.mezat.exceptions.general.NotfoundException;
import com.karakoc.mezat.user.User;
import com.karakoc.mezat.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.karakoc.mezat.auction.Auction.auctionValidationsForNewOffers;
import static com.karakoc.mezat.offer.Offer.offerToDTO;
import static com.karakoc.mezat.offer.Offer.offersToDTO;

@Service
@AllArgsConstructor
public class OfferManager implements OfferService {
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final SocketIOServer socketIOServer;



    public OfferDTO createOffer(CreateOfferRequest request) {
        Auction auction = auctionRepository.findById(request.getAuctionId())
                .orElseThrow(() -> new NotfoundException("Auction not found"));

        User user = userRepository.findUserByToken(request.getUserToken())
                .orElseThrow(() -> new NotfoundException("User not found."));

        auctionValidationsForNewOffers(auction, request);

        // Teklif oluşturma işlemi
        Offer offer = Offer.createOffer(request);
        OfferDTO dto = offerToDTO(offer);
        offerRepository.save(offer);
        auction.setPrice(request.getPrice());
        auction.getOffers().add(offer);
        auction.setEndDate(auction.getEndDate().plusMinutes(10));
        auctionRepository.save(auction);

        // Sokete yeni teklif mesajı gönderme işlemi
        socketIOServer.getBroadcastOperations().sendEvent("new_offer", "Yeni bir teklif geldi.");

        return dto;
    }


    public List<OfferDTO> getAll(){
        return offersToDTO(offerRepository.findAll());
    }
}
