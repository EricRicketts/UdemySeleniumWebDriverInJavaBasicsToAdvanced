package org.example;


import com.google.common.collect.Range;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.*;

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
    public void testSearchAndSelectProductsUsingPageObjects() throws InterruptedException {
        // use AtomicInteger for the convenience methods
        AtomicInteger carCountCounter = new AtomicInteger(0);

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
        // assert that the first product in the list is not null
        Assert.assertNotNull(allProducts.get(0));
        // we have a random number of products to buy, select the list based product to buy
        // which would be 0 to numberOfProducts - 1, of the productNumber is already in the list
        // then continue the loop.  The application does not allow two purchases of the same product.
        for (int index = 0; index <= numberOfProductsToBuy; index++) products.productNumbers.add(index);
        // check that the indices
        Range<Integer> rangeOfIndices = Range.closed(0, products.allProducts.size() - 1);
        for (Integer index : products.productNumbers) {
            Assert.assertTrue(rangeOfIndices.contains(index));
        }

        // now that we have a list of products in the list to buy cycle through the list and buy each one
        for (int productIndex : products.productNumbers) {
            CartCount cartCount = new CartCount(driver);
            carCountCounter.getAndIncrement();
            WebElement product = products.allProducts.get(productIndex);
            product.findElement(By.cssSelector("button:nth-of-type(2)")).click();
            Boolean cartCountUpdated = wait.until(
                    ExpectedConditions.textToBePresentInElement(
                            cartCount.cartQuantity, String.valueOf(carCountCounter)
                    )
            );
            Assert.assertTrue(cartCountUpdated);
        }
        sleep(2000);
    }
}
