package com.poj.dfs;

import java.util.Arrays;
import java.util.Scanner;

/**
 * http://poj.org/problem?id=3187
 * Created by wuyq on 16/5/31.
 */
public class Main3187 {
    static int N, SUM;
    static int[][] p = new int[10][10];
    static int[] d;
    static int[] best;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        N = cin.nextInt();
        SUM = cin.nextInt();
        init();
        solve();
        output(best);
    }

    private static void output(int[] t) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            sb.append(t[i]).append(' ');
        }
        System.out.println(sb.toString());
    }

    private static void solve() {
        d = new int[N];
        for (int i = 0; i < N; i++) {
            d[i] = i + 1;
        }
        best = new int[N];
        Arrays.fill(best, N + 1);
        dfs(0);
    }

    private static void dfs(int idx) {
        if (idx == N) {
            int sum = 0;
            for (int i = 0; i < N; i++) {
                sum += p[N - 1][i] * d[i];
            }
            if (sum == SUM) {
                boolean valid = true;
                for (int i = 0; i < N; i++) {
                    if (d[i] > best[i]) {
                        valid = false;
                        break;
                    }
                    if (d[i] < best[i]) {
                        valid = true;
                        break;
                    }
                }
                if (valid) {
                    System.arraycopy(d, 0, best, 0, N);
                }
            }
            return;
        }
        for (int i = idx; i < N; i++) {
            swap(d, idx, i);
            dfs(idx + 1);
            swap(d, idx, i);
        }
    }

    private static void swap(int[] t, int i, int j) {
        int temp = t[i];
        t[i] = t[j];
        t[j] = temp;
    }

    private static void init() {
        for (int i = 0; i < N; i++) {
            p[i][0] = 1;
            p[i][i] = 1;
            for (int j = 1; j < i; j++) {
                p[i][j] = p[i - 1][j - 1] + p[i - 1][j];
            }
        }
    }
}
