import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class WebAppPageTitleTest {

    WebDriver driver;
    String url;
    WebDriverWait wait;

    // This method will be called once before all tests - here we will initialize driver and wait
    @BeforeSuite
    public void setUpMethod() {
        //Get the directory path to file 'chromedriver' which is: ./JavaTests/SeleniumTest1/chromedriver
        String cwd = System.getProperty("user.dir");
        //initialize ChromeDriver with properties and path to chromedriver
        System.setProperty("webdriver.chrome.driver", cwd + "/chromedriver");
        driver = new ChromeDriver();
        //initialize Wait (we'll use this to wait for specific state of element - visible, clickable....
        wait = new WebDriverWait(driver, 10);
    }

    // This method will be called before each @Test method
    @BeforeMethod
    public void beforeEachTest() {
        driver.get("https://www.askgamblers.com/login-p46");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("login")));
    }

    // Test 1 - Navigate to askgamblers and verify it's title is "Sign in to AskGamblers"
    @Test
    public void TC01_navigateToLoginPage() {
        String title = driver.getTitle();
        Assert.assertEquals(true, title.contains("Sign in to AskGamblers"));
    }

    // Test 2 - We are on Login page, verify there is a button CREATE ACCOUNT, click on it and verify URL contains /join
    @Test
    public void TC02_checkCreateAccountLink() {
        WebElement createAccount = driver.findElement(By.linkText("CREATE ACCOUNT"));
        Assert.assertEquals(true,createAccount.isDisplayed());
        createAccount.click();
        url = driver.getCurrentUrl();
        Assert.assertEquals(true,url.contains("join"));
    }

    // Test 3 - We are on Login page, verify there is a button Forgot password?, click on it and verify URL contains /forgot
    @Test
    public void TC03_checkForgotPasswordLink() {
        WebElement forgotPassword = driver.findElement(By.linkText("Forgot password?"));
        Assert.assertEquals(true,forgotPassword.isDisplayed());
        forgotPassword.click();
        url = driver.getCurrentUrl();
        Assert.assertEquals(true,url.contains("forgot"));
    }

    // Test 4 - Verify there are 2 visible fields - Email/Username and Password
    @Test
    public void TC04_checkForEmailAndPassword() {
        WebElement username = driver.findElement(By.name("_username"));
        WebElement password = driver.findElement(By.name("_password"));

        Assert.assertEquals(true,username.isDisplayed());
        Assert.assertEquals(true,password.isDisplayed());
    }

    // Test 5 - Without values for email & password, click on Log In button and verify there is an error for invalid email or pass
    @Test
    public void TC05_tryLoginWithoutUsernameAndPassword() {
        WebElement loginButton = driver.findElement(By.name("login"));
        Assert.assertEquals(true,loginButton.isDisplayed());
        loginButton.click();
        WebElement error = driver.findElements(By.className("error__text")).get(2);
        Assert.assertEquals("Invalid username/email or password.",error.getText());
    }

    // This method is called once after all tests are done, to close the webdriver (browser)
    @AfterSuite
    public void tearDownMethod() {
        driver.quit();
    }
}
