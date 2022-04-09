import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import utils.Init;

import java.util.concurrent.TimeUnit;

public class ValidLoginTest {
    public static WebDriver driver;
    public static MainPage page;
    private static String login;
    private static String password;
    @BeforeAll
    public static void setup() {
        Init init = new Init();
        login = init.getProperty("login");
        password = init.getProperty("password");
        driver = init.initWebDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.get("http://maps.yandex.ru/");
        page = new MainPage(driver);
    }
    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Test
    @DisplayName("Valid Login")
    public void validLogin() {
        page.login(login, password);
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='main-user-view']")));
        Assertions.assertTrue(page.getUserView().isDisplayed());
    }
}
