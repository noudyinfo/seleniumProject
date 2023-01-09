package Raghav.Resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReporterNG {
	
	public static ExtentReports getReportObject ()
	{
		// We use 2 class - ExtentSparkReporter and ExtentReporter
		//ExtentSparkReporter
		String path = System.getProperty("user.dir")+"\\reports\\index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Web Automation Results"); // This is the button on the report of right side
		reporter.config().setDocumentTitle("Test Results");
		
		//ExtentReporter
		ExtentReports extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Quality Testing", "Ragha Noudy");
		return extent;
	}

}
