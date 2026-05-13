package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import utils.WaitUtils;

public class CheckoutPage {

    private WebDriver driver;
    private static final int TIMEOUT = 5;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }


    private By emailField = By.id("email");
    private By pincodeField = By.name("postalCode");
    private By phoneField = By.xpath("//input[@name='phone']");

    @FindBy(name = "countryCode")
    private WebElement countryDropdown;

    @FindBy(name = "firstName")
    private WebElement firstNameInput;

    @FindBy(name = "lastName")
    private WebElement lastNameInput;

    @FindBy(name = "address1")
    private WebElement addressInput;

    @FindBy(name = "address2")
    private WebElement apartmentInput;

    @FindBy(name = "city")
    private WebElement cityInput;

    @FindBy(name = "zone")
    private WebElement stateDropdown;

    @FindBy(name = "reductions")
    private WebElement reductions;

    @FindBy(id = "save_shipping_information")
    private WebElement saveButton;


    public void handleEmailIfNotLoggedIn(String email) {
        if (!driver.findElements(emailField).isEmpty()) {

            WebElement input = WaitUtils.waitForVisibility(driver, emailField, TIMEOUT);

            input.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
            input.sendKeys(email);
            input.sendKeys(Keys.TAB);

            System.out.println("Email entered: " + email);
        } else {
            System.out.println("User already logged in");
        }
    }


    public void selectCountry(String country) {
        WaitUtils.waitForElement(driver, countryDropdown, TIMEOUT).sendKeys(country);
    }

    public void enterFirstName(String name) {
        WebElement el = WaitUtils.waitForElement(driver, firstNameInput, TIMEOUT);
        el.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        el.sendKeys(name);
    }

    public void enterLastName(String name) {
        WebElement el = WaitUtils.waitForElement(driver, lastNameInput, TIMEOUT);
        el.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        el.sendKeys(name);
    }

    public void enterAddress(String address) {
        WebElement el = WaitUtils.waitForElement(driver, addressInput, TIMEOUT);
        el.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        el.sendKeys(address);
        el.sendKeys(Keys.TAB);
    }

    public void enterApartment(String apartment) {
        if (apartment != null && !apartment.isEmpty()) {
            WebElement el = WaitUtils.waitForElement(driver, apartmentInput, TIMEOUT);
            el.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
            el.sendKeys(apartment);
        }
    }

    public void enterCity(String city) {
        WebElement el = WaitUtils.waitForElement(driver, cityInput, TIMEOUT);
        el.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        el.sendKeys(city);
        el.sendKeys(Keys.TAB);
    }

    public void selectState(String state) {
        WaitUtils.waitForElement(driver, stateDropdown, TIMEOUT).sendKeys(state);
    }

    // ========= PROMO =========

    public boolean applyPromoCode(String code) {

        WebElement el = WaitUtils.waitForElement(driver, reductions, TIMEOUT);

        el.sendKeys(Keys.CONTROL + "a", Keys.DELETE);
        el.sendKeys(code);
        el.sendKeys(Keys.ENTER);

        try {
            By applied = By.xpath("//span[text()='" + code + "']");

            WaitUtils.waitForVisibility(driver, applied, TIMEOUT);

            System.out.println("Promo applied: " + code);
            return true;

        } catch (Exception e) {
            System.out.println("Promo failed: " + code);
            return false;
        }
    }


    public boolean enterPincodeWithRetry(String... pincodes) {

        WebElement pin = WaitUtils.waitForPresence(driver, pincodeField, TIMEOUT);

        for (String code : pincodes) {
            try {
                pin.click();
                pin.sendKeys(Keys.CONTROL + "a", Keys.DELETE);

                pin.sendKeys(code);
                pin.sendKeys(Keys.TAB);

                String value = pin.getAttribute("value");
                String invalid = pin.getAttribute("aria-invalid");

                System.out.println("PIN: " + code + " | value=" + value + " | status=" + invalid);

                if (!value.equals(code)) continue;

                if (invalid == null || invalid.equals("false")) {
                    System.out.println("PIN valid: " + code);
                    return true;
                }

            } catch (Exception e) {
                System.out.println("PIN error: " + code);
            }
        }

        return false;
    }



    public boolean enterPhoneWithRetry(String... phones) {

        WebElement phone = WaitUtils.waitForPresence(driver, phoneField, TIMEOUT);

        for (String ph : phones) {
            try {
                phone.click();
                phone.sendKeys(Keys.CONTROL + "a", Keys.DELETE);

                phone.sendKeys(ph);
                phone.sendKeys(Keys.TAB);

                String value = phone.getAttribute("value");
                String invalid = phone.getAttribute("aria-invalid");

                System.out.println("Phone: " + ph + " | value=" + value + " | status=" + invalid);

                if (!value.contains(ph.substring(5))) continue;

                if (invalid == null || invalid.equals("false")) {
                    System.out.println("Phone valid: " + ph);
                    return true;
                }

            } catch (Exception e) {
                System.out.println("Phone error: " + ph);
            }
        }

        return false;
    }

    public void clickSave() {
        WaitUtils.waitForClickableElement(driver, saveButton, TIMEOUT).click();
        System.out.println("Shipping info saved");
    }
}