package com.poj;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 2142 aX+bY=d, 求|x|+|y|最小的整数解
 * 
 * @author vanguard001
 * @version 1.0
 */
public class Main2142 {

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        while (true) {
            long a = cin.nextLong();
            long b = cin.nextLong();
            long d = cin.nextLong();
            if (a == 0 && b == 0 && d == 0)
                break;
            solve(a, b, d);
        }
    }

    private static void solve(long a, long b, long d) {
        // aX+bY=g
        long g = ex_gcd(a, b);
        // aX+bY=g ---> aX+bY=d
        long s = d / g;
        long t = b / g; // X的差值
        long k = a / g; // Y的差值
        // 初始解X0，Y0
        X *= s;
        Y *= s;
        // 通解
        // X = X0 + (b/g)*n
        // Y = Y0 - (a/g)*n
        // 求X的最小正整数解
        long X1 = (X % t + t) % t;
        long Y1 = (d - a * X1) / b;
        Y1 = Y1 < 0 ? -Y1 : Y1;
        // 求Y的最小正整数解
        long Y2 = (Y % k + k) % k;
        long X2 = (d - b * Y2) / a;
        X2 = X2 < 0 ? -X2 : X2;
        boolean flag = X1 + Y1 < X2 + Y2 || (X1 + Y1 == X2 + Y2 && (a * X1 + b * Y1 < a * X2 + b * Y2)) ? true : false;
        if (flag)
            System.out.println(X1 + " " + Y1);
        else
            System.out.println(X2 + " " + Y2);
    }

    private static long X = 0;
    private static long Y = 0;

    private static long ex_gcd(long a, long b) {
        if (b == 0) {
            X = 1;
            Y = 0;
            return a;
        }
        long g = ex_gcd(b, a % b);
        long temp = X;
        X = Y;
        Y = temp - a / b * Y;
        return g;
    }
}
