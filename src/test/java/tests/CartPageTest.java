package tests;

import base.BaseTest;

import pages.CartPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CartPageTest extends BaseTest {

	private CartPage cartPage;
	
	@BeforeClass
	public void basic_setup(){
	    cartPage = new CartPage(driver);
	}
	
    @Test(priority=8)
    public void verifyCartPage(){
        cartPage.openCart();
        String check=cartPage.verifyCart();
        Assert.assertEquals(check, "Shipping calculated at checkout");
    }
    
    @Test(priority=9)
    public void verifyProductCount() {
    	String txt=cartPage.getCartCount();
    	Assert.assertNotEquals(txt,"0","Products cnt should be available");
    }
    
    @Test(priority=10)
    public void PriceValidation() {
    	double expected=cartPage.getCartPrice();
    	double actual=cartPage.getActualPrice();
    	Assert.assertEquals(actual, expected, "Total Price mismatch in cart");
    }
    
    @Test(priority=11)
    public void clickCheckOut() {
    	cartPage.proceedToCheckout();
    }
    
}

