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

    @FindBy(how = How.XPATH, using = "//input[@value='4542 9931 9292 2293']")
    WebElement creditCardNumber;

    @FindBy(how = How.XPATH, using = "//div[contains(text(), 'Expiry Date')]/following-sibling::select[1]")
    WebElement expirationMonth;

    @FindBy(how = How.XPATH, using = "//div[contains(text(), 'Expiry Date')]/following-sibling::select[2]")
    WebElement expirationDay;

    public Payment(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
