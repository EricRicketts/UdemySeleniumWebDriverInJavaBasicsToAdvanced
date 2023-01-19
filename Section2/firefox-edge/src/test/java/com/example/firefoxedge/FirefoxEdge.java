package com.example.firefoxedge;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FirefoxEdge {

  public static void main(String[] args) {
//  by putting the location of the drivers in the PATH variable => C:\Program Files\WebDrivers
//  I can locate any driver without having to call out the driver location explicitly.  My first
//  projects using only the ChromeDriver shows how to explicitly set the path and point to it
//  using Java's System.setProperty().

//  Note for some reason I could not get the gecko driver to work by explicitly pointing to it.
//  It worked when I just included the WebDrivers directory in the PATH variable.

    WebDriver chromeDriver = new ChromeDriver();
//    WebDriver edgeDriver = new EdgeDriver();
//    WebDriver geckoDriver = new FirefoxDriver();

    WebDriver driver = chromeDriver;
//    WebDriver driver = edgeDriver;
//    WebDriver driver = geckoDriver;
    driver.get("https://rahulshettyacademy.com");
    String title = driver.getTitle();
    String currentUrl = driver.getCurrentUrl();
    System.out.println("URL: " + currentUrl + " Page Title: " + title);
    driver.quit();
  }
}
