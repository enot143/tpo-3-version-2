package pages;

import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

@Data
public class MainPage {
    public MainPage(WebDriver webDriver) {
        PageFactory.initElements(webDriver, this);
        this.driver = webDriver;
    }


//    <button data-t="button:default" type="button" class="Button2 Button2_size_l Button2_view_default" autocomplete="off"><span class="Button2-Text">Почта</span></button>
    public void login(String login, String password) {
        WebDriverWait wait = new WebDriverWait(driver,20);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@class='button _view_air _size_medium'][@aria-label='Профиль']")));
        profileButton.click();
        loginButton.click();
        By phoneButton = By.xpath("//button[@class='Button2 Button2_size_l Button2_view_default']");
        try{
            if (driver.findElement(phoneButton).isDisplayed()){
                if (!driver.findElement(phoneButton).getText().equals("Почта")){
                    driver.findElement(By.xpath("//button[@class='Button2 Button2_size_l Button2_view_clear']")).click();;
                }
            }
        }catch (NoSuchElementException e){
            System.out.println();
        }
        emailInput.sendKeys(login);
        signIn.click();
        passwordInput.sendKeys(password);
        signIn.click();
    }

    public WebDriver driver;

    @FindBy(xpath = "//button[@class='button _view_air _size_medium'][@aria-label='Профиль']")
    private WebElement profileButton;

    @FindBy(xpath = "//a[@class='button _view_primary _ui _size_medium _link']")
    private WebElement loginButton;

    @FindBy(xpath = "//input[@class='Textinput-Control'][@name='login']")
    private WebElement emailInput;

    @FindBy(xpath = "//input[@class='Textinput-Control'][@name='passwd']")
    private WebElement passwordInput;

    @FindBy(xpath = "//button[@class='Button2 Button2_size_l Button2_view_action Button2_width_max Button2_type_submit']")
    private WebElement signIn;

    @FindBy(xpath = "//div[@class='main-user-view']")
    private WebElement userView;

    @FindBy(xpath = "//input[@class='input__control _bold']")
    private WebElement searchInput;
}

