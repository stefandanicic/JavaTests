import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

public class RegisterPageTests {

    WebDriver driver;
    WebDriverWait wait;

    @BeforeSuite
    public void setUpMethod() {
        String cwd = System.getProperty("user.dir");
        System.setProperty("webdriver.chrome.driver", cwd + "/chromedriver");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @BeforeMethod
    public void beforeEachTest() {
        driver.get("https://www.askgamblers.com/join-p53");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("signup_signup")));
    }

    // Test 1 - Navigate to askgamblers register page and verify it's title is "Create an Account - Sign up to AskGamblers"
    @Test
    public void TC01_navigateToRegisterPage() {
        String title = driver.getTitle();
        Assert.assertEquals(true, title.contains("Create an Account - Sign up to AskGamblers"));
    }

    // Test 2 - Verify there are TC, Cookie policy and Privacy links on Register page form
    @Test
    public void TC02_checkCreateAccountLink() {
        WebElement terms = driver.findElement(By.linkText("Terms & Conditions"));
        WebElement cookie = driver.findElement(By.linkText("Cookie"));
        WebElement privacy = driver.findElements(By.linkText("Privacy")).get(0);

        Assert.assertEquals(true,terms.isDisplayed());
        Assert.assertEquals(true,privacy.isDisplayed());
        Assert.assertEquals(true,cookie.isDisplayed());
    }

    // Test 3 - Verify there are Email, Username and Password fields on Register page
    @Test
    public void TC03_checkMandatoryFields() {
        WebElement email = driver.findElement(By.id("signup_email"));
        WebElement username = driver.findElement(By.id("signup_username"));
        WebElement password = driver.findElement(By.id("signup_plainPassword"));
        WebElement createMyAccount = driver.findElement(By.id("signup_signup"));

        Assert.assertEquals(true,email.isDisplayed());
        Assert.assertEquals(true,username.isDisplayed());
        Assert.assertEquals(true,password.isDisplayed());
        Assert.assertEquals(true,createMyAccount.isDisplayed());
    }

    // Test 4 - Click on Create New Account with blank fields, verify error messages
    @Test
    public void TC04_submitBlankFields() throws InterruptedException {
        WebElement createMyAccount = driver.findElement(By.id("signup_signup"));
        createMyAccount.click();
        Thread.sleep(500);
        WebElement emailError = driver.findElements(By.className("error__text")).get(2);
        WebElement usernameError = driver.findElements(By.className("error__text")).get(3);
        WebElement passwordError = driver.findElements(By.className("error__text")).get(4);

        Assert.assertEquals("Please enter your email address.",emailError.getText());
        Assert.assertEquals("This field is required.",usernameError.getText());
        Assert.assertEquals("This field is required.",passwordError.getText());
    }

    // Test 5 - Try to register new user with email from existing user
    @Test
    public void TC05_submitEmailExistingUser() throws InterruptedException {
        WebElement email = driver.findElement(By.id("signup_email"));
        WebElement createMyAccount = driver.findElement(By.id("signup_signup"));
        email.sendKeys("stefan.danicic@catenamedia.com");
        createMyAccount.click();
        Thread.sleep(500);
        WebElement emailError = driver.findElements(By.className("error__text")).get(2);
        Assert.assertEquals("Email taken. If you have an account please sign in.",emailError.getText());
    }

    @AfterSuite
    public void tearDownMethod() {
        driver.quit();
    }
}
