package com.poj;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 3753 根据关键字进行字符串拷贝
 * 
 * @author wyq@palmdeal.com
 * @version 1.0
 */
public class Main3753 {

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        while (cin.hasNext()) {
            String src = cin.next();
            String key = cin.next();
            while (!"END".equals(key)) {
                copy(src, key);
                key = cin.next();
            }
        }
    }

    private static void copy(String src, String key) {
        if ("NULL".equals(key)) {
            System.out.printf("%d %s\n", 0, "NULL");
            return;
        }
        int index = src.indexOf(key);
        if (index == 0) {
            System.out.printf("%d %s\n", 0, "NULL");
        } else if (index > 0) {
            String des = src.substring(0, index);
            System.out.printf("%d %s\n", des.length(), des);
        } else {
            System.out.printf("%d %s\n", src.length(), src);
        }
    }
}
