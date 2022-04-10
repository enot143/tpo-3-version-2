import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import utils.Init;

import java.util.concurrent.TimeUnit;

public class FavouriteNoLoginTest {
    public static WebDriver driver;
    public static MainPage page;

    @BeforeAll
    public static void setup() {
        Init init = new Init();
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
    @DisplayName("Error Add to Favourite without login")
    public void invalidLogin() {
        new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='input__control _bold'][@type='search']")));
        driver.findElement(By.xpath("//input[@class='input__control _bold'][@type='search']")).sendKeys("ИТМО");
        driver.findElement(By.xpath("//input[@class='input__control _bold'][@type='search']")).sendKeys(Keys.ENTER);
        driver.findElements(By.xpath("//a[@class='search-snippet-view__link-overlay']")).get(0).click();
        driver.findElement(By.xpath("//button[@class='button _view_secondary-blue _ui _size_medium']")).click();
        Assertions.assertTrue(driver.findElement(By.xpath("//div[@class='login-dialog-view _type_favorites']")).isDisplayed());
    }
}