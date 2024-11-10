package com.assignment.pdf_generator.Service;

import com.assignment.pdf_generator.Dto.InvoiceRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class PdfGenerationService {

    @Autowired
    private SpringTemplateEngine templateEngine;

    public void generatePdf(InvoiceRequestDTO request, String uniqueId) throws IOException {

        Context context = new Context();
        context.setVariable("seller", request.getSeller());
        context.setVariable("sellerGstin", request.getSellerGstin());
        context.setVariable("sellerAddress", request.getSellerAddress());
        context.setVariable("buyer", request.getBuyer());
        context.setVariable("buyerGstin", request.getBuyerGstin());
        context.setVariable("buyerAddress", request.getBuyerAddress());
        context.setVariable("items", request.getItems());

        String htmlContent = templateEngine.process("invoiceTemplate", context);

        File directory = new File("generated_pdfs");
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new IOException("Failed to create directory: generated_pdfs");
            }
        }

        String outputPath = "generated_pdfs/" + uniqueId + ".pdf";


        try (OutputStream outputStream = new FileOutputStream(outputPath)) {
            ITextRenderer renderer = new ITextRenderer();


            renderer.setDocumentFromString(htmlContent);
            renderer.layout();
            renderer.createPDF(outputStream);
        } catch (Exception e) {
            throw new IOException("Error while generating PDF", e);
        }
    }
}
