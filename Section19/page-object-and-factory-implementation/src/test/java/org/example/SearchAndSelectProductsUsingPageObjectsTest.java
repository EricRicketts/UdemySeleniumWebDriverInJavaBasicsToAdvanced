package org.example;

import org.example.Cart;
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

        // for future comparison with MyCart section get some information on the products
        List<WebElement> allProductImages = product.allProductImages;
        List<WebElement> allProductTitles = product.allProductTitles;
        List<WebElement> allProductMRPs = product.allProductMRPs;
        ArrayList<String> imageSRCs = new ArrayList<String>();
        ArrayList<String> productTitles = new ArrayList<String>();
        ArrayList<String> productMRPs = new ArrayList<String>();
        for (WebElement productImage : allProductImages) imageSRCs.add(productImage.getAttribute("src"));
        for (WebElement productTitle : allProductTitles) productTitles.add(productTitle.getText());
        for (WebElement productMRP : allProductMRPs) productMRPs.add(productMRP.getText());


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
            Assert.assertEquals(imageSRCs.get(index), cartItemImages.get(index).getAttribute("src"));
            Assert.assertTrue(cartItemNumbers.get(index).getText().startsWith("#626"));
            Assert.assertTrue(productTitles.get(index).equalsIgnoreCase(cartItemTitles.get(index).getText()));
            // need to isolate the MRP price from the MyCart item
            WebElement cartItemMRP = cartItemMRPs.get(index);
            String entireCartItemMRPText = cartItemMRP.getText();
            int lastIndexOfMRPText = entireCartItemMRPText.indexOf("P");
            String cartItemMRPText = entireCartItemMRPText.substring(lastIndexOfMRPText + 2);
            // new compare the productMRP to the cart item MRP
            Assert.assertEquals(productMRPs.get(index), cartItemMRPText);
            Assert.assertTrue(cartItemsStockStatus.get(index).getText().equalsIgnoreCase("in stock"));
            // assert the product totals begin with a '$' and consist only of digits
            Pattern pattern = Pattern.compile("\\$\\s\\d+");
            Matcher matcher = pattern.matcher(cartItemsProductTotals.get(index).getText());
            Assert.assertTrue(matcher.find());
        }



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
