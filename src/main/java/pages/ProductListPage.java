package pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import utils.WaitUtils;

public class ProductListPage {

	private WebDriver driver;

	private WebDriverWait wait;

	private int TIMEOUT;

	// ========= CONSTRUCTOR =========
	public ProductListPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));

	}

	// ========= LOCATORS (ONLY By) =========
	private final By PRICE_DROPDOWN = By.xpath("//span[normalize-space()='Price']");

	private final By CATEGORY_DROPDOWN = By.xpath("//span[normalize-space()='Category']");

	private final By MATERIAL_DROPDOWN = By.xpath("//span[normalize-space()='Material']");

	private final By COLOUR_DROPDOWN = By.xpath("//span[contains(text(),'Colour')]");

	private final By MIN_PRICE_INPUT = By.name("filter.v.price.gte");

	private final By MAX_PRICE_INPUT = By.name("filter.v.price.lte");

	private final By APPLY_PRICE_BUTTON = By.xpath("//button[normalize-space()='Apply']");

	private final By SORT_DROPDOWN = By.id("SortBy");

	private final By PRODUCT_TITLES = By.xpath("//div[@class='new__product_details']/h3");

	private final By PRODUCT_PRICES = By.xpath("//span[contains(@class,'new_product_selling')]");

	private final By PRODUCT_LIST = By.xpath("//span[@class='collection-total-count']");

	private final By APPLIED_FILTERS = By.xpath("//div[@id='SelectedFiltersList']");

	private final By CLICK_PRODUCT = By.xpath("//div[@class='new_product__image']/ancestor::a");


	private void stabilizeGrid() {
		WaitUtils.waitForPageReady(driver, TIMEOUT);
		WaitUtils.waitForPrices(driver, PRODUCT_PRICES, TIMEOUT);

		try {
			Thread.sleep(2500); 
		} catch (InterruptedException ignored) {
		}
	}

	// ========= PRICE FILTER =========
	public void filterByPrice(String min, String max) {
		WaitUtils.waitForClickable(driver, PRICE_DROPDOWN, TIMEOUT).click();

		if (min != null && !min.isEmpty()) {
			WebElement minInput = WaitUtils.waitForVisibility(driver, MIN_PRICE_INPUT, TIMEOUT);
			minInput.clear();
			minInput.sendKeys(min);
		}

		if (max != null && !max.isEmpty()) {
			WebElement maxInput = WaitUtils.waitForVisibility(driver, MAX_PRICE_INPUT, TIMEOUT);
			maxInput.clear();
			maxInput.sendKeys(max);
		}
		
		
		WaitUtils.waitForClickable(driver, APPLY_PRICE_BUTTON, TIMEOUT).click();
		
		stabilizeGrid();

	}

	// ========= CATEGORY FILTER =========
	public void selectCategory(String categoryName) {

		WaitUtils.waitForClickable(driver, CATEGORY_DROPDOWN, TIMEOUT).click();

		By checkbox = By.xpath("//input[@name='filter.p.m.custom.category' and @value='" + categoryName + "']");

		WebElement element = WaitUtils.waitForClickable(driver, checkbox, TIMEOUT);

		element.click();

		if (!element.isSelected()) {
			element.click();
		}
		stabilizeGrid();
	}

	// ========= MATERIAL FILTER =========
	public void selectMaterial(String materialName) {
		WaitUtils.waitForClickable(driver, MATERIAL_DROPDOWN, TIMEOUT).click();

		By checkbox = By.xpath("//input[@value='" + materialName + "']");

		WebElement element = WaitUtils.waitForClickable(driver, checkbox, TIMEOUT);

		element.click();

		if (!element.isSelected()) {
			element.click();
		}
		stabilizeGrid();
	}

	// ========= COLOUR FILTER =========
	public void selectColour(String colourName) {

		WaitUtils.waitForClickable(driver, COLOUR_DROPDOWN, TIMEOUT).click();

		By checkbox = By.xpath("//input[@value='" + colourName + "']");
		WaitUtils.waitForClickable(driver, checkbox, TIMEOUT).click();
		stabilizeGrid();
	}

	// ========= SORT =========
	public void sortByLowToHigh() {
		sortBy("Price: Low To High");
	}

	public void sortByHighToLow() {
		sortBy("Price: High To Low");
	}

	public void sortByLatest() {
		sortBy("Latest");
	}

	public void sortByAlphabetically() {
		sortBy("Alphabetically: A–Z");
	}

	public void sortByBestSelling() {
		sortBy("Best Selling");
	}

	private void sortBy(String option) {
		WebElement dropdown = WaitUtils.waitForClickable(driver, SORT_DROPDOWN, TIMEOUT);
		new Select(dropdown).selectByVisibleText(option);
		stabilizeGrid();
	}

	// ========= Applied Filter Text ========

	public String getFilterList() {
		WebElement filterList = driver.findElement(APPLIED_FILTERS);
		return filterList.getText();
	}

	// ========= OUTPUT (SAFE) =========
	public void printFirstThreeProducts() {
		List<WebElement> titles = driver.findElements(PRODUCT_TITLES);
		List<WebElement> prices = driver.findElements(PRODUCT_PRICES);

		System.out.println("----- List of Products -----");

		int count = Math.min(3, Math.min(titles.size(), prices.size()));

		for (int i = 0; i < count; i++) {
			System.out.println(titles.get(i).getText() + " --> " + prices.get(i).getText());
		}
	}

//    ========= Click on First page ==============

	public void clickOnFirstProductAndWait() {

		wait.until(driver -> driver.findElements(CLICK_PRODUCT).size() > 0);

		driver.findElements(CLICK_PRODUCT).get(0).click();
	}

	public String checkProductList() {
		return WaitUtils.waitForVisibility(driver, PRODUCT_LIST, TIMEOUT).getText();
	}

}
