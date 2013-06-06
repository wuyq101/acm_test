package com.poj.math;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 1061 青蛙的约会 求方程 x+s*m mod l == y+s*n mod l关于s的最小正整数解。 根据模方程的性质，可得出： s*(m-n)+t*l=y-x， t为引入的一个整数 令a=m-n, b=y-x: 所以:
 * s*a+t*l=b ........... 等式0
 * 
 * 扩展欧几里得算法求d=gcd(a,l), s*a+t*l=gcd(a,l), 得到一个解s0,t0 s0*a+t0*l=d ........... 等式1
 * 
 * 将等式0 s*a+t*l=b 两边都除以d s*(a/d)+t*(l/d)=b/d
 * 
 * 因为d是a,l的最大公约数，所以a/d,l/d都是整数，b/d也必须是整数，否则方程无解
 * 
 * 将等式1两边都乘以b/d s0*a*(b/d)+t0*l*(b/d) = d*(b/d)
 * 
 * s0*(a*b/d) + t0*(l*b/d) = b (新的式子) s*a + t*l = b (原来等式0)
 * 
 * 得到 s = s0*b/d, t=t0*b/d
 * 
 * 另外，考虑有两组解 s0*a+t0*l=d=s1*a+t1*l a*(s0-s1)= l*(t1-t0) a*l/d = a*l/d 所以相邻两个解s0,s1的差是l/d, 相邻两个解t0,t1的差是a/d 所以通解 s =
 * s0+k*(l/d), k为整数
 * 
 * @author vanguard001
 * @version 1.0
 */
public class Main1061 {
    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        long x = cin.nextLong();
        long y = cin.nextLong();
        long m = cin.nextLong();
        long n = cin.nextLong();
        long l = cin.nextLong();
        if (m == n) {
            System.out.println("Impossible");
            return;
        }
        if(linear_equation(n-m, l, x-y)){
            System.out.println(X);
        }else{
            System.out.println("Impossible");
        }
    }

    private static long X = 0;
    private static long Y = 0;

    // 解线性方程aX+bY=c
    private static boolean linear_equation(long a, long b, long c) {
        long g = ex_gcd(a, b);
        if (c % g != 0) {
            return false;
        }
        long k = c / g, t = Math.abs(b / g);
        X *= k;
        Y *= k;
        X = (X % t + t) % t;
        return true;
    }

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