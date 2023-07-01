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
import java.util.ArrayList;
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

    public void navigateToMyCartPageAndVerifyPurchases(WebDriver driver, Products products) {
        List<String> allProductPrices = new ArrayList<>();
        List<String> allProductTitles = new ArrayList<>();
        Cart myCart = new Cart(driver);
        CartButton cartButton = new CartButton(driver);
        cartButton.button.click();
        WebElement checkoutButton = wait.until(
                ExpectedConditions.visibilityOf(myCart.checkoutButton)
        );
        Assert.assertNotNull(checkoutButton);
        myCart.allProductPrices.forEach((priceElement) -> allProductPrices.add(priceElement.getText()));
        myCart.allProductTitles.forEach((priceElement) -> allProductTitles.add(priceElement.getText()));
        for (WebElement product : products.allProducts) {
            String productTitle = product.findElement(By.tagName("b")).getText();
            Assert.assertTrue(allProductTitles.contains(productTitle));
            String productPrice = product.findElement(By.className("text-muted")).getText();
            Assert.assertTrue(allProductPrices.contains(productPrice));
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

    public void verifyContinueShoppingButton(WebDriver driver, Products products) {
        Cart cart = new Cart(driver);
        cart.continueShoppingButton.click();
        Boolean filterHeadingPresent = wait.until(
                ExpectedConditions.textToBePresentInElement(products.filterHeading, "Filter")
        );
        Assert.assertTrue(filterHeadingPresent);
        CartButton cartButton = new CartButton(driver);
        cartButton.button.click();
        WebElement checkoutButton = wait.until(
                ExpectedConditions.visibilityOf(cart.checkoutButton)
        );
        Assert.assertNotNull(checkoutButton);

    }

    public void verifyPlaceOrder(WebDriver driver) {
        Cart myCart = new Cart(driver);
        myCart.checkoutButton.click();
        Checkout checkout = new Checkout(driver);
        WebElement placeOrderButton = wait.until(
                ExpectedConditions.visibilityOf(checkout.placeOrderButton)
        );
        Assert.assertNotNull(placeOrderButton);
        WebElement creditCardPayment = wait.until(
                ExpectedConditions.visibilityOf(checkout.creditCardPaymentMethod)
        );
        Assert.assertNotNull(creditCardPayment);
        // clear inputs
        checkout.creditCardInput.clear();
        Assert.assertEquals(checkout.creditCardInput.getAttribute("value"), "");
    }

    private void verifyPurchasesAddUpToTotalAmount(WebDriver driver) {
        Cart cart = new Cart(driver);
        int calculatedTotalPrice = 0;
        int filterOutMonetarySymbolAndSpace = 2;
        for (WebElement productPriceElement : cart.allProductPrices) {
            String productPriceText = productPriceElement.getText();
            String productPriceNumericalAmount = productPriceText.substring(filterOutMonetarySymbolAndSpace);
            calculatedTotalPrice += Integer.parseInt(productPriceNumericalAmount);
        }
        int actualTotalPrice = Integer.parseInt(cart.total.getText().substring(1));
        Assert.assertEquals(calculatedTotalPrice, actualTotalPrice);
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
        navigateToMyCartPageAndVerifyPurchases(driver, products);
        verifyPurchasesAddUpToTotalAmount(driver);
        verifyContinueShoppingButton(driver, products);
        verifyPlaceOrder(driver);
    }
}
