package com.practice.management.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class timeCompare {
    public static void main(String[] args){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = "1997-03-27 9:00";
        String str = "1997-03-27 9:00";
        Date cjtime = null;
        Date sbtime =null;
        try {
            cjtime = sdf.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {
            sbtime = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        if (cjtime.before(sbtime)) {
            System.out.println("迟到");
        } else{
            System.out.println("正常");
        }
    }
}
