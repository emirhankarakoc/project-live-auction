package com.karakoc.mezat.offer;

import com.corundumstudio.socketio.SocketIOServer;
import com.karakoc.mezat.auction.Auction;
import com.karakoc.mezat.auction.AuctionRepository;
import com.karakoc.mezat.exceptions.general.NotfoundException;
import com.karakoc.mezat.user.User;
import com.karakoc.mezat.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.karakoc.mezat.auction.Auction.auctionValidationsForNewOffers;
import static com.karakoc.mezat.offer.Offer.offerToDTO;
import static com.karakoc.mezat.offer.Offer.offersToDTO;

@Service
@Slf4j
@AllArgsConstructor
public class OfferManager implements OfferService {
    private final OfferRepository offerRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;
    private final SocketIOServer socketIOServer;


    @Transactional
    public OfferDTO createOffer(double price, String auctionId,String userToken) {

        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new NotfoundException("Auction not found"));

        User user = userRepository.findUserByToken(userToken)
                .orElseThrow(() -> new NotfoundException("User not found."));

        auctionValidationsForNewOffers(auction, price);

        // Teklif oluşturma işlemi
        Offer offer = Offer.createOffer(price,auctionId,userToken);
        offer.setFullname(user.getFirstname() + " " +user.getLastname());

        OfferDTO dto = offerToDTO(offer);
        offerRepository.save(offer);
        auction.setPrice(price);
        auction.getOffers().add(offer);
        auction.setEndDate(auction.getEndDate().plusMinutes(10));
        auctionRepository.save(auction);

        // Sokete yeni teklif mesajı gönderme işlemi
        socketIOServer.getBroadcastOperations().sendEvent("new_offer", "Yeni bir teklif geldi.");
        log.info("birileri teklif verdi.");

        return dto;
    }


    public List<OfferDTO> getAll(){
        return offersToDTO(offerRepository.findAll());
    }


    public List<OfferDTO> getOffersByAuctionId(String auctionId) {
        Auction auction = auctionRepository.findById(auctionId).orElseThrow(() -> new NotfoundException("Auction not found."));
        List<OfferDTO> offers = offersToDTO(auction.getOffers());

        // Fiyata göre azalan şekilde sırala
        List<OfferDTO> sortedOffers = offers.stream()
                .sorted(Comparator.comparingDouble(OfferDTO::getPrice).reversed()) // Fiyata göre azalan sıralama
                .collect(Collectors.toList());

        return sortedOffers;
    }


}
