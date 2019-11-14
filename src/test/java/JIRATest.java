import com.sun.javaws.exceptions.ErrorCodeResponseException;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.NewIssuePage;
import utils.WebDriverFactory;

public class JIRATest extends BaseTest {

  @Feature("Login")
  //@Test(groups = {"Regression"}, dataProvider = "data-provider")
  @Test(groups = {"Smoke"})
  public void loginTest() {
    LoginPage loginPage = new LoginPage();
    loginPage.navigate();
    loginPage.loginToJira("webinar5", "webinar5");
    Assert.assertEquals(WebDriverFactory.getDriver().getCurrentUrl(), "https://jira.hillel.it/secure/Dashboard.jspa");
    //Assert.assertTrue(1 > 1);
  }

  @Feature("Issue")
  @Test(groups = {"Regression"}, dataProvider = "data-provider")
  public void createIssue(String userName, String pswd, String msg) throws InterruptedException {
    LoginPage loginPage = new LoginPage();
    loginPage.navigate();
    loginPage.loginToJira(userName, pswd);
    Assert.assertFalse(loginPage.isErrorMessagePresent(msg));

    // Assert.assertEquals(WebDriverFactory.getDriver().getCurrentUrl(), "https://jira.hillel.it/secure/Dashboard.jspa");
    //Assert.assertTrue(loginPage.);

    NewIssuePage newIssuePage = new NewIssuePage();
    newIssuePage.clickCreateNewIssueButton();
    newIssuePage.enterProjectName("QAAUT-8");
    newIssuePage.enterIssueType("Test");
    newIssuePage.enterIssueSummary("Some Summary");
    newIssuePage.enterIssueDescription("Some Description");
    newIssuePage.clickCreateIssue();
    //Assert.assertTrue(1 > 1);
  }

  @Feature("Login")
  //@Test(groups = {"Regression"}, dataProvider = "data-provider")
  public void dataProviderTest(String userName, String password, String msg) {
    System.out.println(userName);
    System.out.println(password);
    System.out.println(msg);
  }

  @DataProvider(name = "data-provider")
  public Object[][] dataProviderData() {
    return new Object[][]{
            {"webinar5", "webinar5", "Sorry, your username and password are incorrect - please try again."},
    };
  }


}
