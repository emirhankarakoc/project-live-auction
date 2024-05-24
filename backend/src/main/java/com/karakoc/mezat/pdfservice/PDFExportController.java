package com.karakoc.mezat.pdfservice;

import com.karakoc.mezat.auction.Auction;
import com.karakoc.mezat.auction.AuctionRepository;
import com.karakoc.mezat.exceptions.general.BadRequestException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@AllArgsConstructor
public class PDFExportController {
    ExportManager pdfGeneratorService;
    private final AuctionRepository auctionRepository;

    @GetMapping("/openpdf/export/{id}")
    public void createPDF(HttpServletResponse response, @PathVariable String id) throws IOException {
        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=teklifler.pdf";
        response.setHeader(headerKey, headerValue);

        pdfGeneratorService.export(response,id);
    }


}
