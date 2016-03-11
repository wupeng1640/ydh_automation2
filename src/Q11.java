/**
 * Created by Administrator on 2016/3/11.
 */

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFCell;

import java.io.FileInputStream;
import java.text.DecimalFormat;

public class Q11 {

    public static String fileToBeRead = "E:/ydh_automation2/lib/dataBase/testdata.xls";

    public static void main(String argv[]) {

        try {
// 创建对Excel工作簿文件的引用
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(fileToBeRead));
// 创建对工作表的引用。
// 也可用getSheetAt(int index)按索引引用，
// 在Excel文档中，第一张工作表的缺省索引是0，
// 其语句为：HSSFSheet sheet = workbook.getSheetAt(0);
// 读取左上端单元
            System.out.println("sheet数目： " + workbook.getNumberOfSheets());
            for (int i = 0; i < workbook.getNumberOfSheets(); i++) {//循环sheet
                System.out.println("==========开始第 " + i + " 个sheet============");
                HSSFSheet childSheet = workbook.getSheetAt(i);
                for (int r = 0; r < childSheet.getPhysicalNumberOfRows(); r++) {//循环该 子sheet row
                    System.out.println("childSheet " + (r + 1) + "行数:: " + childSheet.getPhysicalNumberOfRows());
                    System.out.println("childSheet 单元格的数目:: " + childSheet.getRow(r).getPhysicalNumberOfCells());
                    for (short c = 0; c < childSheet.getRow(r).getPhysicalNumberOfCells(); c++) {//循环该子sheet行对应的单元格项
                        HSSFCell cell = childSheet.getRow(r).getCell(c);
                        System.out.println("cell:: " + cell);
                        String value = null;
                        if (cell == null)
                            continue;
                     //   System.out.println("cell.getCellType():: " + cell.getCellType());
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
                            default:
                        }
                        System.out.println("value ---:: " + value);
                    }
                }
            }

//HSSFRow row = sheet.getRow(0);//行
//HSSFCell cell = row.getCell((short) 0);//单元格
// 输出单元内容，cell.getStringCellValue() 就是取所在单元的值
        } catch (
                Exception e
                )

        {
            System.out.println("已运行xlRead() : " + e);
        }
    }
}