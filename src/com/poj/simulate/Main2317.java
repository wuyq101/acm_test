package com.poj.simulate;

import java.util.Scanner;

/**
 * <pre>
 *     原题：http://poj.org/problem?id=2317
 *     题意：给出三种加密方式，求加密后的字符串
 *     分析：直接模拟
 * </pre>
 * User: wuyq101
 * Date: 13-2-3
 * Time: 下午4:48
 * To change this template use File | Settings | File Templates.
 */
public class Main2317 {
    private static String key, msg;
    private static char[][] map = new char[101][101];
    private static char[][] copy = new char[101][101];
    private static int n;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNextLine()) {
            key = cin.nextLine();
            msg = cin.nextLine().toUpperCase();
            solve();
        }
    }

    private static void solve() {
        init();
        encrypt();
        print();
    }

    private static void print() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                sb.append(map[i][j]);
            }
        }
        System.out.println(sb.toString());
    }

    private static void init() {
        String num = key.substring(0, 2);
        if (num.equals("00")) {
            n = 100;
        } else {
            n = num.startsWith("0") ? Integer.valueOf(num.substring(1)) : Integer.valueOf(num);
        }
        int idx = 0, len = msg.length(), k = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= n; j++) {
                if (idx < len)
                    map[i][j] = msg.charAt(idx++);
                else {
                    map[i][j] = (char) ('A' + (k++) % 26);
                }
            }
        }
    }

    private static void encrypt() {
        key = key.substring(2);
        char pre = key.charAt(0);
        int cnt = 1;
        for (int i = 1; i < key.length(); i++) {
            char ch = key.charAt(i);
            if (ch == pre) {
                cnt++;
            } else {
                if(pre=='S' || pre=='R')
                    cnt %= n;
                for (int k = 0; k < cnt; k++) {
                    encrypt(pre);
                }
                pre = ch;
                cnt = 1;
            }
        }
        if(pre=='S' || pre=='R')
            cnt %= n;
        for (int k = 0; k < cnt; k++) {
            encrypt(pre);
        }
    }

    private static void encrypt(char ch) {
        if (ch == 'S')
            shake();
        if (ch == 'R')
            rattle();
        if (ch == 'L')
            roll();
    }

    //一列一列地换
    private static void shake() {
        for (int j = 1; j <= n; j++) {
            if (j % 2 == 1) {
                //向上
                char ch = map[1][j];
                for (int i = 1; i < n; i++)
                    map[i][j] = map[i + 1][j];
                map[n][j] = ch;
            } else {
                //向下
                char ch = map[n][j];
                for (int i = n; i > 1; i--)
                    map[i][j] = map[i - 1][j];
                map[1][j] = ch;
            }
        }
    }

    //一行一行地换
    private static void rattle() {
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 1) {
                //向右
                char ch = map[i][n];
                for (int j = n; j > 1; j--) {
                    map[i][j] = map[i][j - 1];
                }
                map[i][1] = ch;
            } else {
                //向左
                char ch = map[i][1];
                for (int j = 1; j < n; j++) {
                    map[i][j] = map[i][j + 1];
                }
                map[i][n] = ch;
            }
        }
    }

    //一圈一圈的换
    private static void roll() {
        copy();
        int half = n / 2;
        for (int k = 1; k <= half; k++) {
            if (k % 2 == 1) {
                //顺时针
                //第一行
                for (int j = n + 1 - k; j > k; j--)
                    map[k][j] = copy[k][j - 1];
                map[k][k] = copy[k + 1][k];
                //最后一行
                for (int j = k; j < n + 1 - k; j++)
                    map[n + 1 - k][j] = copy[n + 1 - k][j + 1];
                map[n + 1 - k][n + 1 - k] = copy[n - k][n + 1 - k];
                //左边列
                for (int i = k; i < n + 1 - k; i++)
                    map[i][k] = copy[i + 1][k];
                map[n + 1 - k][k] = copy[n + 1 - k][k + 1];
                //右边列
                for (int i = n + 1 - k; i > k; i--)
                    map[i][n + 1 - k] = copy[i - 1][n + 1 - k];
                map[k][n + 1 - k] = copy[k][n - k];
            } else {
                //逆时针
                //第一行
                for (int j = k; j < n + 1 - k; j++)
                    map[k][j] = copy[k][j + 1];
                map[k][n + 1 - k] = copy[k + 1][n + 1 - k];
                //最后一行
                for (int j = n + 1 - k; j > k; j--)
                    map[n + 1 - k][j] = copy[n + 1 - k][j - 1];
                map[n + 1 - k][k] = copy[n + 1 - k][k - 1];
                //左边列
                for (int i = n + 1 - k; i > k; i--)
                    map[i][k] = copy[i - 1][k];
                map[k][k] = copy[k][k + 1];
                //右边列
                for (int i = k; i < n + 1 - k; i++)
                    map[i][n + 1 - k] = copy[i + 1][n + 1 - k];
                map[n + 1 - k][n + 1 - k] = copy[n + 1 - k][n - k];
            }
        }
    }

    private static void copy() {
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= n; j++) {
                copy[i][j] = map[i][j];
            }
        }
    }
}
