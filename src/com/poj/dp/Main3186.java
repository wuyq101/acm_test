package com.poj.dp;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * http://poj.org/problem?id=3186
 * Created by wuyq on 16/5/31.
 */
public class Main3186 {
    static int N;
    static int[] v;
    static int[][] r; //r[i][j]代表从两端开始算，中间[i,j]部分占的最大和

    public static void main(String[] args) throws Exception {
        input();
        solve();
    }

    private static void solve() {
        r = new int[N][N];
        for (int i = 0; i < N; i++) {
            r[i][i] = v[i] * N;
        }
        for (int len = 2; len <= N; len++) {
            int m = N - len + 1;
            for (int i = 0; i + len - 1 < N; i++) {
                int j = i + len - 1;
                int a = v[i] * m + r[i + 1][j];
                int b = v[j] * m + r[i][j - 1];
                r[i][j] = Math.max(a, b);
            }
        }
        System.out.println(r[0][N - 1]);
    }

    private static void input() throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(cin.readLine());
        v = new int[N];
        for (int i = 0; i < N; i++) {
            v[i] = Integer.parseInt(cin.readLine());
        }
    }
}
