package com.poj;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 2115
 * 
 * <pre>
 * for (variable = A; variable != B; variable += C)
 * 判断语句是否会结束
 * </pre>
 * 
 * @author vanguard001
 * @version 1.0
 */
public class Main2115 {

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        while (true) {
            long A = cin.nextLong();
            long B = cin.nextLong();
            long C = cin.nextLong();
            long k = cin.nextLong();
            if (A == 0 && B == 0 && C == 0 && k == 0)
                break;
            if(A==B){
                System.out.println(0);
                continue;
            }
            long L = 1L << k;
            // 结束条件 A+Cx == B (mod L)
            // Cx == (B-A) (mod L)
            long g = ex_gcd(C, L);
            if ((B - A) % g == 0) {
                // 有解，计算答案
                //System.out.printf("X=%d, Y=%d\n",X,Y);
                //CX+LY=g  --->  CX+LY=B-A
                long s = (B-A)/g;
                long t = L/g;
                X *= s;
                X = (X % t + t) % t;
                System.out.println(X);
                //System.out.printf("X=%d\n",X);
            } else {
                // 无解
                System.out.println("FOREVER");
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

}
