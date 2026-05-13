package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;

import config.ConfigReader;

@Listeners(listeners.TestListener.class)
public class BaseTest extends BasePage {
    

    protected static WebDriver driver;


//    @Parameters({"browser"})
    @BeforeTest(alwaysRun = true)
    public void setUp() {

        driver = setupDriver(ConfigReader.getBrowserName()); 
        
        int timeout = Integer.parseInt(ConfigReader.getTimeout());
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(timeout));
        driver.manage().window().maximize();
        

    }
    
    public WebDriver getDriver(){
    	return driver;
    }



    @AfterTest(alwaysRun = true)
    public void tearDown() {
    	System.out.println("Quitting the browser");
        if (driver != null) {
            driver.quit(); 
        }
    }
    
    
}
