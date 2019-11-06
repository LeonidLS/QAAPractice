import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import utils.TestListener;
import utils.WebDriverFactory;


public class BaseTest {

    @BeforeTest(groups = "Regression")
    public void setUp() {
        WebDriverFactory.createDriver("Chrome");
    }

    @AfterTest(groups = "Regression")
    public void tearDown() {
        // Close the driver
        WebDriverFactory.getDriver().quit();
//        new TestListener().captureScreenshot();
    }
}
