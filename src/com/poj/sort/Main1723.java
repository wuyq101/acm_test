package com.poj.sort;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <pre>
 * 原题：http://poj.org/problem?id=1723
 * 题意：有N个点，坐标分别为(x1,y1),(x2,y2),...,(xn,yn)，经过移动让这些点水平排成一排(x,y),(x+1,y),...(x+N-1,y)。
 *           求移动的最小步数。每次可上下左右移动1个单位。
 * </pre>
 * User: wuyq101
 * Date: 13-2-17
 * Time: 下午7:23
 */
public class Main1723 {
    private static int[] x = new int[10000];
    private static int[] y = new int[10000];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int N = cin.nextInt();
        for (int i = 0; i < N; i++) {
            x[i] = cin.nextInt();
            y[i] = cin.nextInt();
        }
        Arrays.sort(y, 0, N);
        Arrays.sort(x, 0, N);
        for (int i = 0; i < N; i++)
            x[i] -= i;
        Arrays.sort(x, 0, N);
        long sum = 0;
        for (int i = 0; i < N / 2; i++)
            sum += y[N - 1 - i] - y[i] + x[N - 1 - i] - x[i];
        System.out.printf("%d\n", sum);
    }
}
