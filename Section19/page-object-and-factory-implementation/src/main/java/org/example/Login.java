package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Login {

    public static final String URL = "https://rahulshettyacademy.com/client";
    public static final String USERNAME = "elmer.fudd@warnerbros.com";
    public static final String PASSWORD = "Bugs123@bunny";
    @FindBy(how = How.ID, using = "userEmail")
    public WebElement emailInput;

    @FindBy(how = How.ID, using = "userPassword")
    public WebElement passwordInput;

    @FindBy(how = How.ID, using = "login")
    public WebElement loginButton;

    public Login(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
