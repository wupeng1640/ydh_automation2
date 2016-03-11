import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.io.*;

import org.apache.poi.xssf.usermodel.*;

/**
 * @author 深圳依云科技测试组
 *         function  web自动化测试过程中使用到的工具
 *         Created by Administrator on 2016/1/29.
 */
public class Tool {
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


/**
 * @author 铱云测试组
 * @time 2015年12月3日
 * 实现从200测试环境数据库获取到随机手机号码的手机短信验证码
 */
class DBread {
    public static final String url = "jdbc:mysql://192.168.1.200/platform";
    public static final String name = "com.mysql.jdbc.Driver";
    public static final String user = "root";
    public static final String password = "ydh20130815";
    String tel = null;
    public Connection conn = null;
    public PreparedStatement pst = null;

    public DBread(String tel) {
        this.tel = tel;
        String sql = "SELECT t.factiveCode FROM platform.t_active_code t "
                + "WHERE fmobile = "
                + "'" + tel + "'"              //在sql中引入变量查询
                + " and  t.factiveStatus=0 ";
        ;
        try {
            Class.forName(name);// 指定连接类型
            conn = DriverManager.getConnection(url, user, password);// 获取连接
            pst = conn.prepareStatement(sql);// 准备执行语句
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getActiveCode(String tel) {
        String code = null;
        DBread db1 = null;
        ResultSet ret = null;
        db1 = new DBread(tel);
        //System.out.println(this.sql);
        try {
            ret = db1.pst.executeQuery();// 执行语句，得到结果集
            while (ret.next()) {
                code = ret.getString(1); // 获取第一列数据
            }// 显示数据
            ret.close();
            db1.close();// 关闭连接
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return code;

    }

    public void close() {
        try {
            this.conn.close();
            this.pst.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


class Excelutils {
    private XSSFSheet ExcelWSheet;
    private XSSFWorkbook ExcelWBook;

    //Constructor to connect to the Excel with sheetname and Path
    public String  getStringData(String Path, String SheetName,int lineNumb,int rowNumb) throws Exception {
        try {
            // Open the Excel file
            FileInputStream ExcelFile = new FileInputStream(Path);
            // Access the required test data sheet

            ExcelWSheet = ExcelWBook.getSheet(SheetName);
            //HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(Path));
            //HSSFSheet ExcelWSheet = workbook.getSheetAt(0);




            XSSFCell cell = ExcelWSheet.getRow(rowNumb).getCell(lineNumb);
            String data=cell.getStringCellValue();
            return data;
        } catch (Exception e) {
            throw (e);
        }
    }


    //This method to get the data and get the value as strings.
    public String getCellDataasstring(int RowNum, int ColNum) throws Exception {
        try {
            String CellData = ExcelWSheet.getRow(RowNum).getCell(ColNum).getStringCellValue();
            System.out.println("The value of CellData " + CellData);
            return CellData;
        } catch (Exception e) {
            return "Errors in Getting Cell Data";
        }
    }


    //This method to get the data and get the value as number.
    public double getCellDataasnumber(int RowNum, int ColNum) throws Exception {
        try {
            double CellData = ExcelWSheet.getRow(RowNum).getCell(ColNum).getNumericCellValue();
            System.out.println("The value of CellData " + CellData);
            return CellData;
        } catch (Exception e) {
            return 000.00;
        }
    }
}
