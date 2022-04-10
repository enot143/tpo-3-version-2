import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import utils.Init;

import java.util.Set;
import java.util.concurrent.TimeUnit;

public class ChangeLanguageTest {
    public static WebDriver driver;
    public static MainPage page;
    private static String login;
    private static String password;
    private static String newLang;

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
        if (newLang.equals("en")){
            changeLanguage();
        }
        driver.quit();
    }

    @Test
    public void changeLang() {
        page.login(login, password);
        changeLanguage();
        Assertions.assertEquals(newLang, driver.findElements(By.xpath("//html")).get(0).getAttribute("lang"));
    }

    private static void changeLanguage() {
        int index = 0;
        newLang = "ru";
        WebDriverWait wait = new WebDriverWait(driver, 20);

//        page.login(login, password);
        Set<String> oldWindowsSet = driver.getWindowHandles();
        By userDetails = By.xpath("//button[@class='user-settings-view__control']");
        wait.until(ExpectedConditions.visibilityOfElementLocated(userDetails));
        driver.findElement(userDetails).click();
        driver.findElements(By.xpath("//a[@class='user-settings-list-view__item']")).get(0).click();

        String newWindowHandle = (new WebDriverWait(driver, 10))
                .until(new ExpectedCondition<String>() {
                           public String apply(WebDriver driver) {
                               Set<String> newWindowsSet = driver.getWindowHandles();
                               newWindowsSet.removeAll(oldWindowsSet);
                               return newWindowsSet.size() > 0 ?
                                       newWindowsSet.iterator().next() : null;
                           }
                       }
                );
        driver.switchTo().window(newWindowHandle);

        String oldLang = driver.findElements(By.xpath("//html")).get(0).getAttribute("lang");
        System.out.println(oldLang);
        if (oldLang.equals("ru")) {
            index = 4;
            newLang = "en";
        }

        driver.findElements(By.xpath("//button[@class='button select__button button_theme_normal button_arrow_down button_size_m i-bem button_js_inited']")).get(1).click();

        wait.until(ExpectedConditions.elementToBeClickable(driver.findElements(By.xpath("//div[@class='select__item']")).get(index))).click();
//        driver.findElements(By.xpath("//div[@class='select__item']")).get(index).click();
        String selected = driver.findElements(By.xpath("//span[@class='button__text']")).get(1).getText();

        driver.findElements(By.xpath("//option[@class='select__option']")).get(index + 4).submit();

        System.out.println("selected : " + selected);
        if (driver.findElement(By.xpath("//button[@class='button form__save button_theme_action button_size_m i-bem button_js_inited']")).isEnabled()){
            System.out.println("enabled");
        };

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='button form__save button_theme_action button_size_m i-bem button_js_inited']"))).click();
//        wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElements(By.xpath("//html"))));
        driver.navigate().refresh();

    }
}

