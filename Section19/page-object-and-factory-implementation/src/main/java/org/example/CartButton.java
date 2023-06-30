package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class CartButton {

    @FindBy(how = How.CSS, using = "button[routerlink='/dashboard/cart']")
    public WebElement button;

    @FindBy(how = How.CSS, using = "button[routerlink='/dashboard/cart'] > label")
    public WebElement cartQuantity;

    public CartButton(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
