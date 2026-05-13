package tests;
 
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import config.ConfigReader;
import pages.HomePage;


@Test(groups="search")
public class HomePageTest extends BaseTest {
	
	private HomePage homePage;
	
	@BeforeClass
	public void basic_setup() {
		homePage = new HomePage(driver);
		String url = ConfigReader.getBaseURL();
		driver.get(url);
	}
	
 
 
	    @Test(priority = 1)
	    public void verifyHomePageLogo() {
	    	homePage = new HomePage(driver);
	        boolean homePageVerified = homePage.verifyHomePage();
	        Assert.assertTrue(homePageVerified , "Home page logo is not displayed!");
	    }
 
	    @Test(priority = 2)
	    public void verifySearchFunctionality() {
	    
	        String searchItem = ConfigReader.getSearchData();

	        homePage.searchForProduct(searchItem);
	        Assert.assertTrue(driver.getTitle().contains(searchItem),"Search result page title does not contain the search term.");


	    }
 
	    
}