package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import config.ConfigReader;
import utils.WaitUtils;

public class AccountPage {
	private WebDriver driver;
	private int timeout;
	

	public AccountPage(WebDriver driver) {
		this.driver = driver;
		this.timeout = Integer.parseInt(ConfigReader.getTimeout());
	}

//	@FindBy(className = "log_out_opt")
	private final By logoutBtn=
			By.className("log_out_opt");
//	@FindBy(className = "new_logo")
	private final By homePageLogo=
			By.xpath("//div[@class='new_logo']");

	
	public boolean verifyAccountPage() {
		String url = driver.getCurrentUrl();
		boolean urlMatch = url.contains("https://www.nilkamalfurniture.com/account");
		return urlMatch;
	}
	public void clickLogoutBtn() {
		driver.findElement(logoutBtn).click();
	}

	public void goToHomePage() {
		WaitUtils.waitForPageReady(driver, timeout);
		WaitUtils.waitForClickable(driver, homePageLogo,timeout ).click();
	}
}
