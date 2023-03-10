package com.example.waitsexercise;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
  @FindBy(how = How.XPATH, using = "//div[@class='product']")
  public List<WebElement> allProducts;

  @FindBy(how = How.XPATH, using = "//div[@class='product-image']/img[@alt='Walnuts - 1/4 Kg']")
  public WebElement walnuts;

  @FindBy(how = How.XPATH, using = "//div[@class='product-action']/button")
  public List<WebElement> allAddToCartButtons;

  @FindBy(how = How.XPATH, using = "(//div[@class='cart-info'] //tr/td[position()=3])[1]")
  public WebElement cartNumberOfItems;

  @FindBy(how = How.XPATH, using = "(//div[@class='cart-info'] //tr/td[position()=3])[2]")
  public WebElement cartTotalPrice;

  @FindBy(how = How.CSS, using = "img[alt='Cart']")
  public WebElement cartIcon;

  @FindBy(how = How.XPATH, using = "//button[contains(text(), 'PROCEED TO CHECKOUT')]")
  public WebElement proceedToCheckoutButton;

  @FindBy(how = How.XPATH, using = "//button[contains(text(), 'Place Order')]")
  public WebElement placeOrderButton;

  @FindBy(how = How.CSS, using = "span.totAmt")
  public WebElement totalAmount;

  @FindBy(how = How.CSS, using = "span.discountPerc")
  public WebElement discount;

  @FindBy(how = How.CSS, using = "span.discountAmt")
  public WebElement discountAmount;

  @FindBy(how = How.CSS, using = "input.promoCode")
  public WebElement promoCodeInput;

  @FindBy(how = How.CSS, using = "button.promoBtn")
  public WebElement applyPromoCodeButton;

  @FindBy(how = How.CSS, using = "span.promoInfo")
  public WebElement promoCodeApplied;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
