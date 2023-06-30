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

    private void fillAndVerifyProductNumberRangeForEachProduct(Products products, RandomNumber randomNumber) {
        products.fillProductNumbersList(randomNumber);
        Range<Integer> validProductNumberRange = Range.closed(
                products.getMinNumberOfProductsToBuy(), products.getMaxNumberOfProductsToBuy()
        );
        for (int index = 0; index <= products.getNumberOfProductsToBuy() - 1; index++) {
            int productNumber = products.productNumbers.get(index);
            Assert.assertTrue(validProductNumberRange.contains(productNumber));
        }
    }

    private void selectAndVerifyEachProductAddedToCart(Products products, CartButton cartButton) {
        AtomicInteger carCountCounter = new AtomicInteger(0);
        for (int index = 0; index < products.productNumbers.size(); index++) {
            WebElement product = products.allProducts.get(index);
            product.findElement(By.cssSelector("button:nth-of-type(2)")).click();
            carCountCounter.getAndIncrement();
            Boolean carCountUpdated = wait.until(
                    ExpectedConditions.textToBePresentInElement(cartButton.cartQuantity, String.valueOf(carCountCounter))
            );
            Assert.assertTrue(carCountUpdated);
        }
    }
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
        Login login = new Login(driver);
        login.emailInput.sendKeys(Login.USERNAME);
        login.passwordInput.sendKeys(Login.PASSWORD);
        login.loginButton.click();

        Products products = new Products(driver, 1);
        products.setMaxNumberOfProductsToBuy(products.allProducts.size());
        RandomNumber randomNumber = new RandomNumber(
                products.getMaxNumberOfProductsToBuy(), products.getMinNumberOfProductsToBuy()
        );
        products.setNumberOfProductsToBuy(randomNumber.generateRandomNumber());
        List<WebElement> allProducts = wait.until(
                ExpectedConditions.visibilityOfAllElements(products.allProducts)
        );
        Assert.assertEquals(allProducts.size(), products.allProducts.size());

        fillAndVerifyProductNumberRangeForEachProduct(products, randomNumber);
        selectAndVerifyEachProductAddedToCart(products, new CartButton(driver));
    }
}
