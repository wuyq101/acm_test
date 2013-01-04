package com.poj;
import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1020 问边长为S的正方形能否切分成n个小正方形
 * 
 * @author wyq@palmdeal.com
 * @version 1.0
 */
public class Main1020 {
    private static int S;
    private static int N;
    private static int[] pieces = new int[11];
    // 总的蛋糕
    private static int[][] cake;
    // 每一列使用的行数
    private static int[] col;
    private static boolean finish;

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        int t = cin.nextInt();
        while (t-- > 0) {
            S = cin.nextInt();
            N = cin.nextInt();
            Arrays.fill(pieces, 0);
            int d = 0;
            int sum = 0;
            for (int i = 0; i < N; i++) {
                d = cin.nextInt();
                pieces[d]++;
                sum += d * d;
            }
            // 先判断面积是否相等
            if (sum != S * S)
                System.out.println("HUTUTU!");
            else
                solve();
        }
    }

    private static void solve() {
        cake = new int[S][S];
        col = new int[S];
        finish = false;
        dfs(0);
        System.out.println(finish ? "KHOOOOB!" : "HUTUTU!");
    }

    private static void dfs(int n) {
        if (finish)
            return;
        if (n == N) {
            finish = true;
            return;
        }
        // 查找目前使用最多的，但是还没有填满的列，然后试着将该列填满
        int max_col = -1;
        int max_col_i = -1;
        for (int i = 0; i < S; i++) {
            if (col[i] == S)
                continue;
            if (col[i] > max_col) {
                max_col = col[i];
                max_col_i = i;
            }
        }
        // 找出这列中，连续空的行，长度最小的那一行
        int minLen = S + 1;
        int minRow = -1;
        for (int i = 0; i < S; i++) {
            if (cake[i][max_col_i] != 0)
                continue;
            // 开始计算从i开始往下走空的行数
            int count = 0;
            int j = 0;
            for (j = i; j < S; j++) {
                if (cake[i][max_col_i] == 0) {
                    count++;
                } else {
                    // i = j;
                    break;
                }
            }
            if (count < minLen) {
                minLen = count;
                minRow = i;
            }
            // 设置i，从这里开始找下一个空的行
            i = j + 1;
        }

        int len = S - max_col < 10 ? S - max_col : 10;
        len = minLen < len ? minLen : len;
        for (int c = len; c >= 1; c--) {
            if (pieces[c] == 0)
                continue;
            if (max_col_i + c - 1 >= S)
                continue;
            boolean can = canPut(minRow, max_col_i, c);
            if (can) {
                // 开始摆放边长为c的蛋糕
                fill(minRow, max_col_i, c, c);
                pieces[c]--;
                for (int i = max_col_i; i < max_col_i + c; i++) {
                    col[i] += c;
                }
                //System.out.printf("开始摆放%d\n", c);
                //print();
                dfs(n + 1);
                if (finish)
                    return;
                pieces[c]++;
                for (int i = max_col_i; i < max_col_i + c; i++) {
                    col[i] -= c;
                }
                fill(minRow, max_col_i, c, 0);
                //System.out.printf("开始去掉%d\n", c);
                //print();
            }
        }
        // 如果这一列所有的都不能放，该方案失败
        return;
    }

    private static boolean canPut(int row, int col, int c) {
        for (int i = row; i < row + c; i++) {
            for (int j = col; j < col + c; j++) {
                if (cake[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void fill(int row, int col, int c, int v) {
        for (int i = row; i < row + c; i++) {
            for (int j = col; j < col + c; j++) {
                cake[i][j] = v;
            }
        }
    }

//    private static void print() {
//        System.out.printf("------------%d X  %d---------------\n", S, S);
//        for (int i = 0; i < S; i++) {
//            for (int j = 0; j < S; j++) {
//                System.out.print(cake[i][j] + " ");
//            }
//            System.out.print("\n");
//        }
//        System.out.printf("-------------col--------------\n");
//        for (int i = 0; i < S; i++) {
//            System.out.print(col[i] + " ");
//        }
//        System.out.print("\n");
//        System.out.printf("---------------------------\n");
//    }

}
