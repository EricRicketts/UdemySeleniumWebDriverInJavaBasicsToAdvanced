package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Checkout {

    @FindBy(how = How.CSS, using = "div.user__address  input")
    public WebElement country;

    @FindBy(how = How.XPATH, using = "//div[contains(text(), 'Credit Card Number')]/following-sibling::input")
    public WebElement creditCardInput;

    @FindBy(how = How.CSS, using = "div.payment__types div:nth-of-type(1)")
    public WebElement creditCardPaymentMethod;

    @FindBy(how = How.XPATH, using = "//a[contains(text(), 'Place Order')]")
    public WebElement placeOrderButton;

    @FindBy(how = How.CSS, using = "div.user__name > input")
    public WebElement usernameInput;

    public Checkout(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
