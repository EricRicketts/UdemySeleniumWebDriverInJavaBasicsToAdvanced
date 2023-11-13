package org.example;

import org.example.AbstractComponents.AbstractComponent;
import org.example.CartButton;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class Product extends AbstractComponent {
    @FindBy(how = How.CSS, using = ".mb-3")
    public List<WebElement> allProducts;

    @FindBy(how = How.CSS, using = ".card-img-top")
    public List<WebElement> images;

    @FindBy(how = How.CSS, using = ".card-body b")
    public List<WebElement> productTitles;

    @FindBy(how = How.CSS, using =".card-body .text-muted")
    public List<WebElement> productMRPs;

    public void addAllProductsToCart(CartButton cartButton, WebDriverWait wait) {
        for (int index = 0; index < this.allProducts.size(); index++) {
            WebElement clickableProduct = allProducts.get(index);
            WebElement addToCartButton = clickableProduct.findElement(By.cssSelector("button:last-of-type"));
            addToCartButton.click();
            // I cannot get the element to be clickable unless I put this wait in
            wait.until(
                ExpectedConditions.textToBePresentInElement(cartButton.cartQuantity, Integer.toString(index + 1))
            );
        }
    }
    public Product(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
}
