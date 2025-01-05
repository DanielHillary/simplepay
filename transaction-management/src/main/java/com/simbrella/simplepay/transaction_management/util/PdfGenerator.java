package com.simbrella.simplepay.transaction_management.util;

import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.element.Table;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.simbrella.simplepay.transaction_management.model.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
public class PdfGenerator {

    private final LoggingService loggingService;

    public byte[] generateSingleTransactionPdf(Transaction transaction) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Add Header
            document.add(new Paragraph("Transaction Statement")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(18)
                    .setBold());

            document.add(new Paragraph("\n")); // Add some space

            // Add Transaction Details in a Table
            Table table = new Table(new float[]{1, 2}); // Two columns: Label and Value
            table.setWidth(100);

            // Add table rows
            table.addCell("Transaction ID:");
            table.addCell(transaction.getId().toString());

            table.addCell("User ID:");
            table.addCell(String.valueOf(transaction.getDebitAccountId()));

            table.addCell("Amount:");
            table.addCell(String.format("$%.2f", transaction.getAmount()));

            table.addCell("Description:");
            table.addCell(transaction.getDescription());

            table.addCell("Transaction Date:");
            table.addCell(transaction.getTransactionTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            document.add(table);

            // Add Footer
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("Thank you for using our service!")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setItalic());

        } catch (Exception e) {
            loggingService.logError("Error generating transaction PDF", String.valueOf(transaction.getId()), e.getMessage());
            throw new RuntimeException("Error generating transaction PDF", e);
        }

        return outputStream.toByteArray();
    }
}
