package org.example;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SearchAndSelectProductsUsingPageObjects {

    private WebDriver driver;
    private WebDriverWait wait;
    private Login login;

    @BeforeClass
    public void oneTimeSetup() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void setUp() {
        Duration explicitDuration;
        int implicitWaitTime = 10;
        int explicitWaitTime = 15;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));

        explicitDuration = Duration.ofSeconds(explicitWaitTime);
        wait = new WebDriverWait(driver, explicitDuration);
        driver.get(Login.URL);
    }

    @AfterMethod
    public void teardown() { driver.quit(); }

    @Test
    public void testSearchAndSelectProductsUsingPageObjects() {
        // use AtomicInteger for the convenience methods
        AtomicInteger carCount = new AtomicInteger(0);

        // login using the Login PageObject
        login = new Login(driver);
        login.emailInput.sendKeys(Login.USERNAME);
        login.passwordInput.sendKeys(Login.PASSWORD);
        login.loginButton.click();

        // select items to buy on the main page
        Products products = new Products(driver);
        // randomly chose how many products to buy
        int numberOfProducts = products.allProducts.size();
        int numberOfProductsToBuy = products.numberOfProductsToBuy(numberOfProducts);
        List<WebElement> allProducts = wait.until(
                ExpectedConditions.visibilityOfAllElements(products.allProducts)
        );

        // we have a random number of products to buy, select the list based product to buy
        // which would be 0 t0 numberOfProducts = 1, of the productNumber is already in the list
        // then continue the loop.  The application does not allow two purchases of the same product.
        for(int index = 0; index < numberOfProductsToBuy; index++) {
            int productNumber = products.randomSelectionOfProduct(numberOfProducts);
            if (products.productNumbers.contains(productNumber)) {
                continue;
            } else {
                products.productNumbers.add(productNumber);
            }
        }
    }
}
