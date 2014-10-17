package com.poj.dp;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by wuyq on 14-10-16.
 */
public class Main1185 {
    private static int MAX = 100;
    private static int N, M;
    private static int[] map = new int[MAX];
    private static int[] state = new int[MAX];
    private static int[] cost = new int[MAX];
    private static int LEN;
    //dp[i][a][b] 前i行，i行处于a状态，i-1行处于b状态时候，最多能放的个数
    //dp[i][a][b] = max{dp[i-1][b][k]} + cost(a), for all valid k;
    private static int[][][] dp;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        N = cin.nextInt();
        M = cin.nextInt();
        for (int i = 0; i < N; i++) {
            String s = cin.next();
            for (int j = 0; j < s.length(); j++) {
                if (s.charAt(j) == 'H') {
                    map[i] |= 1 << (M - 1 - j);
                }
            }
        }
        findAllValidState();
        solve();
    }

    private static void solve() {
        dp = new int[N][LEN][LEN];
        for (int i = 0; i < N; i++) {
            for (int a = 0; a < LEN; a++) {
                if ((state[a] & map[i]) != 0) {
                    continue;
                }
                if (i - 1 >= 0) {
                    for (int b = 0; b < LEN; b++) {
                        if ((state[b] & map[i - 1]) != 0 || (state[a] & state[b]) != 0) {
                            continue;
                        }
                        dp[i][a][b] = cost[a] + cost[b];
                        int max = 0;
                        if (i - 2 >= 0) {
                            for (int c = 0; c < LEN; c++) {
                                if ((state[c] & map[i - 2]) != 0) continue;
                                if ((state[a] & state[c]) != 0) continue;
                                if ((state[b] & state[c]) != 0) continue;
                                if (max < dp[i - 1][b][c]) {
                                    max = dp[i - 1][b][c];
                                }
                            }
                            if (max > 0) {
                                dp[i][a][b] += max - cost[b];
                            }
                        }
                    }
                } else {
                    Arrays.fill(dp[0][a], cost[a]);
                }
            }
        }
        int best = 0;
        for (int a = 0; a < LEN; a++) {
            for (int b = 0; b < LEN; b++) {
                if (best < dp[N - 1][a][b]) {
                    best = dp[N - 1][a][b];
                }
            }
        }
        System.out.println(best);
    }

    private static void findAllValidState() {
        int max = 1 << M;
        int k = 0;
        for (int i = 0; i < max; i++) {
            if ((i & (i << 1)) == 0 && (i & (i << 2)) == 0) {
                state[k++] = i;
                int t = 0, s = i;
                while (s != 0) {
                    s = s & (s - 1);
                    t++;
                }
                cost[k - 1] = t;
            }
        }
        LEN = k;
    }
}
