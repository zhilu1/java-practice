package com.practice.management.test;

public class Test {
    public static void main(String[] args) {

        try {

            DataGather gather = new DataGather ();

            gather.loadFile();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

}