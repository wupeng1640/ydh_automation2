import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.xml.ws.WebEndpoint;
import java.security.PrivateKey;

/**
 * Created by Administrator on 2016/3/7.
 */
public class BasePage {
    private static WebDriver driver;

 /*   public static WebDriver driver() {
        return driver;
    }*/

    public BasePage() {
        System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
        driver = new ChromeDriver();
        PageFactory.initElements(driver, this);
    }

    public void close() {
        driver.close();
    }
}

class LoginPage  {
    private WebDriver driver;
    public LoginPage(WebDriver driver) {
        this.driver=driver;

        PageFactory.initElements(driver, this);
    }

    public void close() {
        driver.close();
    }
    public  WebDriver driver() {
        return driver;
    }
    private WebElement username;
    private WebElement password;
    @FindBy(id="btn-submit")
    private WebElement btn_submit;
    @FindBy(xpath = "//*[@id=\"myForm\"]/div/div[4]/div/a")
    private WebElement findPassworld;
    @FindBy(xpath = "//*[@id=\"myForm\"]/div/div[7]/div/div/a")
    private WebElement enroll;

    public void get() {
        driver.get("https://sso.dinghuo123.com/login?service=ydh-web");
    }
    public void login(String UserName, String PassWorld) {
        username.clear();
        username.sendKeys(UserName);
        password.clear();
        password.sendKeys(PassWorld);
        btn_submit.click();
        String tt=driver.getTitle();
        System.out.println(tt);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String  title=driver.getTitle();
        System.out.println(title);
        if(title.equals("我的工作台"))
            System.out.println("登录成功了");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public EnrollPage linkPage(){
        enroll.click();
        EnrollPage enrollPage=new EnrollPage(driver);
        return  enrollPage;
    }
}
class EnrollPage {
    private WebDriver driver;
    public EnrollPage(WebDriver driver) {
        //this.driver=driver;
       // System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
       // driver = new ChromeDriver();
        PageFactory.initElements(driver, this);
    }

    public void close() {
        driver.close();
    }
    @FindBy(id="username")
    private WebElement username1;
    public void cc(){
        username1.sendKeys("18720091224");
    }


}
