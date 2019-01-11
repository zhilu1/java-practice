package com.practice.management.test;

import javax.validation.constraints.Null;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

public class DataGather {

    private static final String path = "C:\\Users\\admin.PC201605091048\\Desktop\\AttendanceRecord.txt";

    public static final String openFileStyle = "r";

    public static final String fieldLimitChar = ",";

    public static final int fieldAllCount = 4;

    private int count;

    private String id;

    private String riqi;

    private String sbtime;

    private String xbtime;

    /*

     * 功能：解析文本文件

     */

    public void loadFile() {

        try {

            RandomAccessFile raf = new RandomAccessFile(path, openFileStyle);

            String line_record = raf.readLine();

            while (line_record != null) {

                // 解析每一条记录

                parseRecord(line_record);

                line_record = raf.readLine();

            }

            System.out.println("共有合法的记录" + count + "条");

        } catch (Exception e) {

            e.printStackTrace();

        }

    }



    /*

     * 功能：具体解析每一条记录，这里可以增加很多对记录的解析判断条件，如是否为字母、

     * 数字、email等。

     */

    private void parseRecord(String line_record) throws Exception {

        //拆分记录

        String[] fields = line_record.split(fieldLimitChar);

        if (fields.length == fieldAllCount) {

            riqi = tranStr(fields[0]);

            id = tranStr(fields[1]);

            sbtime = tranStr(fields[2]);

            xbtime = tranStr(fields[3]);


            System.out.println(riqi + " " + id + " " + sbtime + " " + xbtime);

            InsertDB db = new InsertDB();

            db.insertDB(riqi ,id, sbtime, xbtime);

            count++;

        }else if(fields.length == 2){
            riqi = tranStr(fields[0]);

            id = tranStr(fields[1]);


            System.out.println(riqi + " " + id + " " + sbtime + " " + xbtime);

            InsertDB db = new InsertDB();

            db.insertDB(riqi ,id, sbtime, xbtime);
            count++;
        }

    }



    private String tranStr(String oldstr) {

        String newstr = "";

        try {

            newstr = new String(oldstr.getBytes("ISO-8859-1"), "GBK");

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        }

        return newstr;

    }

}