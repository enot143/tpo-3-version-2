import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import utils.Init;

import java.util.concurrent.TimeUnit;

public class MakeReviewTest {


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
    public void createRoadTest() {

        //Авторизация пользователя
        page.login(login, password);


        // Выбор места
        new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='input__control _bold'][@type='search']")));
        driver.findElement(By.xpath("//input[@class='input__control _bold'][@type='search']")).sendKeys("ИТМО");
        driver.findElement(By.xpath("//input[@class='input__control _bold'][@type='search']")).sendKeys(Keys.ENTER);
        driver.findElements(By.xpath("//a[@class='search-snippet-view__link-overlay']")).get(0).click();

        // Переход во вкладку отзывов
        driver.findElement(By.xpath("//div[@class='tabs-select-view__title _name_reviews']")).click();

        // Оставляем отзыв
        driver.findElements(By.xpath("//div[@class='business-rating-edit-view__star _size_s _wide']")).get(4).click();

        // Проверка
        Assertions.assertTrue(driver.findElement(By.xpath("//div[@class='business-review-form__title']")).isDisplayed());

        // Подчищаем за собой
        driver.findElement(By.xpath("//button[@class='close-button _color_black _circle _offset_large']")).click();
        driver.findElement(By.xpath("//span[@class='business-review-view__delete']")).click();
    }
}