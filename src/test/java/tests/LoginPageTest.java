package tests;

import java.io.IOException;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import config.ConfigReader;
import pages.AccountPage;
import pages.HomePage;
import pages.LoginPage;
import utils.ExcelUtility;

@Test(groups = "login")
public class LoginPageTest extends BaseTest {

	private LoginPage lp;
	private ExcelUtility excelRead;
	private HomePage homePage;
	private AccountPage actPage;

	@BeforeClass
	public void basic_setup() throws IOException {
		lp = new LoginPage(driver);
		excelRead = new ExcelUtility("LoginData");
		String currUrl = driver.getCurrentUrl();
		if (!currUrl.contains("login")) {
			homePage = new HomePage(driver);
			driver.get(ConfigReader.getBaseURL());
		}
		actPage = new AccountPage(driver);
	}

	@DataProvider(name = "loginData")
	public String[][] getLoginData() throws IOException {
		String[][] data = excelRead.getData();
		return data;

	}

	@Test(priority = 1)
	public void LoginPageVerification() {
		boolean verifyPage = lp.verifyLoginPage();
		Assert.assertTrue(verifyPage);

	}

	@Test(priority = 2, dataProvider = "loginData")
	public void login(String email, String password) {
		lp.loginWithData(email, password);
		Reporter.getCurrentTestResult().setAttribute("email", email);
		Assert.assertTrue(driver.getCurrentUrl().contains("account"));
	}

	@Test(priority = 3)
	public void clickLogo() {

		actPage.goToHomePage();
		String title = ConfigReader.getHomePageTitle();
		Assert.assertTrue(driver.getTitle().contains(title), "clicklogo is failed");

	}

	@Test(priority = 0)
	public void verifyLoginNavigation() {
		homePage.clickLoginIcon();

	}

}
