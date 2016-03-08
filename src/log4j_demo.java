/**
 * Created by Administrator on 2016/3/8.
 */
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;


public class log4j_demo
{

    static final Logger logger = LogManager.getLogger(log4j_demo.class.getName());
    public static void main(String[] args)
    {

        DOMConfigurator.configure("log4j.xml");

        logger.info("# # # # # # # # # # # # # # # # # # # # # # # # # # # ");
        logger.info("TEST Has Started");
        System.setProperty("webdriver.chrome.driver", ".\\lib\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        //Puts a Implicit wait, Will wait for 10 seconds before throwing exception
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //Launch website
        driver.navigate().to("https://sso.dinghuo123.com/login"); logger.info("打开易订货登录页面");

        //Maximize the browser
        driver.manage().window().maximize();

        // Click on Math Calculators
        driver.findElement(By.id("username")).clear();  logger.info("清除易订货登录的用户名内容");

        // Click on Percent Calculators
        driver.findElement(By.id("username")).sendKeys("310003502"); logger.info("输入登录账号");

        logger.info("# # # # # # # # # # # # # # # # # # # # # # # # # # # ");



        // Enter value 10 in the first number of the percent Calculator
       driver.findElement(By.id("password")).sendKeys("123456"); logger.info("输入用户登录密码");


        logger.info("# # # # # # # # # # # # # # # # # # # # # # # # # # # ");

        // Enter value 50 in the second number of the percent Calculator
        driver.findElement(By.id("btn-submit")).click();  logger.info("点击登录操作！！！");

        logger.info("# # # # # # # # # # # # # # # # # # # # # # # # # # # ");

        // Click Calculate Button
       // driver.findElement(By.xpath(".//*[@id='content']/table/tbody/tr/td[2]/input")).click(); logger.info("Click Calculate Button");

        // Get the Result Text based on its xpath
        //String result = driver.findElement(By.xpath(".//*[@id='content']/p[2]/span/font/b")).getText();  logger.info("Get Text Value");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }    logger.info("进程休眠1秒钟######");
        String result=driver.getTitle();
        if(result.equals("我的工作台"))    logger.info("登录易订货系统成功");

        //Print a Log In message to the screen
       // logger.info(" The Result is " + result);

        //if(result.equals("5"))
        //{
        //    logger.info("The Result is Pass");

       // }
       // else
       // {
       //     logger.error("TEST FAILED. NEEDS INVESTIGATION");
//
     //   }
//
        logger.info("# # # # # # # # # # # # # # # # # # # # # # # # # # # ");

        //Close the Browser.
        driver.close();
    }
}