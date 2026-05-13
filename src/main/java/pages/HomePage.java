package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import config.ConfigReader;
import utils.WaitUtils;

public class HomePage {

	private WebDriver driver;
	private int timeout;

	public HomePage(WebDriver driver) {
		this.driver = driver;
		this.timeout = Integer.parseInt(ConfigReader.getTimeout());
	}

	// Locators
	private final By logo = By.xpath("//div[@class='new_logo']");

	private final By searchBar = By.name("q");

	private final By searchButton = By.xpath("//input[@class='submit-main-search']");

	private final By loginIcon = By.xpath("//ul[@class='menu right']/li/a");

	public boolean verifyHomePage() {
		WaitUtils.waitForPageReady(driver, timeout);
		String currTitle = driver.getTitle();
		String expectedTitle = ConfigReader.getHomePageTitle();

		boolean titleMatch = currTitle.equals(expectedTitle);
		boolean logoPresent = isLogoDisplayed();

		return (titleMatch && logoPresent);
	}

	public void searchForProduct(String product) {
		WaitUtils.waitForVisibility(driver, searchBar, timeout).clear();
		WaitUtils.waitForVisibility(driver, searchBar, timeout).sendKeys(product);
		WaitUtils.waitForClickable(driver, searchButton, timeout).click();
	}

	public void clickLoginIcon() {
		WaitUtils.waitForClickable(driver, loginIcon, timeout).click();
	}

	public boolean isLogoDisplayed() {
		return WaitUtils.waitForVisibility(driver, logo, timeout).isDisplayed();
	}

}