package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Payment {

    @FindBy(how = How.CSS, using = ".payment > .payment__title")
    WebElement paymentMethodTitle;

    public Payment(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
