package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class CartCount {

    @FindBy(how = How.CSS, using = "button[routerlink='/dashboard/cart'] > label")
    public WebElement cartQuantity;

    public CartCount(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
