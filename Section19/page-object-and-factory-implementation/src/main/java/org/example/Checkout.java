package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Checkout {

    @FindBy(how = How.XPATH, using = "//button[contains(text(), 'Apply Coupon')]")
    public WebElement getApplyCouponButton;

    @FindBy(how = How.XPATH, using = "//div[contains(text(), 'Apply Coupon')]/following-sibling::input")
    public WebElement applyCouponInput;

    @FindBy(how = How.CSS, using = "div.user__address  input")
    public WebElement countryInput;

    @FindBy(how = How.XPATH, using = "//div[contains(text(), 'Credit Card Number')]/following-sibling::input")
    public WebElement creditCardInput;

    @FindBy(how = How.CSS, using = "div.payment__types div:nth-of-type(1)")
    public WebElement creditCardPaymentMethod;

    @FindBy(how = How.XPATH, using = "//div[contains(text(), 'CVV Code')]/following-sibling::input")
    public WebElement cvvInput;

    @FindBy(how = How.XPATH, using = "//div[contains(text(), 'Name on Card')]/following-sibling::input")
    public WebElement nameOnCardInput;

    @FindBy(how = How.XPATH, using = "//a[contains(text(), 'Place Order')]")
    public WebElement placeOrderButton;

    @FindBy(how = How.CSS, using = "select:nth-of-type(1)")
    public WebElement selectExpirationMonth;

    @FindBy(how = How.CSS, using = "select:nth-of-type(2)")
    public WebElement selectExpirationYear;

    @FindBy(how = How.CSS, using = "div.user__name > input")
    public WebElement usernameInput;

    public Checkout(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
