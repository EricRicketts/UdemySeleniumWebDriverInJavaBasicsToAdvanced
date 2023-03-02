package com.example.greenkart;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
  @FindBy(how = How.CSS, using = "h4.product-name")
  public List<WebElement> productTitles;

  @FindBy(how = How.XPATH, using = "//div[@class='product']")
  public List<WebElement> allProducts;

  @FindBy(how = How.XPATH, using = "//div[@class='product-action']/button")
  List<WebElement> addToCartButtonsWithXpath;

  @FindBy(how = How.XPATH, using = "//div[@class='cart-info'] //tr/td[position()=3]")
  public List<WebElement> numberOfItemsAndTotalPrice;

  @FindBy(how = How.XPATH, using = "//button[contains(text(), 'ADDED')]")
  public List<WebElement> allAddedButtons;

  @FindBy(how = How.XPATH, using = "//img[@src='./images/walnuts.jpg']")
  public WebElement walnutImage;

  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
