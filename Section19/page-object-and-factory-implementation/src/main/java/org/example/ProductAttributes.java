package org.example;

import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class ProductAttributes {
    private static List<String> productImageSRCs;
    private static List<String> productTitles;
    private static List<String> productMRPs;
    public ProductAttributes(
        List<WebElement> productImageSRCElements,
        List<WebElement> productTitleElements,
        List<WebElement> productMRPElements
    )
    {
        productImageSRCs = new ArrayList<>(productImageSRCElements.size());
        productTitles = new ArrayList<>(productTitleElements.size());
        productMRPs = new ArrayList<>(productMRPElements.size());
        for (int index = 0; index < productImageSRCElements.size(); index++) {
            productImageSRCs.add(productImageSRCElements.get(index).getAttribute("src"));
            productTitles.add(productTitleElements.get(index).getText());
            productMRPs.add(productMRPElements.get(index).getText());
        }
    }

    public static List<String> getProductImageSRCs() {
        return productImageSRCs;
    }

    public static List<String> getProductTitles() {
        return productTitles;
    }

    public static List<String> getProductMRPs() {
        return productMRPs;
    }
}
