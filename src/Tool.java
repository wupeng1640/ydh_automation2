import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author 深圳依云科技测试组
 *         function  web自动化测试过程中使用到的工具
 *         Created by Administrator on 2016/1/29.
 */
public class Tool {
    WebDriver driver =null;
    public String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();

    //构造方法
    public Tool(){
        driver = new FirefoxDriver();
    }
    //启动浏览器
    public void setUp(int browers) throws Exception {
        driver.manage().window().maximize();//浏览器窗口最大化
        baseUrl = "https://sso.dinghuo123.com/login?service=ydh-web";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(baseUrl);
    }

    //窗口切换
    /*说明：调用该方法时只需要把需要切换的大窗口的标题放入到，和当前的driver
    * 例如 this.switchToWindow("易订货登录首页",driver)
    * */
    public static boolean switchToWindow(String windowTitle, WebDriver driver) {
        boolean flag = false;
        try {
//		    	将页面上所有的windowshandle放在入set集合当中
            String currentHandle = driver.getWindowHandle();
            Set<String> handles = driver.getWindowHandles();
            for (String s : handles) {
                if (s.equals(currentHandle))
                    continue;
                else {
                    driver.switchTo().window(s);
//		                和当前的窗口进行比较如果相同就切换到windowhandle
//		                判断title是否和handles当前的窗口相同
                    if (driver.getTitle().contains(windowTitle)) {
                        flag = true;
                        System.out.println("Switch to window: "
                                + windowTitle + " successfully!");
                        break;
                    } else
                        continue;
                }
            }
        } catch (Exception e) {
            System.out.printf("Window: " + windowTitle
                    + " cound not found!", e.fillInStackTrace());
            flag = false;
        }
        return flag;
    }


    //随机生成一个字符串（主要用于新增一些内容时 使用）
    public String getRandomString(int length) {
        StringBuffer buffer = new StringBuffer(
                "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        int range = buffer.length();
        for (int i = 0; i < length; i++) {
            sb.append(buffer.charAt(random.nextInt(range)));
        }
        return sb.toString();
    }

    //获取当前时间
    public String getDate() {
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss");//设置时间格式
        String time = format.format(date);
        return time;
    }

}
