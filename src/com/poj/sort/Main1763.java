package com.poj.sort;

import java.util.*;

import static java.lang.Math.abs;

/**
 * <pre>
 *     原题：http://poj.org/problem?id=1763
 *     题意：在平面上有一条路径，求一条长度最短的捷径。
 *     分析：先根据x坐标排序，x相等的话，根据y排序，捷径仅在某个坐标相等的时候才出现，排序使用快排，答案只在排序后的相邻两点中才出现。
 * </pre>
 * User: wuyq101
 * Date: 13-2-22
 * Time: 下午10:30
 */
public class Main1763 {
    private static int[][] breakpoints = new int[2][250001];
    private static int[] sorted = new int[250001];
    private static int n;
    private static int best_len = Integer.MAX_VALUE, best_b, best_e;
    private static char best_d;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        n = cin.nextInt();
        String str = cin.next();
        int x = 0, y = 0;
        char ch;
        for (int i = 1; i <= n; i++) {
            sorted[i] = i;
            ch = str.charAt(i - 1);
            switch (ch) {
                case 'N':
                    y++;
                    break;
                case 'S':
                    y--;
                    break;
                case 'E':
                    x++;
                    break;
                case 'W':
                    x--;
            }
            breakpoints[0][i] = x;
            breakpoints[1][i] = y;
        }
        solve();
    }

    private static void solve() {
        quicksort(0, n, 0);
        calc(0);
        quicksort(0, n, 1);
        calc(1);
        best_d = dir();
        System.out.printf("%d %d %d %c", best_len, best_b, best_e, best_d);
    }

    private static char dir() {
        if (breakpoints[0][best_b] == breakpoints[0][best_e]) {
            return breakpoints[1][best_b] < breakpoints[1][best_e] ? 'N' : 'S';
        }
        return breakpoints[0][best_b] < breakpoints[0][best_e] ? 'E' : 'W';
    }

    private static void calc(int flag) {
        int len, b, e;
        for (int i = 1; i <= n; i++) {
            if (breakpoints[flag][sorted[i - 1]] != breakpoints[flag][sorted[i]])
                continue;
            len = abs(breakpoints[1 - flag][sorted[i]] - breakpoints[1 - flag][sorted[i - 1]]);
            if (sorted[i - 1] < sorted[i]) {
                b = sorted[i - 1];
                e = sorted[i];
            } else {
                e = sorted[i - 1];
                b = sorted[i];
            }
            if (b + 1 < e && (len < best_len || (len == best_len && b < best_b) || (len == best_len && b == best_b && e > best_e))) {
                best_len = len;
                best_b = b;
                best_e = e;
            }
        }
    }

    private static void quicksort(int left, int right, int flag) {
        if (right <= left) return;
        int i = left, j = right, v = sorted[(left + right) / 2];
        while (i < j) {
            while (isSmaller(sorted[i], v, flag))
                i++;
            while (isSmaller(v, sorted[j], flag))
                j--;
            if (i <= j)
                swap(i++, j--);
        }
        if (left < j) quicksort(left, j, flag);
        if (i < right) quicksort(i, right, flag);
    }

    private static void swap(int i, int j) {
        int temp = sorted[i];
        sorted[i] = sorted[j];
        sorted[j] = temp;
    }

    private static boolean isSmaller(int i, int j, int flag) {
        if (breakpoints[flag][i] < breakpoints[flag][j])
            return true;
        if (breakpoints[flag][i] > breakpoints[flag][j])
            return false;
        return breakpoints[1 - flag][i] < breakpoints[1 - flag][j];
    }
}