package tests;

import java.io.IOException;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.CheckoutPage;
import utils.ExcelUtility;

public class CheckoutPageTest extends BaseTest {

	private CheckoutPage checkoutPage;


	@BeforeClass
	public void setup() {
		checkoutPage = new CheckoutPage(driver);
	}


	@DataProvider(name = "checkoutData")
	public String[][] getData() throws IOException {
		return new ExcelUtility("checkoutData").getData();

	}

	@Test(dataProvider = "checkoutData")
	public void testCheckoutFlow(String email, String country, String firstName, String lastName, String address,
			String apartment, String city, String state, String pincode, String phone, String Promo) {

		System.out.println("\n--------------------------------");

		// checkoutPage.handleEmailIfNotLoggedIn(email);
		checkoutPage.selectCountry(country);
		checkoutPage.enterFirstName(firstName);
		checkoutPage.enterLastName(lastName);
		checkoutPage.enterAddress(address);
		checkoutPage.enterApartment(apartment);
		checkoutPage.enterCity(city);
		checkoutPage.selectState(state);
		checkoutPage.enterPincodeWithRetry(pincode.split(","));
		checkoutPage.enterPhoneWithRetry(phone.split(","));
		// checkoutPage.applyPromoCode(Promo);
		checkoutPage.clickSave();

		System.out.println("TEST COMPLETED\n");
	}
}