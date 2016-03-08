import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by Administrator on 2016/3/7.
 */
public class JunitTest {
    private WebDriver driver;
    //BasePage basePage=new BasePage();
    LoginPage loginPage;
    EnrollPage1 enrollPage;
    EnrollPage2 enrollPage2;
    EnrollSuccessPage enrollSuccessPage;

    //public static Logger logger = LogManager.getLogger(JunitTest.class.getName());
    @Before
    public void setUp() {
        DOMConfigurator.configure("log4j.xml");//日志运行的配置文件
        System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();//浏览器窗口最大化
        loginPage = new LoginPage(driver);
//        loginPage.get();
//        loginPage.login("31000598","123456");
        loginPage.get();

    }

    @Test
    public void test() throws InterruptedException {
        System.out.println(driver.getTitle());
        enrollPage = loginPage.linkPage();
        enrollPage.firstSubmit("18600011111");
        //enrollSuccessPage = enrollPage2.secondSubmit("测试的--公司", "联系人", "123456", "12345678@qq.com");
        //enrollSuccessPage.LinkAdminPage();
        //enrollPage.firstSubmit("18607099140");

        //loginPage.login("310003502","123456");
    }

    @After
    public void tearDown() {
        loginPage.close();
    }
}
