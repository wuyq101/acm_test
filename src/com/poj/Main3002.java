package com.poj;
import java.util.Scanner;

/**
 * 3002 求最小公倍数
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main3002 {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int n = cin.nextInt();
        while (n-- > 0) {
            int w = cin.nextInt();
            long a = 1;
            for (int i = 0; i < w; i++) {
                int p = cin.nextInt();
                a = lcm(a, p);
            }
            if (a <= 1000000000)
                System.out.println(a);
            else
                System.out.println("More than a billion.");
        }
    }

    private static long gcd(long a, long b) {
        if (a % b == 0)
            return b;
        return gcd(b, a % b);
    }

    private static long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }
}
