package utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtils {

	private WaitUtils() {

	}
	
	public static WebElement waitForElement(WebDriver driver, WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

  
    public static WebElement waitForClickableElement(WebDriver driver, WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }
    
    public static List<WebElement> waitForElements(WebDriver driver, List<WebElement> elements, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        wait.until(driver1 -> !elements.isEmpty());
        return elements;
    }
    
   


	// ============================================================
	// BY LOCATOR METHODS (Using By)
	// ============================================================

	private static WebDriverWait getWait(WebDriver driver, int timeoutInSeconds) {
		return new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
	}

	public static WebElement waitForVisibility(WebDriver driver, By locator, int timeoutInSeconds) {
		return getWait(driver, timeoutInSeconds).until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static WebElement waitForPresence(WebDriver driver, By locator, int timeoutInSeconds) {
		return getWait(driver, timeoutInSeconds).until(ExpectedConditions.presenceOfElementLocated(locator));
	}

	public static boolean waitForTitle(WebDriver driver, String title, int timeoutInSeconds) {
		return getWait(driver, timeoutInSeconds).until(ExpectedConditions.titleContains(title));
	}

	public static WebElement waitForClickable(WebDriver driver, By locator, int timeoutInSeconds) {
		return getWait(driver, timeoutInSeconds).until(ExpectedConditions.elementToBeClickable(locator));
	}

	public static List<WebElement> waitForVisibilityOfElements(WebDriver driver, By locator, int timeout) {
		return getWait(driver, timeout).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}

	// ========= NILKAMAL‑SPECIFIC STABILIZATION =========

	// Waits for document + Ajax 
	public static void waitForPageReady(WebDriver driver, int timeoutInSeconds) {

		getWait(driver, timeoutInSeconds).until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").toString().equals("complete"));

		try {
			getWait(driver, timeoutInSeconds).until(d -> (Boolean) ((JavascriptExecutor) d)
					.executeScript("return window.jQuery ? jQuery.active === 0 : true"));
		} catch (TimeoutException ignored) {
			// site may not expose jQuery reliably
		}
		
//		
//		    JavascriptExecutor js = (JavascriptExecutor) driver;
//		    // 'complete' ka matlab hai DOM aur saare resources load ho chuke hain
//		    if (js.executeScript("return document.readyState").toString().equals("complete")) {
//		        System.out.println("Page loaded successfully!");
//		    }
		
	}

	// Waits until at least one price is rendered 
	public static void waitForPrices(WebDriver driver, By locator, int timeoutInSeconds) {

		getWait(driver, timeoutInSeconds).until(d -> {
			List<WebElement> prices = d.findElements(locator);
			return prices.size() > 0 && prices.get(0).getText().trim().length() > 0;
		});
	}

}