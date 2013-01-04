package com.poj;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 2891 求最小的m使得下列方程成立
 * 
 * <pre>
 * m = r1 (mod a1)
 * m = r2 (mod a2)
 *  ... 
 * m = rk (mod ak)
 * </pre>
 * 
 * @author vanguard001
 * @version 1.0
 */
public class Main2891 {
    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        while (cin.hasNextLong()) {
            long k = cin.nextLong();
            long a1 = cin.nextLong();
            long r1 = cin.nextLong();
            // m == r1 (mod a1)
            boolean flag = false;
            for (int i = 1; i < k; i++) {
                long a2 = cin.nextLong();
                long r2 = cin.nextLong();
                // m == r2 (mod a2)
                // 如果无解
                if (flag)
                    continue;
                // 开始解方程
                // m = r1 (mod a1) ---> a1x+r1=m
                // m = r2 (mod a2) ---> a2y+r2=m
                // 两个等式相减 ---> a1x - a2y = r2-r1
                // 先解a1X - a2Y = gcd(a1,a2)
                long g = ex_gcd(a1, a2);
                long c = r2 - r1;
                if (c % g != 0) {
                    // 无解
                    flag = true;
                    continue;
                } else {
                    // 此时： a1X+a2Y=g ------- 等式1
                    // 在此基础上计算出 a1X+a2Y = c = r2-r1的最小X值
                    long s = c / g; // 在等式1的基础上两边乘以s,就得到了目标式子
                    long t = Math.abs(a2 / g); // 通解X的差值为t，用来计算最小正整数解
                    X *= s;
                    // 不使用Y值，所以不计算
                    // Y *= s;
                    // 求出X的最小正整数解: a1X+a2Y=r2-r1
                    X = (X % t + t) % t;
                    // 这时最小的m值为 m = a1X+r1
                    r1 = a1 * X + r1;
                    a1 = lcm(a1, a2);
                    // System.out.printf("m=%d", r1);
                }
            }
            if (flag) {
                System.out.println(-1);
            } else {
                System.out.println(r1);
            }
        }

    }

    private static long X = 0;
    private static long Y = 0;

    // 扩展欧几里得
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

    private static long gcd(long a, long b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    // 求最小公倍数
    private static long lcm(long a, long b) {
        long g = gcd(a, b);
        return a * b / g;
    }
}
