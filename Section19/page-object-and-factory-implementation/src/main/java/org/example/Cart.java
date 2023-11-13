package org.example;

import org.example.CartButton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Cart {

    @FindBy(how = How.CSS, using = ".cart > .cartWrap")
    public List<WebElement> cartItems;

    @FindBy(how = How.CSS, using = ".cartSection > .itemImg")
    public List<WebElement> images;

    @FindBy(how = How.CSS, using = ".cartSection > h3")
    public List<WebElement> titles;

    @FindBy(how = How.CSS, using = ".cartSection > p:nth-of-type(2)")
    public List<WebElement> MRPs;

    @FindBy(how = How.CSS, using = ".cartSection > .stockStatus")
    public List<WebElement> allItemsStockStatus;

    @FindBy(how = How.XPATH, using = "//button[contains(text(),'Checkout')]")
    public WebElement checkoutButton;

    @FindBy(how = How.CSS, using = ".prodTotal > p")
    public List<WebElement> allItemProductTotals;

    @FindBy(how = How.CSS, using = ".cartSection .btn-primary")
    public List<WebElement> buyNowButtons;

    @FindBy(how = How.CSS, using = ".cartSection i")
    public List<WebElement> trashIcons;

    @FindBy(how = How.CSS, using = ".totalRow:nth-of-type(2) .value")
    public WebElement totalPrice;

    @FindBy(how = How.CSS, using = "button[routerlink='/dashboard']")
    public WebElement continueShoppingButton;

    public void navigateToMyCartPage(CartButton cartButton, WebDriverWait wait) {
        cartButton.button.click();
        wait.until(
            ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), "My Cart")
        );
    }

    public Cart(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
