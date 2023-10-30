package org.example;

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

import static org.openqa.selenium.devtools.v85.debugger.Debugger.pause;

public class SearchAndSelectProductsUsingPageObjectsTest {
    private static final String chromeDriverProperty = "webdriver.chrome.driver";
    private static final String webDriversFolderPC = "C:\\Program Files\\WebDrivers\\";
    private static final String chromeDriverWindows = "chromedriver.exe";
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void oneTimeSetup() { System.setProperty(chromeDriverProperty, webDriversFolderPC + chromeDriverWindows); }

    @BeforeMethod
    public void setUp() {
        String url = "https://rahulshettyacademy.com/client";
        int implicitWaitTime = 5;
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));

        driver.get(url);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    @AfterMethod
    public void teardown() { driver.quit(); }

    @Test
    public void testSearchAndSelectProductsUsingPageObjects() throws InterruptedException {
        // login
        String productName = "ZARA COAT 3";
        Login login = new Login(driver);
        login.loginApplication("elmer.fudd@warnerbros.com", "Bugs123@bunny");

        // gather all the products on the product page
        ProductCatalog productCatalog = new ProductCatalog(driver);
        List<WebElement> allProductsInProductCatalog = productCatalog.getProductList();
        Assert.assertNotNull(allProductsInProductCatalog);

        // in the past I randomly chose the amount of products to buy but at the time of writing this test
        // the product choice is very limited, I will buy all products.
        CartButton cartButton = new CartButton(driver);
        Product product = new Product(driver);
        List<WebElement> allProducts = wait.until(
                ExpectedConditions.visibilityOfAllElements(product.allProducts)
        );
        int numberOfProducts = allProducts.size();
        for (int index = 0; index < numberOfProducts; index++) {
            WebElement clickableProduct = allProducts.get(index);
            WebElement addToCartButton = clickableProduct.findElement(By.cssSelector("button:last-of-type"));
            Assert.assertTrue(addToCartButton.getTagName().strip().equalsIgnoreCase("button"));
            addToCartButton.click();
            Boolean addToCartUpdated = wait.until(
                ExpectedConditions.textToBePresentInElement(cartButton.cartQuantity, Integer.toString(index + 1))
            );
            Assert.assertTrue(addToCartUpdated);
        }

        // go to the cart button and verify that all items purchased are included in your cart
        cartButton.button.click();
        Boolean onMyCartPage = wait.until(
            ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), "My Cart")
        );
        Assert.assertTrue(onMyCartPage);

//        Assert.assertEquals(Integer.toString(numberOfProducts), cartButton.cartQuantity.getText());
        /*
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
