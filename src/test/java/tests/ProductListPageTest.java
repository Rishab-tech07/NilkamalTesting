package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.BaseTest;
import config.ConfigReader;
import pages.HomePage;
import pages.ProductListPage;


@Test(groups="filter")
public class ProductListPageTest extends BaseTest {

	private HomePage homePage;
	private ProductListPage productListPage;

	@BeforeClass
	public void setup() {

		String currentUrl = driver.getCurrentUrl();


		if (!currentUrl.contains(ConfigReader.getBaseURL())) {
			String baseUrl = ConfigReader.getBaseURL();
			driver.get(baseUrl);
			
		}
		homePage = new HomePage(driver);
		homePage.searchForProduct("bookshelves");
		
		productListPage = new ProductListPage(driver);
	}


	// ---------------- TEST CASE 1 ----------------

	@Test(priority = 0)
	public void verifyProductList() {
		productListPage = new ProductListPage(driver);
		String s = productListPage.checkProductList();
		Assert.assertNotEquals(s, "0", "Products list should be available");
	}

	@Test(priority = 1)
	public void verifyPriceFilterWorks() {

		productListPage.filterByPrice("0", "20000");

		Assert.assertTrue(productListPage.getFilterList().contains(ConfigReader.getPrice()),
				"Price filter caused page navigation issue");

	}

	// ---------------- TEST CASE 2 ----------------
	
	@Test(priority = 2)
	public void verifyCategoryFilterWorks() {

		productListPage.selectCategory("Storage Cabinets");


		String filter_list = productListPage.getFilterList();

		boolean f = filter_list.contains(ConfigReader.getCategory());

		Assert.assertTrue(f, "Category filter did not show products");
	}

	// ---------------- TEST CASE 3 ----------------

	@Test(priority = 3)
	public void verifyMaterialFilterWorks() {

		productListPage.selectMaterial("Engineered Wood");

		Assert.assertTrue(productListPage.getFilterList().contains(ConfigReader.getMaterial()),
				"Material filter did not render prices");
	}

	// ---------------- TEST CASE 4 ----------------

	@Test(priority = 4)
	public void verifySortBy() {
		productListPage = new ProductListPage(driver);

		productListPage.sortByHighToLow();
		productListPage.printFirstThreeProducts();

		Assert.assertTrue(driver.getCurrentUrl().contains("sort_by"), "Sort action caused unexpected page state");
	}
	
	
	// ---------------- TEST CASE 5 ----------------
	@Test(priority = 5, groups = "clickOnProduct")
	public void navigatesToProductPage() {
		
		productListPage = new ProductListPage(driver);
		
			try {
				productListPage.clickOnFirstProductAndWait();
			} catch (Exception e) {
				driver.navigate().refresh();
			}

		    Assert.assertTrue(
		            driver.getCurrentUrl().contains("/products/"),
		            "User was not navigated to product detail page"
		    );

	}

}