package base;

import org.openqa.selenium.WebDriver;

import utils.DriverFactory;

public class BasePage {

    
    public WebDriver setupDriver(String browserName) {
        DriverFactory df = new DriverFactory(); 
        return df.initDriver(browserName);
    }
}
