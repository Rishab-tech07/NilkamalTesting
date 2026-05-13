package pages;


import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import config.ConfigReader;

import utils.WaitUtils;

public class CartPage {

    private WebDriver driver;
    private int timeout;

    private final By cartCount =
    		By.xpath("//span[contains(@class, 'cart_count')]");
    
    private final  By wishlistCount=
    		By.xpath("//span[contains(@class, 'iWishCount')]");
    
    private final By cartIcon = 
    		By.xpath("//div[@class='cart_container hi']");

    private final By checkouttxt = 
    		By.xpath("//span[@class='shipping__title']");
    
    private final By cartItemList=
    		By.xpath("//ul[@class='cart_items js-cart_items clearfix']/li/div[2]/div/strong/span");
    
    private final By checkoutButton=
    		By.xpath("//input[@value='Checkout']");
    
    private final By cartactualPrice=
    		By.xpath("//div[@class='mySideCartBtn']/ul/li/span/span");


    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.timeout = Integer.parseInt(ConfigReader.getTimeout());
    }


    
    public String getCartCount() {
        return WaitUtils.waitForVisibility(driver, cartCount, timeout).getText();
    }
    

    public String getWishlistCountText() {
        return WaitUtils.waitForVisibility(driver, wishlistCount, timeout).getText();
    }

    public void openCart() {
        WaitUtils.waitForClickable(driver, cartIcon, timeout).click();
    }

    
    public String verifyCart() {
    	String txt=WaitUtils.waitForVisibility(driver, checkouttxt, timeout).getText();
    	return txt;
    }
    
    public double getCartPrice() {
    	double sum=0;
		List<WebElement> prices = WaitUtils.waitForVisibilityOfElements(driver,cartItemList,timeout);
		for (WebElement e : prices) {
		    String txt1 = e.getText();
		    double value = cleanPrice(txt1);

		    System.out.println("Cleaned price: " + value);
		    
		    sum += value;
		}
		System.out.println("Total:-"+sum);
		return sum;
    }
    
    public double getActualPrice() {
    	String txt=WaitUtils.waitForVisibility(driver, cartactualPrice, timeout).getText();
    	double actual=cleanPrice(txt);
    	System.out.println("act:-"+actual);
    	return actual;
    }
    
    private static double cleanPrice(String price) {
        price = price.replace(",", "");       
        price = price.replaceAll("[^0-9.]", ""); 
        return Double.parseDouble(price);
    }
    

    public void proceedToCheckout() {
        WaitUtils.waitForClickable(driver, checkoutButton, timeout).click();
    }
}

