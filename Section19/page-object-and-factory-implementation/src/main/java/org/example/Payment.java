package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Payment {

    @FindBy(how = How.CSS, using = ".payment > .payment__title")
    WebElement paymentMethodTitle;

    @FindBy(how = How.CSS, using = ".payment > .payment__types .active")
    WebElement paymentType;

    @FindBy(how = How.CSS, using = ".actions .action__submit")
    WebElement placeOrderButton;

    @FindBy(how = How.XPATH, using = "(//div[@class = 'field'])[1]//input")
    WebElement creditCardNumber;

    @FindBy(how = How.XPATH, using = "//div[contains(text(), 'Expiry Date')]/following-sibling::select[1]")
    WebElement expirationMonth;

    @FindBy(how = How.XPATH, using = "//div[contains(text(), 'Expiry Date')]/following-sibling::select[2]")
    WebElement expirationDay;

    @FindBy(how = How.XPATH, using = "//div[contains(text(), 'CVV Code')]/following-sibling::input")
    WebElement CVVCode;

    @FindBy(how = How.XPATH, using = "(//div[@class = 'field'])[2]")
    WebElement nameOnCard;

    @FindBy(how = How.XPATH, using = "//input[@placeholder = 'Select Country']")
    WebElement selectCountry;

    @FindBy(how = How.XPATH, using = "//button[contains(@class, 'ta-item')][1]")
    WebElement desiredCountry;

    @FindBy(how = How.XPATH, using = "//div[contains(@class, 'actions')]//a[1]")
    WebElement placeOrder;

    public Payment(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
