package com.poj;
import java.io.BufferedInputStream;
import java.math.BigInteger;
import java.util.Scanner;

/**
 * 1047
 * 
 * @author wyq
 * @version 1.0
 */
public class Main1047 {

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        while (cin.hasNext()) {
            String num = cin.next();
            if (isCyclic(num)) {
                System.out.printf("%s is cyclic\n", num);
            } else {
                System.out.printf("%s is not cyclic\n", num);
            }
        }
    }

    private static boolean isCyclic(String num) {
        int n = num.length();
        BigInteger a = new BigInteger(num);
        for (int i = 1; i <= n; i++) {
            BigInteger b = a.multiply(BigInteger.valueOf(i));
            if (!isValid(num, b.toString())) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValid(String a, String b) {
        // 前面补零
        if (b.length() < a.length()) {
            int len = a.length() - b.length();
            for (int i = 0; i < len; i++) {
                b = "0" + b;
            }
        }
        int n = a.length();
        // 比较a和b
        for (int i = 0; i < n; i++) {
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (a.charAt((i + j) % n) != b.charAt(j)) {
                    flag = false;
                }
            }
            if (flag) {
                return true;
            }
        }
        return false;
    }

}
