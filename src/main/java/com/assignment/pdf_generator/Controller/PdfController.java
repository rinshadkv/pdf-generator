package com.assignment.pdf_generator.Controller;

import com.assignment.pdf_generator.Dto.InvoiceRequestDTO;
import com.assignment.pdf_generator.Service.PdfGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    private PdfGenerationService pdfService;

    @PostMapping("/generate")
    public ResponseEntity<String> generatePdf(@RequestBody InvoiceRequestDTO request) {

        try {

            String uniqueId = DigestUtils.md5DigestAsHex(request.toString().getBytes());

            File pdfFile = new File("generated_pdfs/" + uniqueId + ".pdf");

            if (pdfFile.exists()) {

                return ResponseEntity.ok("File already exists. Download from /api/pdf/download/" + uniqueId);
            }


            pdfService.generatePdf(request, uniqueId);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("PDF Generated successfully. Download from /api/pdf/download/" + uniqueId);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating PDF: " + e.getMessage());
        }
    }

    @GetMapping("/download/{uniqueId}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable String uniqueId) {
        try {
            File pdfFile = new File("generated_pdfs/" + uniqueId + ".pdf");

            if (!pdfFile.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(null);
            }

            byte[] pdfBytes = java.nio.file.Files.readAllBytes(pdfFile.toPath());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", uniqueId + ".pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfBytes);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
