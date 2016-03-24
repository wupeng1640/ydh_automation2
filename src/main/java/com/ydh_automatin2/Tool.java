package com.ydh_automatin2;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.text.DateFormat;
import java.text.DecimalFormat;
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
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * @author 深圳依云科技测试组
 *         function  web自动化测试过程中使用到的工具
 *         Created by Administrator on 2016/1/29.
 */
public class Tool {


    String value = null;
    //用来获取execl的数据，参数说明，Path：文件路径，SheetName：工作表名称 ，rowNumb：数据所在行数，cellNumb：数据所在列数
    //数据驱动测试使用的方法
    public String getExeclData(String Path, String SheetName, int rowNumb, int cellNumb) throws Exception {
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(Path));
            HSSFSheet childSheet = workbook.getSheet(SheetName);
            HSSFRow row = childSheet.getRow(rowNumb);
            HSSFCell cell = row.getCell(cellNumb);
            //HSSFCell cell = childSheet.getRow(rowNumb).getCell(lineNumb);
            switch (cell.getCellType()) {
                case HSSFCell.CELL_TYPE_NUMERIC:
                    DecimalFormat df = new DecimalFormat("0");
                    value = df.format(cell.getNumericCellValue());
                    break;
                case HSSFCell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue();
                    break;
                case HSSFCell.CELL_TYPE_BLANK:
                    break;
                case HSSFCell.CELL_TYPE_ERROR:
                    break;
                default:
            }
            return value;
        } catch (Exception e) {
            System.out.println("所去数据的在Excel中的位置坐标设置错位，请确认！！！！");
            throw (e);
        }
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


/**
 * 文件操作代码
 *
 * @author cn.outofmemory
 * @date 2013-1-7
 */
 class FileUtils {
    /**
     * 将文本文件中的内容读入到buffer中
     * @param buffer buffer
     * @param filePath 文件路径
     * @throws IOException 异常
     * @author cn.outofmemory
     * @date 2013-1-7
     */
    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }
    /**
     * 读取文本文件内容
     * @param filePath 文件所在路径
     * @return 文本内容
     * @throws IOException 异常
     * @author cn.outofmemory
     * @date 2013-1-7
     */
    public static String readFile(String filePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        FileUtils.readToBuffer(sb, filePath);
        return sb.toString();
    }

}

