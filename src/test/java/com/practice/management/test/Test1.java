package com.practice.management.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test1 {

    List<Bean> beanList = new ArrayList<Bean>();

    Map<String, Result> map = new HashMap<String, Result>();

    public void setBean(Bean bean) {
        this.beanList.add(bean);
    }

    public void compare() {
        for (Bean bean : beanList) {

            if (map.containsKey(bean.id + bean.letter)) {
                Result result = map.get(bean.id + bean.letter);
                result.compare(bean);
            } else {
                Result result = new Result(bean);
                map.put(bean.id + bean.letter, result);
            }
        }
    }

    public void printResult() {
        for (Map.Entry<String, Result> entry : map.entrySet()) {
            entry.getValue().printResult();
        }
    }

    public static void main(String[] args) {

        Bean a = new Bean("1", "a", "2013-04-01");
        Bean b = new Bean("1", "a", "2013-04-08");
        Bean c = new Bean("1", "a", "2013-04-28");
        Bean d = new Bean("2", "a", "2013-04-08");
        Bean e = new Bean("2", "a", "2013-11-11");
        Bean f = new Bean("1", "a", "2013-11-11");

        Test1 test = new Test1();
        test.setBean(a);
        test.setBean(b);
        test.setBean(c);
        test.setBean(d);
        test.setBean(e);
        test.setBean(f);

        test.compare();
        test.printResult();
    }
}

class Bean {
    String id;
    String letter;
    String date;

    public Bean(String a, String b, String c) {
        this.id = a;
        this.letter = b;
        this.date = c;
    }
}

class Result {
    String id;
    String letter;
    String min;
    String max;
    int num;

    public Result(Bean bean) {
        this.id = bean.id;
        this.letter = bean.letter;
        this.min = bean.date;
        this.max = bean.date;
        this.num = 1;
    }

    public void compare(Bean bean) {
        if (this.min.compareTo(bean.date) > 0) {
            this.min = bean.date;
        } else {
            this.max = bean.date;
        }
        this.num++;
    }

    public void printResult() {
        System.out.println(this.id + this.letter + "   " + this.min + "   "
                + this.max + "   " + this.num);
    }
}
