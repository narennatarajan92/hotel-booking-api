package com.utils;

import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReportGenerator {

    public static void generateReport() {
        // Set up paths
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File reportOutputDirectory = new File("target/cucumber-html-report-" + timestamp);
        List<String> jsonFiles = Collections.singletonList("target/cucumber.json");


        // Set config
        Configuration config = new Configuration(reportOutputDirectory, "Booking API Automation");
        config.setBuildNumber(timestamp); // Adds timestamp to the report
        config.addClassifications("Platform", System.getProperty("os.name"));
        config.addClassifications("Browser", "N/A");
        config.addClassifications("Java Version", System.getProperty("java.version"));
        config.addClassifications("Generated At", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        // Create report
        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, config);
        reportBuilder.generateReports();
    }
}
