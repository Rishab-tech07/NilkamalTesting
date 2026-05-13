package listeners;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import base.BaseTest;
import utils.ExcelUtility;
import utils.ScreenshotUtils;

public class TestListener implements ITestListener {

    private ExcelUtility ex;

    @Override
    public void onStart(ITestContext context) {
        try {
            ex = new ExcelUtility();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void write(String testName, String status) {
        try {
            ex.write(testName, status);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
    	String email = (String)result.getAttribute("email");
    	String pincode = (String)result.getAttribute("pincode");
        if(email != null ) {
        	System.out.println("test pass:- " + result.getName()+ " with email " + email);
        	write(result.getName()+ "with email " + email , "PASS");
        }else if(pincode != null) {
        	System.out.println("test pass:- " + result.getName()+ " with pincode " + pincode);
        	write(result.getName()+ "with pincode " + pincode , "PASS");
        }
        else {
        	System.out.println("test pass:- " + result.getName());
        	write(result.getName() , "PASS");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
    	System.out.println("test skipped:- " + result.getName());
    	write(result.getName() , "SKIP");
    }
    
    @Override
    public void onTestFailure(ITestResult result) {
//        System.out.println("test fail:- " + result.getName());
    	Object testClass = result.getInstance();
        WebDriver driver = ((BaseTest) testClass).getDriver();

	        String email = (String)result.getAttribute("email");
	        String pincode = (String)result.getAttribute("pincode");
	        if(email != null ) {
	        	System.out.println("test fail:- " + result.getName()+ " with email " + email);
	        	write(result.getName()+ "with email " + email , "FAIL");
	        }else if(pincode != null) {
	        	System.out.println("test fail:- " + result.getName()+ " with pincode " + pincode);
	        	write(result.getName()+ "with pincode " + pincode , "FAIL");
	        }
	        else {
	        	System.out.println("test fail:- " + result.getName());
	        	write(result.getName() , "FAIL");
	        }
	        
	       
            if (driver != null) {
                ScreenshotUtils.takeScreenshot(driver, result.getName());
                System.out.println("Screenshot captured for failed test");
            } else {
                System.out.println("Driver is null. Screenshot not captured.");
                return;
            }
           
//        write(result.getName(), "FAIL");
    }
    

    @Override
    public void onFinish(ITestContext context) {
        try {
            ex.closeExcelFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}