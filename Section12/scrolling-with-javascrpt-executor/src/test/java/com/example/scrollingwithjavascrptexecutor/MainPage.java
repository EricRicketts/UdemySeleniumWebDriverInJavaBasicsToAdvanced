package com.example.scrollingwithjavascrptexecutor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
    @FindBy(how = How.CSS, using = "table#product")
    public WebElement productTable;

    @FindBy(how = How.CSS, using = "table#product > tbody > tr > td:nth-of-type(4)")
    List<WebElement> productAmounts;

    @FindBy(how = How.CSS, using = "div.totalAmount")
    WebElement totalAmountElement;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
