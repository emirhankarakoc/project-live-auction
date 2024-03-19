package com.karakoc.mezat.offer;

import java.util.List;

public interface OfferService {
    OfferDTO createOffer(CreateOfferRequest request);
    List<OfferDTO> getAll();
}
