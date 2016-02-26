import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.server.handler.SubmitElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ThreadGuard;

/**
 * Created by Administrator on 2016/1/29.
 */
public class BasePage {
  //  private static WebDriver driver;

//    public static WebDriver get(String url) {
//        driver = new FirefoxDriver();
//        driver.get(url);
//        return driver;
//    }
//
//    public static void quit() {
//        driver.quit();
//    }
    public static void  main(String[] args) {
        System.setProperty("webdriver.chrome.driver","D:\\WebDriverServer\\chromedriver.exe");
        WebDriver driver=new ChromeDriver();
        driver.manage().window().maximize();
       // WebDriver driver=new FirefoxDriver();

      //  BaiduIndexPage baidu=new BaiduIndexPage(driver);
       // baidu.searchFor("踏歌");
        LoginPage loginPage=new LoginPage(driver);
        loginPage.loginydh("310005098","123456");
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // loginPage.enroll();
       // loginPage.FindPassword();
        AdminFirstPage adminFirstPage=new AdminFirstPage(driver);
        adminFirstPage.enProduct();
        System.out.println("sadasd");
    }
}


class BaiduIndexPage {
    private WebDriver driver;
    private final String url = "http://www.baidu.com";

    public BaiduIndexPage(WebDriver driver) {
        this.driver = driver;
        driver.get(url);
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "kw")
    private WebElement searchField;

    @FindBy(id = "su")
    private WebElement baidSearchButton;

    public void searchFor(String term) {
        searchField.clear();
        searchField.sendKeys(term);
        baidSearchButton.submit();
    }
}
//class Basepage{
//   // private  WebDriver driver;
//    public WebDriver driver;
//
//    public Basepage(WebDriver driver){
//        this.driver=driver;
//    }
//}
class LoginPage{
    @FindBy(id="username")
    private WebElement Username;
    @FindBy(id="password")
    private  WebElement Password;
    @FindBy(id="btn-submit")
    private  WebElement Submit;
    @FindBy(linkText = "立即注册")
    private WebElement Enroll;
    @FindBy(linkText = "忘记密码？")
    private WebElement Forgetpassword;
    private WebDriver driver;
    private final String url = "https://sso.dinghuo123.com/login?service=ydh-web";
    public LoginPage(WebDriver driver){
        this.driver=driver;
        driver.get(url);
        PageFactory.initElements(driver,this);
    }
    public void loginydh(String username,String password){
        Username.clear();
        Username.sendKeys(username);
        Password.sendKeys(password);
        Submit.click();
        System.out.println(driver.getTitle());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(driver.getTitle());
    }
    public void enroll(){
        Enroll.click();
        System.out.println(driver.getTitle());
    }
    public void FindPassword(){
        Forgetpassword.click();
        System.out.println(driver.getTitle());
    }
}
class AdminFirstPage{
    @FindBy(xpath = "//*[@id='order']/a")
    private WebElement Order;
    @FindBy(xpath = "//*[@id='product']/a")
    private  WebElement Product;
    @FindBy(xpath = "//*[@id='product']/div/ul/li[1]/ul/li[1]/a")
    private WebElement ProductList;
    @FindBy(xpath = "//*[@id='customer']/a")
    private  WebElement Customer;
    @FindBy(xpath = "//*[@id='pay']/a")
    private  WebElement Pay;
    @FindBy(xpath = "//*[@id='message']/a")
    private  WebElement Message;
    @FindBy(xpath = "//*[@id='report']/a")
    private  WebElement  Report;
    @FindBy(className = "info")
    private WebElement Information;
    @FindBy(className = "user menu1")
    private WebElement UserMenul;
    @FindBy(className = "setting menu1")
    private  WebElement SettingMenu1;
    @FindBy(className = "headerService menu1")
    private WebElement HeaderServicMenu1;
    private WebDriver driver;
    public AdminFirstPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
   public void moveMouse(WebDriver driver,WebElement webElement){
       Actions ac=new Actions(driver);
       ac.moveToElement(webElement).perform();
   }
  public void enProduct(){
      //this.moveMouse(this.driver,Product);
      Actions actions=new Actions(this.driver);
      actions.moveToElement(Product).perform();
      ProductList.click();

  }
}




