package com.bddanalyzer.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class AnalysisResults {
    private double overallScore;
    
    private FeatureFiles featureFiles = new FeatureFiles();
    private StepDefinitions stepDefinitions = new StepDefinitions();
    private TestCoverage testCoverage = new TestCoverage();
    private FrameworkStructure frameworkStructure = new FrameworkStructure();
    private BddImplementation bddImplementation = new BddImplementation();
    private FrameworkArchitecture frameworkArchitecture = new FrameworkArchitecture();
    private CodeQuality codeQuality = new CodeQuality();
    private SeleniumImplementation seleniumImplementation = new SeleniumImplementation();
    private BrowserExecution browserExecution = new BrowserExecution();
    private PageObjects pageObjects = new PageObjects();
    private FrameworkHealth frameworkHealth = new FrameworkHealth();
    private List<String> recommendations = new ArrayList<>();

    @Data
    public static class FeatureFiles {
        private int count;
        private double qualityScore;
        private List<String> issues = new ArrayList<>();
    }

    @Data
    public static class StepDefinitions {
        private int count;
        private double qualityScore;
        private List<String> issues = new ArrayList<>();
    }

    @Data
    public static class TestCoverage {
        private double score;
        private List<String> issues = new ArrayList<>();
    }

    @Data
    public static class FrameworkStructure {
        private double score;
        private List<String> issues = new ArrayList<>();
    }

    @Data
    public static class BddImplementation {
        private boolean backgroundUsage;
        private double scenarioQuality;
        private int totalScenarios;
        private int emptySteps;
    }

    @Data
    public static class FrameworkArchitecture {
        private boolean baseClassImplementation;
        private boolean dataDrivenApproach;
        private boolean frameworkScalability;
        private double methodQuality;
    }

    @Data
    public static class CodeQuality {
        private double namingConventions;
        private int unusedImports;
        private int commentedCode;
        private int systemOutUsage;
        private int unusedVariables;
    }

    @Data
    public static class SeleniumImplementation {
        private double waitStrategy;
        private boolean explicitWaits;
        private boolean customWaitConditions;
        private boolean screenshotCapability;
        private boolean webdriverManagement;
        private boolean actionsClassUsage;
        private boolean javascriptExecutor;
    }

    @Data
    public static class BrowserExecution {
        private double browserCompatibility;
        private boolean gridSupport;
        private boolean parallelExecution;
        private boolean retryMechanism;
    }

    @Data
    public static class PageObjects {
        private boolean hasPageObjects;
        private int totalPageObjects;
        private boolean basePagePattern;
        private boolean properEncapsulation;
    }

    @Data
    public static class FrameworkHealth {
        private double selenium;
        private double bdd;
        private double codeQuality;
        private double structure;
        private double codeHealth;
    }
} 