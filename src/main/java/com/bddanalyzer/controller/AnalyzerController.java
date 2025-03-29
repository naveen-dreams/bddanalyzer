package com.bddanalyzer.controller;

import com.bddanalyzer.model.AnalysisResults;
import com.bddanalyzer.service.BddFrameworkAnalyzer;
import com.bddanalyzer.service.ReportGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AnalyzerController {

    private final BddFrameworkAnalyzer analyzer;
    private final ReportGenerator reportGenerator;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/analyze")
    public String analyze(@RequestParam("project_path") String projectPath,
                         Model model,
                         HttpSession session) {
        try {
            String path = projectPath.isEmpty() ? "Team02_SeleniumNinjas" : projectPath;
            AnalysisResults results = analyzer.analyzeBddFramework(path);
            
            String projectName = Paths.get(path).getFileName().toString();
            
            session.setAttribute("analysis_results", results);
            session.setAttribute("project_path", path);
            session.setAttribute("project_name", projectName);
            
            model.addAttribute("results", results);
            model.addAttribute("project_path", path);
            model.addAttribute("project_name", projectName);
            
            return "results";
            
        } catch (Exception e) {
            log.error("Error analyzing project", e);
            model.addAttribute("error", e.getMessage());
            return "index";
        }
    }

    @GetMapping("/health-overview")
    public String healthOverview(Model model, HttpSession session) {
        AnalysisResults results = (AnalysisResults) session.getAttribute("analysis_results");
        String projectPath = (String) session.getAttribute("project_path");
        String projectName = (String) session.getAttribute("project_name");
        
        if (results == null) {
            model.addAttribute("error", "Please analyze a project first");
            return "redirect:/";
        }
        
        model.addAttribute("results", results);
        model.addAttribute("project_path", projectPath);
        model.addAttribute("project_name", projectName);
        
        return "health_overview";
    }

    @GetMapping("/download-guide")
    public ResponseEntity<Resource> downloadGuide(HttpSession session) {
        try {
            AnalysisResults results = (AnalysisResults) session.getAttribute("analysis_results");
            String projectPath = (String) session.getAttribute("project_path");
            String projectName = (String) session.getAttribute("project_name");
            
            if (results == null) {
                return ResponseEntity.badRequest().build();
            }
            
            Resource pdfResource = reportGenerator.generateScoringGuide(results, projectPath);
            
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, 
                            "attachment; filename=\"bdd_scoring_guide_" + projectName + ".pdf\"")
                    .body(pdfResource);
                    
        } catch (Exception e) {
            log.error("Error generating scoring guide", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/api/analyze")
    @ResponseBody
    public ResponseEntity<?> apiAnalyze(@RequestBody Map<String, String> request) {
        try {
            String projectPath = request.get("project_path");
            if (projectPath == null || projectPath.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(Map.of("error", "Project path is required"));
            }
            
            AnalysisResults results = analyzer.analyzeBddFramework(projectPath);
            return ResponseEntity.ok(Map.of("results", results));
            
        } catch (Exception e) {
            log.error("API error", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }
} 