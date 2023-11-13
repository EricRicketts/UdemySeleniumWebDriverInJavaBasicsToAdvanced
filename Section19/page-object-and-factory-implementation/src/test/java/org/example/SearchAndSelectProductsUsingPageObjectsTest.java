package org.example;

import com.google.common.collect.ImmutableList;
import org.example.Cart;
import com.google.common.collect.Range;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.openqa.selenium.devtools.v85.debugger.Debugger.pause;

public class SearchAndSelectProductsUsingPageObjectsTest {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void oneTimeSetup() {
        WebDriverManager.chromedriver().setup();
    }

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
        String creditCardNumber = "4542 9931 9292 2293";
        Login login = new Login(driver);

        ProductCatalog productCatalog = login.loginApplication(
                "elmer.fudd@warnerbros.com",
                "Bugs123@bunny"
        );

        // cycle through the products and add them to the cart
        CartButton cartButton = new CartButton(driver);
        Product product = new Product(driver);
        List<WebElement> allProducts = product.allProducts;
        int numberOfProducts = allProducts.size();
        final List<String> PRODUCT_IMAGE_SRCs = new ArrayList<String>(allProducts.size());
        final List<String> PRODUCT_TITLES = new ArrayList<String>(allProducts.size());
        final List<String> PRODUCT_MRPs = new ArrayList<String>(allProducts.size());
        for (int index = 0; index < numberOfProducts; index++) {
            PRODUCT_IMAGE_SRCs.add(product.images.get(index).getAttribute("src"));
            PRODUCT_TITLES.add(product.titles.get(index).getText());
            PRODUCT_MRPs.add(product.MRPs.get(index).getText());
        }
        product.addAllProductsToCart(cartButton, wait);
        Assert.assertTrue(cartButton.cartQuantity.getText().equals(Integer.toString(numberOfProducts)));

        // go to the cart button and verify that all items purchased are included in your cart

        // first navigate to my cart page
        Cart cart = new Cart(driver);
        cart.navigateToMyCartPage(cartButton, wait);

        // assert the number in my cart is the same as the total number of products
        Assert.assertEquals(cart.cartItems.size(), numberOfProducts);

        // setup for the next test were we verify all features of a cart item
        List<WebElement> cartItemsStockStatus = cart.allItemsStockStatus;
        List<WebElement> cartItemsProductTotals = cart.allItemProductTotals;
        List<WebElement> cartItemBuyNowButtons = cart.allItemBuyNowButtons;
        List<WebElement> cartItemTrashIcons = cart.allItemTrashIcons;

        // verify everything about each item, image, item number, minimum retail price, if in stock, actual price
        for (int index = 0; index < numberOfProducts; index++) {
            Assert.assertTrue(
                cart.images.get(index).getAttribute("src")
                    .equalsIgnoreCase(PRODUCT_IMAGE_SRCs.get(index))
            );
            Assert.assertTrue(
                cart.titles.get(index).getText()
                    .equalsIgnoreCase(PRODUCT_TITLES.get(index))
            );
            String MRP = cart.MRPs.get(index).getText();
            Assert.assertTrue(
                MRP.substring(MRP.indexOf("$"))
                    .equalsIgnoreCase(PRODUCT_MRPs.get(index))
            );
        }
        /*
        // verify the total purchase amount adds up to the sum of the product prices
        String totalPriceText = null;
        int calculatedTotalPrice = 0;
        for (WebElement cartItemPrice : cartItemsProductTotals) {
            String[] priceArray = cartItemPrice.getText().split("\\$");
            String priceString = priceArray[1].trim();
            int price = Integer.parseInt(priceString);
            calculatedTotalPrice += price;
        }
        String calculatedTotalPriceText = "$".concat(String.valueOf(calculatedTotalPrice));
        Assert.assertEquals(calculatedTotalPriceText, cart.totalPrice.getText());

        // get the checkout button and checkout, we must first make sure it is visible
        // note the checkout button was not clickable at the given coordinates
        WebElement checkoutButton = wait.until(
                ExpectedConditions.elementToBeClickable(cart.checkoutButton)
        );
        Assert.assertNotNull(checkoutButton);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", checkoutButton);

        // assert that I have landed on the payment page, assert on the title and the place order button
        Payment payment = new Payment(driver);
        WebElement paymentMethodTitle = wait.until(
            ExpectedConditions.visibilityOf(payment.paymentMethodTitle)
        );
        Assert.assertNotNull(paymentMethodTitle);
        WebElement placeOrderButton = wait.until(
            ExpectedConditions.visibilityOf(payment.placeOrderButton)
        );
        Assert.assertNotNull(placeOrderButton);

        // Assert the payment type is a credit card
        Assert.assertTrue(payment.paymentType.getText().equalsIgnoreCase("credit card"));

        // validate credit card number
        Assert.assertEquals(
            payment.creditCardNumber.getAttribute("value"), creditCardNumber
        );

        // Choose expiration month
        String selectMonth = "10";
        String selectDay = "21";
        Select expirationMonth = new Select(payment.expirationMonth);
        expirationMonth.selectByVisibleText(selectMonth);

        // Choose expiration Day
        Select expirationDay = new Select(payment.expirationDay);
        expirationDay.selectByVisibleText(selectDay);

        // verify the above values have been selected
        Assert.assertEquals(expirationMonth.getFirstSelectedOption().getText(), selectMonth);
        Assert.assertEquals(expirationDay.getFirstSelectedOption().getText(), selectDay);

        // set CVV code and Name on Card, then verify both
        String CVVCode = "456";
        String nameOnCard = "Elmer Fudd";
        payment.CVVCode.sendKeys(CVVCode);
        payment.NameOnCard.sendKeys(nameOnCard);

        // select and verify the desired country
        payment.selectCountry.sendKeys("United States");
        WebElement desiredCountry = wait.until(
            ExpectedConditions.visibilityOf(payment.desiredCountry)
        );
        Assert.assertNotNull(desiredCountry);
        desiredCountry.click();

        // place order and verify on completed order page
        payment.placeOrder.click();
        WebElement orderComplete = wait.until(
            ExpectedConditions.visibilityOf(driver.findElement(By.tagName("h1")))
        );
        Assert.assertTrue(orderComplete.getText().trim().equalsIgnoreCase("thankyou for the order."));

         */
    }
}
