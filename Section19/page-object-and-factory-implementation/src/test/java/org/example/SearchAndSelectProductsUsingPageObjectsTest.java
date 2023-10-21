package org.example;

import org.example.SetWebDriverLocation;
import com.google.common.collect.Range;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class SearchAndSelectProductsUsingPageObjectsTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void oneTimeSetup() {
        // this should be a one time setup as this particular version of chrome at the time
        // of this test, 118.0.5993.89, does not have a native driver.  In the future, Selenium
        // can now get the driver automatically
        SetWebDriverLocation.setDriverLocationAndDriverSystemProperty();
    }

    @BeforeMethod
    public void setUp() {
        String url = "https://rahulshettyacademy.com/client";
        int implicitWaitTime = 10;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));

        driver.get(url);
    }

    @AfterMethod
    public void teardown() { driver.quit(); }

    @Test
    public void testSearchAndSelectProductsUsingPageObjects() throws InterruptedException {
        String productName = "ZARA COAT 3";
        Login login = new Login(driver);
        login.loginApplication("elmer.fudd@warnerbros.com", "Bugs123@bunny");

        ProductCatalog productCatalog = new ProductCatalog(driver);
        List<WebElement> allProducts = productCatalog.getProductList();
        Assert.assertNotNull(allProducts);
        productCatalog.addProductToCart(productName);
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
        /*

        fillAndVerifyProductNumberRangeForEachProduct(products, randomNumber);
        selectAndVerifyEachProductAddedToCart(products, new CartButton(driver));
        navigateToMyCartPageAndVerifyPurchases(driver, products);
        verifyPurchasesAddUpToTotalAmount(driver);
        verifyContinueShoppingButton(driver, products);
        verifyNavigateToPlaceOrderPage(driver);
        verifyPlaceOrder(driver);
//        verifyOrderNotification(driver);

         */
    }
}
