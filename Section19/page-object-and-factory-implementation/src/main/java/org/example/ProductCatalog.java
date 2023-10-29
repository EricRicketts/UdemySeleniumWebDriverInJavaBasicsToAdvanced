package org.example;

import org.example.AbstractComponents.AbstractComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static java.util.spi.ToolProvider.findFirst;


public class ProductCatalog extends AbstractComponent {

    @FindBy(how = How.CSS, using = ".mb-3")
    public List<WebElement> allProducts;

    public void addProductToCart(String productName) {
        WebElement product = getProductByName(productName);
        WebElement addToCartButton = product.findElement(By.cssSelector(".card-body button:last-of-type"));
        addToCartButton.click();
    }

    public WebElement getProductByName(String productName) {
        return getProductList().stream().filter(product ->
        product.findElement(By.cssSelector("b")).getText().trim().equals(productName)).findFirst().orElse(null);
    }

    public List<WebElement> getProductList() {
        By findBy = By.cssSelector(".mb-3");
        return waitForElementsToAppear(findBy);
    }

    public ProductCatalog(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
}
