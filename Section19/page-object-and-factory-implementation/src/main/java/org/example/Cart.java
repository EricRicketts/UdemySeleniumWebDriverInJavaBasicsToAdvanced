package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Cart {

    @FindBy(how = How.CSS, using = ".cart > .cartWrap")
    public List<WebElement> allCartItems;

    @FindBy(how = How.CSS, using = ".cartSection > .itemImg")
    public List<WebElement> itemImages;

    @FindBy(how = How.CSS, using = ".cartSection > .itemNumber")
    public List<WebElement> itemNumbers;

    @FindBy(how = How.CSS, using = ".cartSection > h3")
    public List<WebElement> itemTitles;

    @FindBy(how = How.CSS, using = ".cartSection > p:nth-of-type(2)")
    public List<WebElement> itemMRPs;

    @FindBy(how = How.CSS, using = ".cartSection > .stockStatus")
    public List<WebElement> allItemsStockStatus;

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Checkout')]")
    public WebElement checkoutButton;

    @FindBy(how = How.CSS, using = "button[routerlink='/dashboard']")
    public WebElement continueShoppingButton;

    @FindBy(how = How.XPATH, using = "(//li[contains(@class, 'totalRow')])[2]//span[2]")
    public WebElement total;

    public Cart(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
