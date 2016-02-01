import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
        WebDriver driver=new FirefoxDriver();
        BaiduIndexPage baidu=new BaiduIndexPage(driver);
        baidu.searchFor("踏歌");
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
        searchField.submit();
    }
}





