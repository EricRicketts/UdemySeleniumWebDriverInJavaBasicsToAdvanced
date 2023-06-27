package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Cart {

    @FindBy(how = How.CSS, using = "button[routerlink='/dashboard/cart'] > label")
    public WebElement cartQuantity;

    public Cart(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
