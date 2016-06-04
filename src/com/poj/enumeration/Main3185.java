package com.poj.enumeration;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * http://poj.org/problem?id=3185
 * 直接枚举，翻转方法一共是[0, 0xFFFFF]种。
 * 翻转之后和原来值比较，相等就表示这个翻转有效。统计1的个数。
 * Created by wuyq on 16/6/1.
 */
public class Main3185 {
    private static int n;
    private static int LEN = 20;

    public static void main(String[] args) throws Exception {
        input();
        solve();
    }

    private static void solve() {
        if (n == 0) {
            System.out.println(0);
            return;
        }
        int MAX = 0xFFFFF;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i <= MAX; i++) {
            if ((((i << 1) ^ i ^ (i >> 1)) & 0xFFFFF) == n) {
                int count = bitCount(i);
                if (count < min) {
                    min = count;
                }
            }
        }
        System.out.println(min);
    }

    private static int bitCount(int x) {
        int cnt = 0;
        while (x > 0) {
            x = x & (x - 1);
            cnt++;
        }
        return cnt;
    }

    private static void input() throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        String[] s = cin.readLine().split(" ");
        for (int i = 0; i < LEN; i++) {
            int b = Integer.parseInt(s[i]);
            n = (n << 1) | b;
        }
    }
}
