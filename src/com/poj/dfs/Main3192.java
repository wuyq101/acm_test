package com.poj.dfs;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * http://poj.org/problem?id=3192
 * 枚举所有的排列，然后合并字符串，计算最优值
 * Created by wuyq on 16/5/31.
 */
public class Main3192 {
    private static int N;
    private static String[] w;
    private static int[] p;
    private static int min;

    public static void main(String[] args) throws Exception {
        input();
        solve();
    }

    private static void solve() {
        min = Integer.MAX_VALUE;
        dfs(0);
        System.out.println(min);
    }

    private static void dfs(int n) {
        if (n == N) {
            int len = assemble();
            if (len < min) {
                min = len;
            }
            return;
        }
        for (int i = n; i < N; i++) {
            swap(n, i);
            dfs(n + 1);
            swap(n, i);
        }
    }

    private static int assemble() {
        String a = w[p[0]];
        for (int i = 1; i < N; i++) {
            if (a.length() > min) {
                return min + 1;
            }
            a = merge(a, w[p[i]]);
        }
        return a.length();
    }

    private static String merge(String a, String b) {
        for (int len = b.length(); len > 0; len--) {
            int i = 0;
            for (; i < len; ) {
                int idx = a.length() - len + i;
                if (idx >= 0 && a.charAt(idx) == b.charAt(i)) {
                    i++;
                } else {
                    break;
                }
            }
            if (i == len) {
                return a + b.substring(len);
            }
        }
        return a + b;
    }

    private static void swap(int i, int j) {
        int temp = p[i];
        p[i] = p[j];
        p[j] = temp;
    }

    private static void input() throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(cin.readLine());
        w = new String[N];
        p = new int[N];
        for (int i = 0; i < N; i++) {
            w[i] = cin.readLine();
            p[i] = i;
        }
    }
}
