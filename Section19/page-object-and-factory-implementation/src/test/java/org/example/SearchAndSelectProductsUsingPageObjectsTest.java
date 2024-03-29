package org.example;

import org.example.ProductAttributes;
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
        String nameOnCreditCard = "Elmer Fudd";
        String CVVCodeEntry = "456";
        String month = "10";
        String day = "21";
        String country = "United States";
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

        product.addAllProductsToCart(cartButton, wait);
        Assert.assertTrue(cartButton.cartQuantity.getText().equals(Integer.toString(numberOfProducts)));

        // go to the cart button and verify that all items purchased are included in your cart
        // store the essential product attributes for later comparison with my cart items
        ProductAttributes productAttributes = new ProductAttributes(
                product.images, product.titles, product.MRPs
        );
        // navigate to my cart page
        Cart cart = new Cart(driver);
        cart.navigateToMyCartPage(cartButton, wait);

        // assert the number in my cart is the same as the total number of products
        Assert.assertEquals(cart.cartItems.size(), numberOfProducts);

        // verify each item image, title, minimum retail price, buy now button, and trash button
        for (int index = 0; index < numberOfProducts; index++) {
            Assert.assertTrue(
                cart.images.get(index).getAttribute("src")
                        .equalsIgnoreCase(productAttributes.getProductImageSRCs().get(index))
            );
            Assert.assertTrue(
                cart.titles.get(index).getText()
                        .equalsIgnoreCase(productAttributes.getProductTitles().get(index))
            );
            String MRP = cart.MRPs.get(index).getText();
            Assert.assertTrue(
                MRP.substring(MRP.indexOf("$"))
                        .equalsIgnoreCase(productAttributes.getProductMRPs().get(index))
            );
            Assert.assertTrue(
                cart.buyNowButtons.get(index).getText().toLowerCase().contains("buy now")
            );
            Assert.assertTrue(
                cart.trashIcons.get(index).getAttribute("class").equals("fa fa-trash-o")
            );
        }

        // before checking out verify the total price is the sum of each item price
        Assert.assertEquals(cart.getTotalPrice(), cart.sumItemPrices());

        // checkout in order to go to the payment page
        WebElement checkoutButton = wait.until(
                ExpectedConditions.elementToBeClickable(cart.checkoutButton)
        );
        Assert.assertNotNull(checkoutButton);
        // in this case must use the Javascript executor to click the checkout button
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", checkoutButton);

        // verify land on the payment page by asserting the active payment is credit card
        WebElement creditCardPayment = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".active"))
        );
        Assert.assertTrue(creditCardPayment.getText().equalsIgnoreCase("credit card"));

        // verify the format of the credit card number and fill in and verify the name on the card
        Payment payment = new Payment(driver);
        Assert.assertTrue(payment.getCreditCardMatcher().matches());
        payment.nameOnCard.sendKeys(nameOnCreditCard);
        Assert.assertTrue(payment.nameOnCard.getAttribute("value").equalsIgnoreCase(nameOnCreditCard));

        // fill in and verify CVV code
        payment.CVVCode.sendKeys(CVVCodeEntry);
        Assert.assertTrue(payment.CVVCode.getAttribute("value").equals(CVVCodeEntry));

        // enter and verify credit card expiration date
        Select expirationMonth = payment.setAndReturnExpirationMonth(month);
        Select expirationDay = payment.setAndReturnExpirationDay(day);

        Assert.assertTrue(expirationMonth.getFirstSelectedOption().getText().equalsIgnoreCase(month));
        Assert.assertTrue(expirationDay.getFirstSelectedOption().getText().equalsIgnoreCase(day));

        // select and verify country
        WebElement desiredCountry = payment.selectAndReturnCountry(country, wait);
        Assert.assertNotNull(desiredCountry);
        desiredCountry.click();
        Assert.assertTrue(
                payment.selectCountry.getAttribute("value").equalsIgnoreCase(country)
        );

        // place order and verify landing on completed order page
        WebElement orderComplete = payment.placeOrder(wait, driver);
        Assert.assertTrue(orderComplete.getText().trim().equalsIgnoreCase("thankyou for the order."));
    }
}
