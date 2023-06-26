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

public class SearchAndSelectProductsUsingPageObjectsTest {

    private WebDriver driver;
    private WebDriverWait wait;

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
        AtomicInteger carCountCounter = new AtomicInteger(0);

        // login using the Login PageObject
        Login login = new Login(driver);
        login.emailInput.sendKeys(Login.USERNAME);
        login.passwordInput.sendKeys(Login.PASSWORD);
        login.loginButton.click();

        // select items to buy on the main page
        Products products = new Products(driver);
        // randomly chose how many products to buy
        int totalNumberOfProducts = products.allProducts.size();
        // randomly generate number of products to buy from 1 to total number of products inclusive
        int numberOfProductsToBuy = products.randomNumberOfProductsToBuy(totalNumberOfProducts);
        List<WebElement> allProducts = wait.until(
                ExpectedConditions.visibilityOfAllElements(products.allProducts)
        );
        // assert that the expected number of products matches the actual number of products
        Assert.assertEquals(allProducts.size(), totalNumberOfProducts);

        // from 1 to the number of products to buy generate a random number for each index.  The random numbers
        // cannot be duplicated, because of the necessity to repeat the loop until a non-duplicate number is
        // selected a while loop must be used
        while (products.productNumbers.size() < numberOfProductsToBuy) {
            int randomProductNumber = products.randomNumberOfProductsToBuy(totalNumberOfProducts);
            if (!products.productNumbers.contains(randomProductNumber)) {
                products.productNumbers.add(randomProductNumber);
            }
        }

        // first we need to see that each product number is within the expected range
        Range<Integer> validProductNumberRange = Range.closed(1, totalNumberOfProducts);
        for (int index = 0; index <= numberOfProductsToBuy - 1; index++) {
            int productNumber = products.productNumbers.get(index);
            Assert.assertTrue(validProductNumberRange.contains(productNumber));
        }

        CartCount carCount = new CartCount(driver);
        for (int index = 0; index < products.productNumbers.size(); index++) {
            WebElement product = allProducts.get(index);
            product.findElement(By.cssSelector("button:nth-of-type(2)")).click();
            carCountCounter.getAndIncrement();
            Boolean carCountUpdated = wait.until(
                ExpectedConditions.textToBePresentInElement(carCount.cartQuantity, String.valueOf(carCountCounter))
            );
            Assert.assertTrue(carCountUpdated);
        }
    }
}
