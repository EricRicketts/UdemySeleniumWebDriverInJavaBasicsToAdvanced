package com.example.screencaptureuxvalidation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

// page_url = https://www.jetbrains.com/
public class MainPage {
    @FindBy(how = How.TAG_NAME, using = "footer")
    public WebElement footer;

    @FindBy(how = How.XPATH, using = "//input[contains(@class,'form-control') and @name='name']")
    public WebElement nameInput;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
