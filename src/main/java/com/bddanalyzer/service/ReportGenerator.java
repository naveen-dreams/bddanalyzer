package com.bddanalyzer.service;

import com.bddanalyzer.model.AnalysisResults;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class ReportGenerator {

    public Resource generateScoringGuide(AnalysisResults results, String projectPath) {
        try {
            Document document = new Document();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);
            
            document.open();
            
            // Add title
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("BDD Framework Analysis Report", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);
            
            // Add project info
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
            document.add(new Paragraph("Project Path: " + projectPath, normalFont));
            document.add(new Paragraph("Analysis Date: " + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), normalFont));
            document.add(new Paragraph("Overall Score: " + results.getOverallScore(), normalFont));
            document.add(Chunk.NEWLINE);
            
            // Add sections
            addSection(document, "Feature Files", results.getFeatureFiles());
            addSection(document, "Step Definitions", results.getStepDefinitions());
            addSection(document, "Framework Structure", results.getFrameworkStructure());
            addSection(document, "Test Coverage", results.getTestCoverage());
            addSection(document, "BDD Implementation", results.getBddImplementation());
            addSection(document, "Framework Architecture", results.getFrameworkArchitecture());
            addSection(document, "Code Quality", results.getCodeQuality());
            addSection(document, "Selenium Implementation", results.getSeleniumImplementation());
            addSection(document, "Browser Execution", results.getBrowserExecution());
            addSection(document, "Page Objects", results.getPageObjects());
            addSection(document, "Framework Health", results.getFrameworkHealth());
            
            // Add recommendations
            Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            document.add(new Paragraph("Recommendations", sectionFont));
            for (String recommendation : results.getRecommendations()) {
                document.add(new Paragraph("• " + recommendation, normalFont));
            }
            
            document.close();
            
            return new ByteArrayResource(outputStream.toByteArray());
            
        } catch (Exception e) {
            log.error("Error generating PDF report", e);
            throw new RuntimeException("Failed to generate PDF report", e);
        }
    }
    
    private void addSection(Document document, String title, Object sectionData) throws DocumentException {
        Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);
        
        document.add(new Paragraph(title, sectionFont));
        document.add(new Paragraph(sectionData.toString(), normalFont));
        document.add(Chunk.NEWLINE);
    }
} 