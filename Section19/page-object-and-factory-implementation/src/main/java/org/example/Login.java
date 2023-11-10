package org.example;

import org.example.AbstractComponents.AbstractComponent;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Login extends AbstractComponent {
    private WebDriver driver;
    @FindBy(how = How.ID, using = "userEmail")
    public WebElement emailInput;

    @FindBy(how = How.ID, using = "userPassword")
    public WebElement passwordInput;

    @FindBy(how = How.ID, using = "login")
    public WebElement loginButton;

    public ProductCatalog loginApplication(String email, String password) {
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        loginButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(
                ExpectedConditions.invisibilityOf(loginButton)
        );
        return new ProductCatalog(driver);
    }

    public Login(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
}
