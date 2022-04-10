import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import utils.Init;

import java.util.concurrent.TimeUnit;

public class CreateRoadTest {


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

        // Выбор первой точки маршрута
        new WebDriverWait(driver, 20).until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class='input__control _bold'][@type='search']")));
        driver.findElement(By.xpath("//input[@class='input__control _bold'][@type='search']")).sendKeys("ИТМО");
        driver.findElement(By.xpath("//input[@class='input__control _bold'][@type='search']")).sendKeys(Keys.ENTER);
        driver.findElements(By.xpath("//a[@class='search-snippet-view__link-overlay']")).get(0).click();

        // Нажатие на кнопку "Выбрать маршрут"
        driver.findElement(By.xpath("//button[@class='button _view_primary _ui _size_medium']")).click();


        // Выбор второй точки маршрута
        driver.findElement(By.xpath("//input[@class='input__control'][@placeholder='Откуда']")).sendKeys("улица Ломоносова, 9");
        driver.findElement(By.xpath("//input[@class='input__control'][@placeholder='Откуда']")).sendKeys(Keys.ENTER);

        // Проверка на корректность работы
        Assertions.assertTrue(driver.findElement(By.xpath("//div[@class='route-list-view__list']")).isDisplayed());


    }
}
