package com.poj.math;
import java.util.Scanner;

/**
 * 2369 置换群
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main2369 {
    private static int n;
    private static int[] p = new int[1001];
    private static int[] cycle = new int[1001];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        n = cin.nextInt();
        for (int i = 1; i <= n; i++)
            p[i] = cin.nextInt();
        cycle();
        order();
    }

    private static long gcd(long a, long b) {
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    private static long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    private static void order() {
        long order = cycle[1];
        for (int i = 2; i <= n; i++) {
            order = lcm(order, cycle[i]);
        }
        System.out.println(order);
    }

    private static void cycle() {
        for (int i = 1; i <= n; i++) {
            int cnt = 1, j = p[i];
            while (j != i) {
                j = p[j];
                cnt++;
            }
            cycle[i] = cnt;
        }
    }
}
