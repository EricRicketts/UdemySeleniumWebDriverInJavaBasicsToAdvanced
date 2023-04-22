package com.example.junitsoftassertions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MainPage {
    @FindBy(how = How.CSS, using = "div#gf-BIG")
    public WebElement footerElement;

    @FindBy(how = How.CSS, using = "li.gf-li a")
    public List<WebElement> footerAnchorElements;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
