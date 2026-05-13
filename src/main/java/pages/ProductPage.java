package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import config.ConfigReader;
import utils.WaitUtils;

public class ProductPage {
    private WebDriver driver;
    private int timeout;


    private final By addToCartButton =
    		By.id("dataLayerClickAdd");
    
    private final By closeSideCartButton = 
    		By.xpath("//a[@onclick='mysideCartClose()']");
    
    private final By pincode = 
    		By.id("pincodeSearch");
    
    private final By errortxt = 
    		By.xpath("//div[@id='pincodeError']");
    
    private final By wishlistBtn = 
 		   By.xpath("(//div[@class='iwishAddWrap']/a[@class='iWishAdd'])[2]");
    
    private final By wishlistCnt = 
    		By.xpath("//span[contains(@class, 'iWishCount')]");
    
    private final By wishlistPageBtn = 
    		By.xpath("//*[@class='new_other__links__header']/a[2]");
    
    
    private final By checkoutBtn = 
    		By.xpath("//input[@id='cart_checkout']");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        this.timeout = Integer.parseInt(ConfigReader.getTimeout());
    }
    
    

    public String checkProductItem(){
    	WaitUtils.waitForPageReady(driver, timeout);
        String txt=WaitUtils.waitForVisibility(driver, addToCartButton, timeout).getText();
        return txt;
    }

//    public String checkPinCode(){
//        String pin=driver.findElement(pincode).getText();
//        return pin;
//    }

    
    public void addItemToCart() {
   
        WebElement btn =
                WaitUtils.waitForClickable(driver, addToCartButton, timeout);


        btn.click();
    }

    public void closeSideCart() {
    	
    	try {

        WebElement closeBtn =
                WaitUtils.waitForClickable(driver, closeSideCartButton, timeout);

            closeBtn.click();
    	} catch (Exception e) {
    		WebElement closeBtn =
                    WaitUtils.waitForClickable(driver, checkoutBtn, timeout);

                closeBtn.click();
    	}
         
        
    }
    

	public void fillpincode(String pincodeValue) {
		
		WaitUtils.waitForVisibility(driver, pincode, timeout);
	    WebElement ele = WaitUtils.waitForClickable(driver, pincode, timeout);
	    ele.clear();
	    ele.sendKeys(pincodeValue);
	    
	}

    

    public String getDeliveryMessage() {	
    String str =  WaitUtils.waitForVisibility(driver, errortxt, timeout).getText().trim();
    return str;
    }
    
    public void clickWishList() {
    	WebElement element = WaitUtils.waitForClickable(driver, wishlistBtn, timeout);
    	JavascriptExecutor js = (JavascriptExecutor) driver;
    	js.executeScript("arguments[0].click();", element);
    }
    
    public String getWishlistCountText() {
        return WaitUtils.waitForVisibility(driver, wishlistCnt, timeout).getText().trim();
    }

	public void WishlistPage() {
		WaitUtils.waitForClickable(driver, wishlistPageBtn, timeout).click();
		
	}

}
