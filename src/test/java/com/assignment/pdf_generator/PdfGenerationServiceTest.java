package com.assignment.pdf_generator;


import com.assignment.pdf_generator.Dto.InvoiceRequestDTO;
import com.assignment.pdf_generator.Service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class PdfGenerationServiceTest {

    @Autowired
    private PdfGenerationService pdfGenerationService;

    private InvoiceRequestDTO request;

    @BeforeEach
    void setUp() {
        request = new InvoiceRequestDTO();
        request.setSeller("XYZ Pvt. Ltd.");
        request.setSellerGstin("29AABBCCDD121ZD");
        request.setSellerAddress("New Delhi, India");
        request.setBuyer("Vedant Computers");
        request.setBuyerGstin("29AABBCCDD131ZD");
        request.setBuyerAddress("Mumbai, India");

        InvoiceRequestDTO.Item item = new InvoiceRequestDTO.Item();
        item.setName("Product 1");
        item.setQuantity("12 Nos");
        item.setRate(123.00);
        item.setAmount(1476.00);
        request.setItems(List.of(item));
    }

    @Test
    void testGeneratePdf() {
        String uniqueId = "test_invoice";

        assertDoesNotThrow(() -> {
            pdfGenerationService.generatePdf(request, uniqueId);
        });


        File pdfFile = new File("generated_pdfs/" + uniqueId + ".pdf");
        assertTrue(pdfFile.exists(), "PDF should be generated");
        assertTrue(pdfFile.length() > 0, "PDF file should not be empty");


        pdfFile.delete();
    }
}
