import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import utils.Init;

import java.util.concurrent.TimeUnit;

public class ShowPanoramaTest {
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
    public void showPanorama() {
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='input__control _bold']")));
        page.getSearchInput().sendKeys("ИТМО");
        page.getSearchInput().sendKeys(Keys.ENTER);
        driver.findElements(By.xpath("//a[@class='search-snippet-view__link-overlay']")).get(0).click();
        driver.findElement(By.xpath("//div[@class='panorama-thumbnail-view _view_org']")).click();
        Assertions.assertTrue(driver.findElement(By.xpath("//div[@class='panorama-player']")).isDisplayed());
    }
}
