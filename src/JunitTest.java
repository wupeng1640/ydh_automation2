import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Created by Administrator on 2016/3/7.
 */
public class JunitTest {
    private WebDriver driver;
    //BasePage basePage=new BasePage();
    LoginPage loginPage;
    EnrollPage enrollPage;


    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
        driver = new ChromeDriver();
        loginPage=new LoginPage(driver);
//        loginPage.get();
//        loginPage.login("31000598","123456");

    }
     @Test
    public void test(){
         loginPage.get();
         //enrollPage= loginPage.linkPage();
         //enrollPage.cc();

         loginPage.login("310005098","123456");
     }
    @After
    public void tearDown(){
        loginPage.close();
    }
}
