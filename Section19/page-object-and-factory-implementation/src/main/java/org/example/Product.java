package org.example;

import org.example.AbstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.devtools.v85.page.Page;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Product extends AbstractComponent {
    @FindBy(how = How.CSS, using = ".mb-3")
    public List<WebElement> allProducts;

    @FindBy(how = How.CSS, using = ".card-img-top")
    public List<WebElement> productImages;

    @FindBy(how = How.CSS, using = ".card-body b")
    public List<WebElement> productTitles;

    @FindBy(how = How.CSS, using =".card-body .text-muted")
    public List<WebElement> productMRPs;

    public Product(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
}
