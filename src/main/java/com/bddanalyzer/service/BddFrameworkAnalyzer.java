package com.bddanalyzer.service;

import com.bddanalyzer.model.AnalysisResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class BddFrameworkAnalyzer {

    public AnalysisResults analyzeBddFramework(String projectPath) {
        log.debug("Starting analysis of project at: {}", projectPath);

        AnalysisResults results = new AnalysisResults();
        Path path = Path.of(projectPath);

        analyzeSeleniumImplementation(path, results);
        analyzeBrowserExecution(path, results);
        analyzePageObjects(path, results);
        analyzeCodeQuality(path, results);
        calculateFrameworkHealth(results);
        generateRecommendations(results);
        calculateOverallScore(results);

        log.debug("Analysis completed with overall score: {}", results.getOverallScore());
        return results;
    }

    private void analyzeCodeQuality(Path projectPath, AnalysisResults results) {
        // TODO: Implement code quality analysis
    }

    private double analyzeSeleniumImplementation(Path projectPath, AnalysisResults results) {
        double score = 0.0;
        Path pageObjectsPath = projectPath.resolve("src/test/java/pageObjects");
        Path testNGPath = projectPath.resolve("testng.xml");
        Path screenshotPath = projectPath.resolve("src/test/java/utils/Screenshot.java");

        if (Files.exists(pageObjectsPath)) {
            try (Stream<Path> paths = Files.walk(pageObjectsPath)) {
                List<Path> pageObjects = paths
                    .filter(p -> p.toString().endsWith(".java"))
                    .collect(Collectors.toList());

                if (!pageObjects.isEmpty()) {
                    double totalScore = 0;
                    boolean hasUniformLocators = true;
                    boolean usesFindBy = false;
                    boolean usesByLocator = false;

                    for (Path pageObject : pageObjects) {
                        String content = new String(Files.readAllBytes(pageObject));
                        if (content.contains("@FindBy")) usesFindBy = true;
                        if (content.contains("By.")) usesByLocator = true;

                        // Check for locator uniformity
                        if (usesFindBy && usesByLocator) {
                            hasUniformLocators = false;
                            break;
                        }
                    }

                    // Check for screenshot implementation in hooks
                    Path hooksPath = projectPath.resolve("src/test/java/hooks");
                    final double[] scoreIncrement = {0};
                    if (Files.exists(hooksPath)) {
                        Files.walk(hooksPath)
                            .filter(p -> p.toString().endsWith(".java"))
                            .forEach(p -> {
                                try {
                                    String content = new String(Files.readAllBytes(p));
                                    if (content.contains("TakesScreenshot") && content.contains("getScreenshotAs")) {
                                        scoreIncrement[0] = 25;
                                        results.getSeleniumImplementation().setScreenshotCapability(true);
                                    }
                                } catch (IOException e) {
                                    log.error("Error reading hook file", e);
                                }
                            });
                    }

                    // Scoring criteria
                    if (hasUniformLocators) totalScore += 25;
                    if (Files.exists(testNGPath)) {
                        String testNGContent = new String(Files.readAllBytes(testNGPath));
                        boolean hasParallel = testNGContent.contains("parallel=\"") || testNGContent.contains("parallel='");

                        // Updated cross-browser detection logic
                        boolean hasCrossBrowser = testNGContent.contains("<test name=\"FirefoxTest\"") || 
                                                testNGContent.contains("<test name=\"SafariTest\"") ||
                                                (testNGContent.contains("<parameter name=\"browser\"") && 
                                                 testNGContent.contains("value=\"FIREFOX\"") ||
                                                 testNGContent.contains("value=\"SAFARI\""));

                        if (hasParallel) {
                            totalScore += 25;
                            results.getBrowserExecution().setParallelExecution(true);
                        } else {
                            results.getBrowserExecution().setParallelExecution(false);
                        }

                        if (hasCrossBrowser) {
                            totalScore += 25;
                            results.getBrowserExecution().setCrossBrowserTesting(true);
                        } else {
                            results.getBrowserExecution().setCrossBrowserTesting(false);
                        }
                    }
                    totalScore += scoreIncrement[0];
                    if (Files.exists(projectPath.resolve("src/test/java/driverFactory"))) totalScore += 25;

                    score = totalScore;
                }
            } catch (IOException e) {
                log.error("Error analyzing Selenium implementation", e);
            }
        }

        results.setSeleniumImplementationScore(score);
        return score;
    }

    private void analyzeBrowserExecution(Path projectPath, AnalysisResults results) {
        // TODO: Implement browser execution analysis
    }

    private void analyzePageObjects(Path projectPath, AnalysisResults results) {
        // TODO: Implement page objects analysis
    }

    private void calculateFrameworkHealth(AnalysisResults results) {
        // TODO: Implement framework health calculation
    }

    private void generateRecommendations(AnalysisResults results) {
        // TODO: Implement recommendations generation
    }

    private void calculateOverallScore(AnalysisResults results) {
        double totalScore = 0.0;
        // Add 25 points for each implemented feature
        if (results.getBrowserExecution().isCrossBrowserTesting()) totalScore += 25;
        if (results.getBrowserExecution().isParallelExecution()) totalScore += 25;

        // Check for screenshot implementation
        if (results.getSeleniumImplementation().isScreenshotCapability()) totalScore += 25;

        // Uniform locator check is already handled in analyzeSeleniumImplementation

        results.setOverallScore(totalScore);
    }
    private void analyzeFeatureFiles(Path projectPath, AnalysisResults results) {
        // TODO: Implement feature file analysis
    }

    private void analyzeStepDefinitions(Path projectPath, AnalysisResults results) {
        // TODO: Implement step definitions analysis
    }

    private void analyzeFrameworkStructure(Path projectPath, AnalysisResults results) {
        // TODO: Implement framework structure analysis
    }

    private void calculateTestCoverage(AnalysisResults results) {
        // TODO: Implement test coverage calculation
    }

    private void analyzeBddImplementation(Path projectPath, AnalysisResults results) {
        // TODO: Implement BDD implementation analysis
    }

    private void analyzeFrameworkArchitecture(Path projectPath, AnalysisResults results) {
        // TODO: Implement framework architecture analysis
    }
}