package com.poj.math;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 2635 大整数求模运算
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main2635 {
    // 1000000之前的质数一共78498个
    private static int[] p = new int[78498];

    private static char[] K;
    private static long[] k;
    private static int L;

    public static void main(String[] args) {
        init_prime();
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        while (true) {
            String k = cin.next();
            L = cin.nextInt();
            if ("0".equals(k) && L == 0)
                break;
            K = k.toCharArray();
            // 转化为10^6进制
            change();
            check();
        }
    }

    private static void change() {
        int len = 1;
        if (K.length % 6 == 0)
            len = K.length / 6;
        else
            len = K.length / 6 + 1;
        k = new long[len];
        long n = 0;
        int idx = 0;
        for (int i = 0; i < K.length % 6; i++) {
            n *= 10;
            n += K[i] - '0';
        }
        k[idx++] = n;
        for (int i = K.length % 6; i < K.length; i += 6) {
            n = 0;
            for (int j = i; j < i + 6; j++) {
                n *= 10;
                n += K[j] - '0';
            }
            k[idx++] = n;
        }
    }

    private static void check() {
        for (int i = 0; i < p.length && p[i] < L; i++) {
            if (mod(p[i])) {
                System.out.println("BAD " + p[i]);
                return;
            }
        }
        System.out.println("GOOD");
    }

    private static boolean mod(int m) {
        long n = 0;
        for (int i = 0; i < k.length; i++) {
            n = n * 1000000 + k[i];
            n %= m;
        }
        return n == 0;
    }

    // 打表求质数
    private static void init_prime() {
        int k = 0;
        for (int i = 2; i <= 1000000; i++) {
            if (isPrime(i)) {
                p[k++] = i;
            }
        }
    }

    private static boolean isPrime(int n) {
        if (n == 2 || n == 3)
            return true;
        if (n % 2 == 0 || n % 3 == 0)
            return false;
        // 因为每个质数mod6 == 1或者5
        // 按照步长2或者4交替进行
        // (i+1)^2 = i^2+2i+1
        for (int i = 5, j = 4, k = i * i; k <= n; j = 6 - j, i += j, k = i * i) {
            if (n % i == 0)
                return false;
        }
        return true;
    }

}
