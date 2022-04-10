import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import utils.Init;

import java.util.concurrent.TimeUnit;

public class AddToFavoriteTest {
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
//        driver.quit();
    }

    @Test
    @Order(1)
    public void addFavoriteTest() {
        page.login(login, password);
        new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='input__control _bold'][@type='search']")));
        driver.findElement(By.xpath("//input[@class='input__control _bold'][@type='search']")).sendKeys("ИТМО");
        driver.findElement(By.xpath("//input[@class='input__control _bold'][@type='search']")).sendKeys(Keys.ENTER);

        driver.findElements(By.xpath("//a[@class='search-snippet-view__link-overlay']")).get(0).click();

        driver.findElement(By.xpath("//button[@class='button _view_secondary-blue _ui _size_medium']")).click();

        String test = driver.findElements(By.xpath("//a[@class='link-wrapper']")).get(0).getText();

        if (!driver.findElement(By.xpath("//*[@class='checkbox__control'][@type='checkbox']")).isSelected()) {
            driver.findElement(By.xpath("//*[@class='checkbox__control'][@type='checkbox']")).click();
        }

        driver.findElement(By.xpath("//button[@class='button _view_air _size_medium'][@aria-label='Профиль']")).click();

        new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='user-menu-item-view']")));
        driver.findElements(By.xpath("//a[@class='user-menu-item-view']")).get(3).click();

        driver.findElement(By.xpath("//div[@class='bookmarks-root-folder-view__folders-list']")).click();

        String result = driver.findElement(By.xpath("//a[@class='search-snippet-view__link-overlay']")).getText();

        Assertions.assertEquals(result, test);

    }

    @Test
    @Order(2)
    public void deleteFavoriteTest() {

        new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@class='input__control _bold'][@type='search']")));

        driver.findElement(By.xpath("//button[@class='button _view_air _size_medium'][@aria-label='Профиль']")).click();

        new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='user-menu-item-view']")));
        driver.findElements(By.xpath("//a[@class='user-menu-item-view']")).get(3).click();

        driver.findElement(By.xpath("//div[@class='bookmarks-root-folder-view__folders-list']")).click();

        driver.findElement(By.xpath("//a[@class='search-snippet-view__link-overlay']")).click();

        driver.findElement(By.xpath("//button[@class='button _view_secondary-blue _ui _size_medium']")).click();

        if (driver.findElement(By.xpath("//*[@class='checkbox__control'][@type='checkbox']")).isSelected()) {
            driver.findElement(By.xpath("//*[@class='checkbox__control'][@type='checkbox']")).click();
        }

        driver.findElement(By.xpath("//button[@class='button _view_air _size_medium'][@aria-label='Профиль']")).click();

        new WebDriverWait(driver, 20).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[@class='user-menu-item-view']")));
        driver.findElements(By.xpath("//a[@class='user-menu-item-view']")).get(3).click();

        driver.findElement(By.xpath("//div[@class='bookmarks-folder-snippet-view']")).click();

        String text = driver.findElement(By.xpath("//li[@class='bookmarks-folder-header-view__info-item']")).getText();

        Assertions.assertEquals(text, "Список пуст");
    }
}
