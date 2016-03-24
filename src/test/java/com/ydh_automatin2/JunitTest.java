package com.ydh_automatin2;
import org.apache.log4j.xml.DOMConfigurator;
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
    Tool tool=new Tool();
    LoginPage loginPage;
    EnrollPage1 enrollPage;
    EnrollPage2 enrollPage2;
    EnrollSuccessPage enrollSuccessPage;
    AdminBasePage adminBasePage;
    ProductListPage productListPage;
    AddNewProductPage addNewProductPage;

    //public static Logger logger = LogManager.getLogger(JunitTest.class.getName());
    @Before
    public void setUp() {
        DOMConfigurator.configure("log4j.xml");//日志运行的配置文件
        System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();//浏览器窗口最大化
        //driver=new FirefoxDriver();
        loginPage = new LoginPage(driver);
        loginPage.get();

    }

    /*   @Test
      public void test1() throws Exception {
           // enrollPage = loginPage.linkPage();
           // enrollPage2=enrollPage.firstSubmit("18600011116");
           // enrollSuccessPage = enrollPage2.secondSubmit("测试的--公司", "联系人", "123456", "12345678@qq.com");
           // enrollSuccessPage.LinkAdminPage();
           //enrollPage.firstSubmit("18607099140");

           adminBasePage = loginPage.login(tool.getExeclData("./lib/dataBase/testdata.xls","loginSheet",2,0), tool.getExeclData("./lib/dataBase/testdata.xls","loginSheet",1,1));
           System.out.println("1234561236666");
           // adminBasePage = loginPage.login("310003502", "123456");
   //        productListPage = adminBasePage.linkProductList();
   //        addNewProductPage = productListPage.linkNewProduct();
   //        productListPage=addNewProductPage.addNewProduct("ceshi_1110e25222", "25", "10");
       }*/
    @Test
    public void test2() throws Exception {
        // enrollPage = loginPage.linkPage();
        // enrollPage2=enrollPage.firstSubmit("18600011116");
        // enrollSuccessPage = enrollPage2.secondSubmit("测试的--公司", "联系人", "123456", "12345678@qq.com");
        // enrollSuccessPage.LinkAdminPage();
        //enrollPage.firstSubmit("18607099140");

        adminBasePage = loginPage.login("310003502", "123456");
        productListPage = adminBasePage.linkProductList();
        addNewProductPage = productListPage.linkNewProduct();
        productListPage=addNewProductPage.addNewProduct(tool.getRandomString(8), "25", "10");
    }
    @After
    public void tearDown() {
        driver.close();
    }
}
