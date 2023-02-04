package com.example.identifyelementwithidandnamelocators;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

/*
  I intend to put a lot of comments on these two classes for two reasons: first, to help me better understand how these
  Selenium annotations and methods work and secondly, to help me understand what I was doing when I visit the
  code a long time from now.
 */
public class MainPage {
  /*
    This is some very important information on the @FindBy Annotation.  Quoting from an article, the @FindBy
    annotation is used to find one or more WebElements using a single criteria.  I personally like to make use of
    the more explicit implementation.  Some examples below:

    @FindBy(how = How.CLASS_NAME, using = "foo")
    public List<WebElement> elementsWithClassNameFoo

    @FindBy(how = How.ID, using = "bar")
    public WebElement elementWithIdBar

    Here are the abbreviated versions:

    @FindBy(className = "foo")
    public List<WebElement> elementsWithClassNameFoo

    @FindBy(id = "bar")
    public WebElement elementWithIdBar

   So in each case above Java returns either a public attribute (field) List of WebElements or a single WebElement
   In the case of MainPage, @FindBy is used extensively to declare a number of public fields.  The notable distinction
   about these public fields is that they are initialized by a search through the PageObject.  Since they are class
   fields they can be referred directly in the class or as an attribute on an instance of the class which is what we
   see occurring in MainPageTest.

   In each case above @FindBy defines how to find the element list or single element (by class name or by id) and
   the annotation also defines what object is returned after finding the element, either a List<WebElement> or a single
   WebElement.

   In my opinion there is no need to document each of the @FindBy annotations as the arguments to each annotation
   and the object or List returned are self-explanatory.
   */
  @FindBy(how = How.ID, using = "inputUsername")
  public WebElement inputUserName;

  @FindBy(how = How.NAME, using = "inputPassword")
  public WebElement inputPassword;

  @FindBy(how = How.CSS, using = "button.submit.signInBtn")
  public WebElement signInButton;

  @FindBy(how = How.CSS, using = "p.error")
  public WebElement errorParagraph;

  @FindBy(how = How.LINK_TEXT, using = "Forgot your password?")
  public WebElement forgotPasswordLink;

  @FindBy(how = How.XPATH, using = "//input[@placeholder='Name']")
  public WebElement forgotPasswordName;

  @FindBy(how = How.CSS, using = "input[placeholder='Email']")
  public WebElement forgotPasswordEmail;

  @FindBy(how = How.XPATH, using = "//input[@type='text'][1]")
  public WebElement getForgotPasswordNameXpathArray;

  @FindBy(how = How.XPATH, using = "//input[@type='text'][2]")
  public WebElement getForgotPasswordEmailXpathArray;

  @FindBy(how = How.CSS, using = "input[type='text']:nth-child(2)")
  public WebElement getForgotPasswordNameCssArray;

  @FindBy(how = How.CSS, using = "input[type='text']:nth-child(3)")
  public WebElement getForgotPasswordEmailCssArray;

  @FindBy(how = How.XPATH, using = "//form/h2")
  public WebElement getForgotPasswordH2;

  @FindBy(how = How.XPATH, using = "//form/input[1]")
  public WebElement getForgotPasswordNameXpathTags;

  @FindBy(how = How.XPATH, using = "//form/input[2]")
  public WebElement getForgotPasswordEmailPathTags;

  @FindBy(how = How.XPATH, using = "//form/input[3]")
  public WebElement getForgotPasswordPhoneNumberXpathTags;

  @FindBy(how = How.CSS, using = "button.reset-pwd-btn")
  public WebElement resetLoginButton;

  @FindBy(how = How.XPATH, using = "//form/p[@class='infoMsg']")
  public WebElement getForgotPasswordInformationalMessage;

  /*
  Below is the constructor for the MainPage class it takes as a single argument a WebDriver, which is highly
  likely to have the web page url included.  The constructor calls a static method on the PageFactory class, passing
  in the WebDriver argument and the current object being instantiated (this).
   */
  public MainPage(WebDriver driver) {
    PageFactory.initElements(driver, this);
  }
}
