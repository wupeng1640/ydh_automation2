package com.ydh_automatin2;
public class test{
    public static void main (String[] args) throws Exception {
        Tool excelutils=new Tool();
        String  adata=excelutils.getExeclData ("E:/ydh_automation2/lib/dataBase/testdata.xls","loginSheet",1,1);
        System.out.println(adata);
    }
}