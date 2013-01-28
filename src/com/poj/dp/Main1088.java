package com.poj.dp;

import java.util.Scanner;

/**
 * 原题：http://poj.org/problem?id=1088
 * 题意：滑雪，求最长距离
 * 分析：动态规划，打表记录中间结果
 * User: wuyq101
 * Date: 13-1-25
 * Time: 下午1:50
 */
public class Main1088 {
    private static int R, C;
    private static int[][] map = new int[105][105];
    private static int[][] cnt = new int[105][105];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        R = cin.nextInt();
        C = cin.nextInt();
        init();
        for (int i = 1; i <= R; i++) {
            for (int j = 1; j <= C; j++) {
                map[i][j] = cin.nextInt();
            }
        }
        solve();
    }

    private static void init() {
        for (int i = 0; i <= R + 1; i++) {
            for (int j = 0; j <= C + 1; j++) {
                map[i][j] = 20000;
                cnt[i][j] = -1;
            }
        }
    }

    private static void solve() {
        int max = -1, cur;
        for (int i = 1; i <= R; i++) {
            for (int j = 1; j <= C; j++) {
                cur = dp(i, j);
                if (cur > max) {
                    max = cur;
                }
            }
        }
        System.out.printf("%d\n", max);
    }

    private static int[] dir_x = {0, 0, -1, 1};
    private static int[] dir_y = {-1, 1, 0, 0};

    private static int dp(int i, int j) {
        if (cnt[i][j] > 0)
            return cnt[i][j];
        cnt[i][j] = 1;
        int a, b;
        for (int n = 0; n < 4; n++) {
            a = i + dir_x[n];
            b = j + dir_y[n];
            if (map[i][j] > map[a][b] && cnt[i][j] < 1 + dp(a, b)) {
                cnt[i][j] = 1 + cnt[a][b];
            }
        }
        return cnt[i][j];
    }
}
