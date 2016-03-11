public class test{
    public static void main (String[] args) throws Exception {
        Excelutils excelutils=new Excelutils();
        String  adata=excelutils.getStringData ("E:/ydh_automation2/lib/dataBase/testdata.xls","loginSheet",2,2);
        System.out.println(adata);
    }
}