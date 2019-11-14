package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import utils.WebDriverFactory;

import static pages.PagesURLs.loginPage;

public class LoginPage {

  WebDriver driver = null;

  // Login
  private By userNameInput = By.xpath("//input[@name='os_username']");
  private By passwordInput = By.xpath("//input[@name='os_password']");
  private By loginButton = By.xpath("//input[@name='login']");
  private By wrongPasswordLabel = By.xpath("//div[@class='aui-message aui-message-error']");
//  final static String wrongCrdentialsMsg="Sorry, your username and password are incorrect - please try again.";

  //private By errorMsg = By.xpath("//div[@id='usernameerror']");

  public LoginPage() {
    this.driver = WebDriverFactory.getDriver();
  }

  public void navigate() {
    driver.get(loginPage);
  }

  public void enterUserName(String name) {
    driver.findElement(userNameInput).sendKeys(name);
  }

  public void enterPassword(String password) {
    driver.findElement(passwordInput).sendKeys(password);
  }

  public void clickLogin() {
    driver.findElement(loginButton).click();
  }

  public boolean isErrorMessagePresent(String msg){
    try {
      By errorMsg = By.xpath("//div[@id='usernameerror']");
      System.out.println(msg);
      System.out.println(driver.findElement(errorMsg).getText());
      return msg.equals(driver.findElement(errorMsg).getText());
    } catch (NoSuchElementException e){
      System.out.println("Login successfull, error message wasn't found");
      return  false;
    }
  }

  public void loginToJira(String name, String password) {
    enterUserName(name);
    enterPassword(password);
    clickLogin();
  }
}
