import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


/**
 * Created by Administrator on 2016/3/7.
 */
public class BasePage {
    static WebDriver driver = null;
    Tool tool = new Tool();
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
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void get() {
        driver.get("https://sso.dinghuo123.com/login?service=ydh-web");
        logger.info(" # # # 进入易订货登录页面 # # # ");
    }

    public AdminBasePage login(String UserName, String PassWorld) {
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
        else if (title.equals("在线订货平台"))
            logger.info(" # # # 成功登录到易订货订货端 # # # ");
        else logger.error(" # # #登录失败 # # #");
        AdminBasePage adminBasePage=new AddNewProductPage(driver);
        return adminBasePage;
    }

    //链接到注册页面方法
    public EnrollPage1 linkPage() {
        enroll.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (driver.getTitle().equals("注册免费订货系统|移动订货|分销订货－易订货｜移动订货系统第１品牌"))
            logger.info(" # # # 成功进入易订货注册页面 # # # ");
        else logger.error(" # # # 从登录页面跳转到注册页面失败 # # # ");
        EnrollPage1 enrollPage = new EnrollPage1(driver);
        return enrollPage;
    }
}

//注册页面1
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
    @FindBy(xpath = "//*[@id=\"registerForm\"]/div[1]/div/span/span/span[3]")
    private WebElement judgeMobile;

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
        if (judgeMobile.getText().equals("用户名重复"))
            logger.error(" # # # 输入的手机号码已经被注册 # # # ");
        else {
            btn_register.click();
            logger.info("# # # " + mobile + "已用于注册易订货系统 # # #");
            Thread.sleep(5000);
        }
        EnrollPage2 enrollPage2 = new EnrollPage2(driver);
        return enrollPage2;
    }
}

//注册页面2
class EnrollPage2 extends BasePage {
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
    private WebElement recommendCode;
    @FindBy(xpath = "//*[@id=\"writeInforForm\"]/div[9]/div/label/a")
    private WebElement backLoginPage;

    public EnrollPage2(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public EnrollSuccessPage secondSubmit(String companyname, String linkname, String passWord, String Email) throws InterruptedException {
        companyName.sendKeys(companyname);
        linkman.sendKeys(linkname);
        password.sendKeys(passWord);
        email.sendKeys(Email);
        btn_register2.click();
        Thread.sleep(5000);
        String assertTarget = driver.findElement(By.xpath("//*[@id=\"loginForm\"]/div/div[2]/div[1]/p[2]")).getText();
        if (assertTarget.equals("恭喜您开通了5用户，终身免费的易订货免费服务！您同时也成为贵公司的易订货系统管理员。")) {
            logger.info(" # # # 注册易订货账号成功 # # # ");
        } else {
            logger.error(" # # # 注册易订货失败  # # # ");
        }
        EnrollSuccessPage enrollSuccessPage = new EnrollSuccessPage(driver);
        return enrollSuccessPage;
    }
}

//注册成功页面
class EnrollSuccessPage extends BasePage {
    private WebElement registerToUseBtn;//进入易订货系统按钮
    @FindBy(xpath = "//*[@id=\"loginForm\"]/div/div[2]/div[1]/p[2]")
    private WebElement assertTarget;

    public EnrollSuccessPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void LinkAdminPage() throws InterruptedException {
        registerToUseBtn.click();
        Thread.sleep(5000);
        tool.switchToWindow("我的工作台", driver);
        if (driver.getTitle().equals("我的工作台")) {
            logger.info(" # # # 成功通过注册进入易订货系统管理端 # # # ");
        } else logger.error(" # # # 注册流程进入易订货失败 # # # ");
    }
}

//管理端首页
class AdminBasePage extends BasePage {
    private WebElement order;
    private WebElement product;
    private WebElement customer;
    private WebElement pay;
    private WebElement message;
    private WebElement report;

    public AdminBasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ProductListPage linkProductList() {
        product.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (driver.getTitle().equals("商品列表"))
            logger.info("# # # 进入商品列表成功 # # # ");
        else logger.error(" # # # 进入商品列表失败 # # # ");
        ProductListPage productList=new ProductListPage(driver);
        return productList;
    }
}

class ProductListPage extends AdminBasePage {
    @FindBy(xpath = "/html/body/div[2]/div[2]/div/div[2]/div[1]/div[2]/a[3]")
    private WebElement newProduct;

    public ProductListPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public AddNewProductPage linkNewProduct() {
        newProduct.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (driver.getTitle().equals("新增商品"))
            logger.info("# # # 成功进入新增商品页面 # # # ");
        else logger.error(" # # # 进入新增商品页面失败 # # # ");
        AddNewProductPage addNewProductPage= new AddNewProductPage(driver);
        return  addNewProductPage;
    }
}

class AddNewProductPage extends AdminBasePage {
    private WebElement productName;//商品名称
    private WebElement productTypeName;//商品分类
    @FindBy(xpath = "//*[@id=\"baseInfoForm\"]/div/div[2]/div[2]/div/div/div/a")
    private WebElement firstType;
    private WebElement productUnitName;//商品单位
    @FindBy(xpath = "//*[@id=\"COMBO_WRAP\"]/div[2]/div[1]/div[1]")
    private WebElement firstUnit;
    private WebElement productOnStore;//上下架状态
    private WebElement keywords;//搜索关键字
    private WebElement sortNum;//商品排序值
    private WebElement marketPriceMain;//市场价
    private WebElement costPriceMain;//成本价
    @FindBy(xpath = "/html/body/div[2]/div[2]/div[1]/div[2]/div/div[1]/div/div/div[2]/label[1]")
    private WebElement productLabel1;//商品标签
    @FindBy(xpath = "/html/body/div[2]/div[2]/div[1]/div[2]/div/div[1]/div/div/div[2]/label[2]")
    private WebElement productLabel2;
    @FindBy(xpath = "/html/body/div[2]/div[2]/div[1]/div[2]/div/div[1]/div/div/div[2]/label[3]")
    private WebElement productLabel3;
    @FindBy(xpath = "/html/body/div[2]/div[2]/div[1]/div[2]/div/div[3]/table/tbody/tr/td[2]/div/img")
    private WebElement addProductPicture;
    @FindBy(linkText = "修改")
    private WebElement modifyPicture;
    @FindBy(xpath = "/html/body/div[1]/table/tbody/tr[3]/td[2]/div/input[1]")
    private WebElement defindButton;//添加图片后的确认按钮
    @FindBy(xpath = "//button")
    private WebElement protectButton;//保存新增按钮
    @FindBy(xpath = "/html/body/div[3]/div[2]/div[2]/button[2]")
    private WebElement cancelButton;//取消新增按钮

    public AddNewProductPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ProductListPage addNewProduct(String productname, String  markPrice, String  costPrice) {
        productName.clear();
        productName.sendKeys(productname);
        productTypeName.click();
        firstType.click();
        productUnitName.click();
        firstUnit.click();
        productLabel1.click();
        productLabel2.click();
        Actions ac = new Actions(driver);
        ac.moveToElement(addProductPicture).perform();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
       // driver.switchTo().frame("productImgUpload");
        //driver.findElement(By.xpath("/html/body/div/div/div[2]/div[2]/span/span")).click();
        // 启动上传图片autoit
        //String path = "C:/Users/Administrator/Desktop/Upload.exe";
        //execShell("C:/Upload.exe");
       //execShell("./lib/goods_picture/goods1.exe");}
        //Thread.sleep(3000);
        //driver.switchTo().defaultContent();
        marketPriceMain.sendKeys(markPrice);
        costPriceMain.sendKeys(costPrice);
        protectButton.click();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ProductListPage productListPage=new ProductListPage(driver);
        return productListPage;
    }
}