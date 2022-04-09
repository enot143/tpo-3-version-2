import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import utils.Init;

import java.util.concurrent.TimeUnit;

public class InvalidLoginTest {
    public static WebDriver driver;
    public static MainPage page;
    private static String login;

    @BeforeAll
    public static void setup() {
        Init init = new Init();
        login = init.getProperty("login");
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
    @DisplayName("Invalid Login")
    public void invalidLogin() {
        page.login(login, "password");
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='Textinput-Hint Textinput-Hint_state_error']")));
        Assertions.assertTrue(driver.findElement(By.xpath("//div[@class='Textinput-Hint Textinput-Hint_state_error']")).isDisplayed());
    }
}
