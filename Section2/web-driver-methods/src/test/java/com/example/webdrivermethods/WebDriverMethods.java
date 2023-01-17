package com.example.webdrivermethods;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverMethods {
  public static void main(String[] args) {
    System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\WebDrivers\\chromedriver.exe");
    WebDriver driver = new ChromeDriver();
    driver.get("https://rahulshettyacademy.com");
    String title = driver.getTitle();
    String currentUrl = driver.getCurrentUrl();
    System.out.println(title);
    System.out.println(currentUrl);
    driver.close(); // note driver.quit() is used to close all browser windows associated with automation
  }
}
