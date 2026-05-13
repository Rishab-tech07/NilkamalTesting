package listeners;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import base.BaseTest;
import config.ConfigReader;
import utils.ScreenshotUtils;

public class ExtentReportManager implements ITestListener {

	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	public void onStart(ITestContext context) {

		sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/extendsReoprt/myExtendReport.html");

		sparkReporter.config().setDocumentTitle("Automation Report");
		sparkReporter.config().setReportName("Functional Testing");
		sparkReporter.config().setTheme(Theme.DARK);

		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);

		extent.setSystemInfo("Computer Name", "localhost");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Tester Name", "Aamir Hasan");
		extent.setSystemInfo("Tester Name", "Prabhat Kumar");
		extent.setSystemInfo("Tester Name", "Ashish Raj");
		extent.setSystemInfo("Tester Name", "Rishab Kumar");
		extent.setSystemInfo("Tester Name", "Gowtham Kumar Reddy");
		extent.setSystemInfo("OS", "Windows11");
		extent.setSystemInfo("Browser", ConfigReader.getBrowserName());
	}

	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.PASS, "Test PASSED: " + result.getName());
	}

	public void onTestFailure(ITestResult result) {

		test = extent.createTest(result.getName());
		test.log(Status.FAIL, "Test FAILED: " + result.getName());
		test.log(Status.FAIL, result.getThrowable());
		
		
		Throwable throwable = result.getThrowable();

		if (throwable != null) {
		        test.fail("Failure Reason: " + throwable.getMessage());
		 }

		Object testClass = result.getInstance();
		WebDriver driver = ((BaseTest) testClass).getDriver();

		if (driver != null) {
			String screenshotPath = ScreenshotUtils.takeScreenshot(driver, result.getName());

			try {
				test.addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			test.log(Status.WARNING, "Driver is NULL - Screenshot not captured");
		}
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getName());
		test.log(Status.SKIP, "Test SKIPPED: " + result.getName());
	}

	public void onFinish(ITestContext context) {
		extent.flush();
	}
}
