package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Cart {

    @FindBy(how = How.CLASS_NAME, using = "infoWrap")
    public List<WebElement> allProductsList;

    @FindBy(how = How.CSS, using = ".infoWrap h3")
    public List<WebElement> allProductTitles;

    @FindBy(how = How.CSS, using = ".prodTotal > p")
    public List<WebElement> allProductPrices;

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Checkout')]")
    public WebElement checkoutButton;

    @FindBy(how = How.CSS, using = "button[routerlink='/dashboard']")
    public WebElement continueShoppingButton;

    @FindBy(how = How.CSS, using = ".totalRow:nth-of-type(1)")
    public WebElement subTotal;

    @FindBy(how = How.CSS, using = ".totalRow:nth-of-type(2)")
    public WebElement total;

    public Cart(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
