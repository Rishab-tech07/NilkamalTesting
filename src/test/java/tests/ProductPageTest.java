package tests;

import java.io.IOException;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import config.ConfigReader;
import pages.ProductPage;
import utils.ExcelUtility;

@Test(groups = "pincode")
public class ProductPageTest extends BaseTest {
	private ProductPage pdp;

	@BeforeClass
	public void setup() {

		String currentUrl = driver.getCurrentUrl();

		if (!currentUrl.contains("/products")) {
			driver.get(ConfigReader.getProductPageUrl());
		}
		pdp = new ProductPage(driver);
	}

	@Test(priority = 0)
	public void verifyProductPage() {

		String txt = pdp.checkProductItem();
		Assert.assertEquals(txt, "ADD TO CART");
	}

	@DataProvider(name = "pincodeData")
	public String[][] getData() throws IOException {
		return new ExcelUtility("PincodeData").getData();
	}

	@Test(priority = 1, dataProvider = "pincodeData")
	public void pinCodeTest(String pincode, String expectedMessage) {

		pdp.fillpincode(pincode);
		String actualMessage = pdp.getDeliveryMessage();
		Reporter.getCurrentTestResult().setAttribute("pincode", pincode);
		Assert.assertTrue(actualMessage.contains(expectedMessage), "Pincode validation failed for: " + pincode);
	}

	@Test(priority = 2)
	public void verifyWishlistCnt() {
		pdp.clickWishList();
		String exp = pdp.getWishlistCountText();
		String act = "1";

		Assert.assertEquals(act, exp, "wishlist failed");
	}

	@Test(priority = 3, dependsOnMethods = { "verifyWishlistCnt" })
	public void verifyWishlistPage() {
		pdp.WishlistPage();
		Assert.assertTrue(driver.getCurrentUrl().contains("wishlist/"), "wishlist skipped");
	}

	@Test(priority = 4)
	public void addToCart() {
		pdp.addItemToCart();
		pdp.closeSideCart();
	}

}
