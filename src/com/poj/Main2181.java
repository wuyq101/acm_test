package com.poj;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 2181
 */
public class Main2181 {
    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        int P = cin.nextInt();
        int a = cin.nextInt();
        // 上一跳最高值
        int b = a;
        // 上上一跳的最高值
        int c = 0;
        // 中间值
        int k = 0;
        for (int i = 1; i < P; i++) {
            a = cin.nextInt();
            k = c + a > b ? c + a : b;
            c = b - a > c ? b - a : c;
            b = k;
        }
        System.out.println(b > c ? b : c);
    }
}
