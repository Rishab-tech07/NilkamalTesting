package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import config.ConfigReader;

public class LoginPage {
	private WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;

	}

	private final By email_area = By.id("customer_email");

	private final By password_area = By.id("customer_password");

	private final By loginBtn = By.xpath("//input[@value='Login']");

	public boolean verifyLoginPage() {
		String currUrl = driver.getCurrentUrl();
		String expectedUrl = ConfigReader.getLoginPageURL();
		boolean urlMatch = currUrl.equals(expectedUrl);

		String currTitle = driver.getTitle();
		String expectedTitle = ConfigReader.getLoginPageTitle();
		boolean titleMatch = currTitle.equals(expectedTitle);

		return (titleMatch && urlMatch);
	}

	public void loginWithData(String email_address, String password) {

		driver.findElement(email_area).clear();

		driver.findElement(email_area).sendKeys(email_address);

		driver.findElement(password_area).clear();

		driver.findElement(password_area).sendKeys(password);
		driver.findElement(loginBtn).click();

	}

}
