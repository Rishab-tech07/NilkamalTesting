package config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static Properties properties = new Properties();

    static {
        try {
            InputStream input = ConfigReader.class
                    .getClassLoader()
                    .getResourceAsStream("ObjectRepo/config.properties");

            if (input == null) {
                throw new RuntimeException("Config.properties not found in resources folder");
            }

            properties.load(input);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load Config.properties");
        }
    }
    
    //getting browser Name
    public static String getBrowserName() {
        return properties.getProperty("browser");
    }
    
    //base url
    public static String getBaseURL() {
    	return properties.getProperty("site.homePage.url");
    	
    }
    
    //timeout
    public static String getTimeout() {
    	return properties.getProperty("timeout");
    	
    }
    
    // login page URL
    public static String getLoginPageURL() {
    	return properties.getProperty("site.loginPage.url");
    }
    
    //login page title
    public static String getLoginPageTitle() {
    	return properties.getProperty("site.loginPage.title");
    }
    
    //home page title
    public static String getHomePageTitle() {
    	return properties.getProperty("site.homePage.title");
    }
    
    public static String getExcelFilePath(String readOrWrite) {
    	if(readOrWrite.equals("read"))
    		return properties.getProperty("excelFilePath");
    	else
    		return properties.getProperty("excelWriteFilePath");
    }
    
    public static String getFilterSorter(){
        return properties.getProperty("filter");
    }
    
    public static String getPrice() {
    	return properties.getProperty("site.productListPage.price");
    }
    
    public static String getCategory() {
    	return properties.getProperty("site.productListPage.category");
    }
    
    public static String getMaterial() {
    	return properties.getProperty("site.productListPage.material");
    }

	public static String getSearchData() {
		
		return properties.getProperty("site.homePage.searItem");
	}

	public static String getProductPageUrl() {
		return properties.getProperty("site.productPage.url");
	}
}
