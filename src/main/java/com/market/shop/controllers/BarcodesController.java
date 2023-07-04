package com.market.shop.controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import java.awt.Font;

@RestController
@RequestMapping("/barcodes")
public class BarcodesController {

    private static final int MATRIX_ROWS = 4; // Number of rows in the matrix
    private static final int MATRIX_COLUMNS = 5; // Number of columns in the matrix

/*     @GetMapping(value = "/barbecue/EAN13/{barcode}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<BufferedImage> barbecueEAN13Barcode(@PathVariable("barcode") long barcode) throws Exception {
        System.out.println(formatIdToEAN13Format(barcode));
        BufferedImage barcodeImage = generateEAN13BarcodeImage(formatIdToEAN13Format(barcode));
        generatePdfFile(barcodeImage);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(barcodeImage);
    } */

    @GetMapping(value = "/barbecue/EAN13/{barcode}")
    public ResponseEntity<byte[]> barbecueEAN13Barcode(@PathVariable("barcode") long barcode) throws Exception {
        System.out.println(formatIdToEAN13Format(barcode));
        BufferedImage barcodeImage = generateEAN13BarcodeImage(formatIdToEAN13Format(barcode));
        ByteArrayOutputStream baos = generatePdfFile(barcodeImage);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "barcodes.pdf");
        headers.setContentLength(baos.size());

        return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
    }

    public static BufferedImage generateEAN13BarcodeImage(String barcodeText) throws Exception {
        Barcode barcode = BarcodeFactory.createEAN13(barcodeText);
        barcode.setBarHeight(60);
        barcode.setBarWidth(2);
        barcode.setFont(new Font("Default", Font.BOLD, 20));

        BufferedImage barcodeImage = BarcodeImageHandler.getImage(barcode);
        return barcodeImage;
    }

    @Bean
    public HttpMessageConverter<BufferedImage> createImageHttpMessageConverter() {
        return new BufferedImageHttpMessageConverter();
    }

    public static String formatIdToEAN13Format(long id) {
        // Format the number with leading zeros to make it 11 characters long
        String formattedNumber = String.format("%012d", id);
        return formattedNumber;
    }

    public ByteArrayOutputStream generatePdfFile(BufferedImage image) throws IOException {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);

        int imageWidth = image.getWidth();
        int imageHeight = image.getHeight();

        float pdfWidth = page.getMediaBox().getWidth();
        float pdfHeight = page.getMediaBox().getHeight();

        int rows = (int) Math.floor((pdfHeight - 5) / (20 + 5));
        int cols = (int) Math.floor((pdfWidth - 5) / (60 + 5));

        float cellWidth = (pdfWidth - (5 * (cols - 1))) / cols;
        float cellHeight = (pdfHeight - (5 * (rows - 1))) / rows;

        PDPageContentStream contentStream = new PDPageContentStream(document, page);

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                float x = col * (cellWidth + 5);
                float y = pdfHeight - ((row + 1) * (cellHeight + 5));

                PDImageXObject pdfImage = PDImageXObject.createFromByteArray(document, toByteArray(image), "barcode");
                contentStream.drawImage(pdfImage, x, y, 60, 20);
            }
        }

        contentStream.close();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        document.save(baos);
        document.close();

        return baos;
    }




    private byte[] toByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        baos.flush();
        byte[] imageBytes = baos.toByteArray();
        baos.close();
        return imageBytes;
    }
}
//Instead of return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(barcodeImage); i want to give the generated pdf in response that is directly downloaded