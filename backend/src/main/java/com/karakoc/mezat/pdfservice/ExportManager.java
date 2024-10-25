package com.karakoc.mezat.pdfservice;

import com.karakoc.mezat.auction.Auction;
import com.karakoc.mezat.auction.AuctionRepository;
import com.karakoc.mezat.exceptions.general.BadRequestException;
import com.karakoc.mezat.offer.Offer;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.text.Normalizer;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
@AllArgsConstructor
public class ExportManager {

    private final AuctionRepository auctionRepository;

    public void export(HttpServletResponse response, String auctionId) throws IOException {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new BadRequestException("Auction not found."));
        List<Offer> offers = auction.getOffers();
        Collections.sort(offers, Comparator.comparingDouble(Offer::getPrice).reversed());

        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        // Belgenin kenar boşluklarını (padding) ayarlama
        float paddingLeft = 100f;
        float paddingRight = 100f;
        document.setMargins(paddingLeft, paddingRight, 0, 0);


        Font fontHeader = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        fontHeader.setSize(22);

        // WELCOME TEXT (1 line, product title)
        Paragraph headerParagraph = new Paragraph(auction.getProduct().getProductTitle().toUpperCase() + " ICIN TEKLIFLER", fontHeader);
        headerParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(headerParagraph);

        // Resmi A4 boyutunda ve ortalanmış şekilde ekleme
        try{
            Image image = Image.getInstance(new URL(auction.getProduct().getPhotoPath()));
            image.setAlignment(Image.MIDDLE);
            image.scaleToFit(PageSize.A4.getWidth() - document.leftMargin() - document.rightMargin(), PageSize.A4.getHeight() - document.topMargin() - document.bottomMargin());
            document.add(image);
        }catch (Exception e){
            throw new BadRequestException("PDF Exporter service problem. Possibly internet connection failed. Try again later.");
        }


        // Tekliflerin olduğu tablo oluşturma
        PdfPTable table = new PdfPTable(2); // 2 sütunlu bir tablo oluştur
        table.setWidthPercentage(100); // Tabloyu belgenin genişliği kadar genişlet
        table.setSpacingBefore(20f); // Tablo öncesinde 20px boşluk bırak

        // Tablo başlıkları ekleme
        table.addCell(new Phrase("Teklifi Veren", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        table.addCell(new Phrase("Miktari", FontFactory.getFont(FontFactory.HELVETICA_BOLD)));

        // Tekliflerin tabloya eklenmesi
        for (Offer offer : offers) {
            table.addCell(new Phrase(offer.getFullname()));
            table.addCell(new Phrase(String.valueOf(offer.getPrice())));
        }

        // Tabloyu belgeye ekleme
        document.add(table);

        // Belgeyi kapat
        document.close();

        // Yanıt akışını kapat
        response.getOutputStream().close();
    }

}
