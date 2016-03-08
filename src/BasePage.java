import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import javax.xml.ws.WebEndpoint;
import java.security.PrivateKey;

/**
 * Created by Administrator on 2016/3/7.
 */
public class BasePage {
    static WebDriver driver=null;
    Tool tool=new Tool();
    public static Logger logger = LogManager.getLogger(JunitTest.class.getName());
    public WebDriver driver() {
        return driver;
    }
    public void close() {
        driver.close();
    }
}

//登录页面
class LoginPage extends BasePage {
    private WebElement username;
    private WebElement password;
    @FindBy(id = "btn-submit")
    private WebElement btn_submit;
    @FindBy(xpath = "//*[@id=\"myForm\"]/div/div[4]/div/a")
    private WebElement findPassworld;
    @FindBy(xpath = "//*[@id=\"myForm\"]/div/div[7]/div/div/a")
    private WebElement enroll;
    public LoginPage(WebDriver driver) {
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }
    public void get() {
        driver.get("https://sso.dinghuo123.com/login?service=ydh-web"); logger.info(" # # # 进入易订货登录页面 # # # ");
    }
    public void login(String UserName, String PassWorld) {
        username.clear();
        username.sendKeys(UserName);
        password.clear();
        password.sendKeys(PassWorld);
        btn_submit.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String title = driver.getTitle();
        if (title.equals("我的工作台"))
            logger.info(" # # # 成功登录到易订货管理端 # # # ");
        else if(title.equals("在线订货平台"))
            logger.info(" # # # 成功登录到易订货订货端 # # # ");
        else logger.error(" # # #登录失败 # # #");
    }
    //链接到注册页面方法
    public EnrollPage1 linkPage() {
        enroll.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(driver.getTitle().equals("注册免费订货系统|移动订货|分销订货－易订货｜移动订货系统第１品牌"))
            logger.info(" # # # 成功进入易订货注册页面 # # # ");
        else logger.error(" # # # 从登录页面跳转到注册页面失败 # # # ");
        EnrollPage1 enrollPage = new EnrollPage1(driver);
        return enrollPage;
    }
}
//注册页面
class EnrollPage1 extends BasePage {
    @FindBy(id = "username")
    private WebElement username1;
    @FindBy(xpath = "//*[@id='validateCodeForm']/div[1]/div/button")
    private WebElement getMobileVerfyCode;
    @FindBy(id = "btn-register")
    private WebElement btn_register;
    private WebElement mobileVerfyCode;
    private WebElement verfCode;
    @FindBy(xpath = "//*[@id=\"validateCodeForm\"]/div[4]/div/label/a")
    private WebElement backLoginPage;
    public EnrollPage1(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    //注册页面也可以直接使用get方法进入页面
    public void get() {
        driver.get("https://sso.dinghuo123.com/apply/apply2");
    }

    public EnrollPage2 firstSubmit(String mobile) throws InterruptedException {
        username1.sendKeys(mobile);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("$.ajax({url: 'https://sso.dinghuo123.com/ajaxChecking?action=getVerifyCode',"
                + "type: 'get',dataType: 'text',success:function(responseText){"
                + "$('#verfCode').val(responseText);" + "}})");
        getMobileVerfyCode.click();
        Thread.sleep(1000);
        DBread db = new DBread(mobile);
        String activecode = db.getActiveCode(mobile);
        mobileVerfyCode.sendKeys(activecode);
        btn_register.click();
        Thread.sleep(5000);
        EnrollPage2 enrollPage2=new EnrollPage2(driver);
        return enrollPage2;
    }
}
class EnrollPage2 extends BasePage{
    private WebElement companyName;
    private WebElement linkman;
    private WebElement password;
    private WebElement email;
    private WebElement businessTypeId;
    @FindBy(id = "btn-register2")
    private WebElement btn_register2;
    @FindBy(xpath = "//*[@id=\"businessTypeId\"]/option[1]")
    private WebElement businessType1;
    //邀请码
    @FindBy(xpath = "//*[@id=\"writeInforForm\"]/div[6]/div/a")
    private WebElement openRecommendCode;
    private  WebElement recommendCode;
    @FindBy(xpath = "//*[@id=\"writeInforForm\"]/div[9]/div/label/a")
    private WebElement backLoginPage;
    public EnrollPage2(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public EnrollSuccessPage  secondSubmit(String companyname,String linkname,String passWord,String Email) throws InterruptedException {
        companyName.sendKeys(companyname);
        linkman.sendKeys(linkname);
        password.sendKeys(passWord);
        email.sendKeys(Email);
        btn_register2.click();
        Thread.sleep(5000);
        String assertTarget=driver.findElement(By.xpath("//*[@id=\"loginForm\"]/div/div[2]/div[1]/p[2]")).getText();
        System.out.println(assertTarget);
        EnrollSuccessPage enrollSuccessPage=new EnrollSuccessPage(driver);
        return enrollSuccessPage;
    }
}
class EnrollSuccessPage extends BasePage{
    private  WebElement registerToUseBtn;//进入易订货系统按钮
    @FindBy(xpath = "//*[@id=\"loginForm\"]/div/div[2]/div[1]/p[2]")
    private  WebElement assertTarget;
    public EnrollSuccessPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver, this);
    }
    public void LinkAdminPage() throws InterruptedException {
        registerToUseBtn.click();
        Thread.sleep(5000);
        tool.switchToWindow("我的工作台", driver);
    }

}
