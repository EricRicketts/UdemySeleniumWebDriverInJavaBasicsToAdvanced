package org.example;

import org.example.Cart;
import com.google.common.collect.Range;
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
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        // login and verify the number of products on the product catalog page
        String productName = "ZARA COAT 3";
        String creditCardNumber = "4542 9931 9292 2293";
        Login login = new Login(driver);

        ProductCatalog productCatalog = login.loginApplication(
                "elmer.fudd@warnerbros.com",
                "Bugs123@bunny"
        );
        Assert.assertEquals(productCatalog.allProducts.size(), 3);

        // Buy all products on the product catalog page
        CartButton cartButton = new CartButton(driver);
        Product product = new Product(driver);

        // cycle through the products and add them to the cart
        List<WebElement> allProducts = product.allProducts;
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

        // assert the number in cart is the same as the total number of products
        Cart cart = new Cart(driver);
        List<WebElement> cartItems = cart.allCartItems;
        int numberOfCartItems = cartItems.size();
        Assert.assertEquals(numberOfCartItems, numberOfProducts);

        // setup for the next test were we verify all features of a cart item
        List<WebElement> cartItemImages = cart.itemImages;
        List<WebElement> cartItemNumbers = cart.itemNumbers;
        List<WebElement> cartItemTitles = cart.itemTitles;
        List<WebElement> cartItemMRPs = cart.itemMRPs;
        List<WebElement> cartItemsStockStatus = cart.allItemsStockStatus;
        List<WebElement> cartItemsProductTotals = cart.allItemProductTotals;
        List<WebElement> cartItemBuyNowButtons = cart.allItemBuyNowButtons;
        List<WebElement> cartItemTrashIcons = cart.allItemTrashIcons;

        // verify everything about each item, image, item number, minimum retail price, if in stock, actual price
        for (int index = 0; index < numberOfCartItems; index++) {
            Assert.assertTrue(cartItemImages.get(index).getAttribute("src").contains("https://"));
            Assert.assertTrue(cartItemNumbers.get(index).getText().startsWith("#626"));
//            Assert.assertTrue(productTitles.get(index).equalsIgnoreCase(cartItemTitles.get(index).getText()));
            // need to isolate the MRP price from the MyCart item
            WebElement cartItemMRP = cartItemMRPs.get(index);
            String entireCartItemMRPText = cartItemMRP.getText();
            int lastIndexOfMRPText = entireCartItemMRPText.indexOf("P");
            String cartItemMRPText = entireCartItemMRPText.substring(lastIndexOfMRPText + 2);
            // new compare the productMRP to the cart item MRP
//            Assert.assertEquals(productMRPs.get(index), cartItemMRPText);
            Assert.assertTrue(cartItemsStockStatus.get(index).getText().equalsIgnoreCase("in stock"));
            // assert the product totals begin with a '$' and consist only of digits
            Pattern pattern = Pattern.compile("\\$\\s\\d+");
            Matcher matcher = pattern.matcher(cartItemsProductTotals.get(index).getText());
            Assert.assertTrue(matcher.find());
            Assert.assertTrue(cartItemBuyNowButtons.get(index).getText().toLowerCase().trim().contains("buy now"));
            String trashIconClass = cartItemTrashIcons.get(index).getAttribute("class");
            Assert.assertTrue(trashIconClass.contains("fa-trash-o"));
        }

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
    }
}
