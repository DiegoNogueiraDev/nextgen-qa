package com.nextgenqa.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ReportGenerator {

    private ExtentReports extent;
    private ExtentTest test;

    public ReportGenerator(String reportPath) {
        // Substituindo ExtentHtmlReporter por ExtentSparkReporter
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
    }

    public void startTest(String testName) {
        test = extent.createTest(testName);
    }

    public void log(String status, String details) {
        switch (status.toLowerCase()) {
            case "pass":
                test.pass(details);
                break;
            case "fail":
                test.fail(details);
                break;
            case "info":
                test.info(details);
                break;
            default:
                test.warning("Status desconhecido: " + status + " - " + details);
        }
    }

    public void endReport() {
        extent.flush();
    }
}
