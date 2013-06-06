package com.poj.dp;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 1037 动态规划DP
 * 
 * @author vanguard001
 * @version 1.0
 */
public class Main1037 {
    // dp[i][j][k],代表长度为i，j开头，k状态的波浪排列的个数
    // k=0 升序
    // k==1 降序
    private static long[][][] dp = new long[21][21][2];
    private static int[] used = new int[21];
    
    private static void dp() {
        dp[1][1][0] = 1;
        dp[1][1][1] = 1;
        for (int i = 2; i <= 20; i++) {
            for (int j = 1; j <= i; j++) {
                for (int k = 0; k <= 1; k++) {
                    if (k == 0) {
                        // 升序，将所有dp[i-1][j2][1]加起来，j<=j2<i-1
                        for (int j2 = j; j2 <= i - 1; j2++) {
                            dp[i][j][0] += dp[i - 1][j2][1];
                        }
                        // System.out.printf("dp[%d][%d][%d]=%d\n", i, j, k, dp[i][j][k]);
                    } else {
                        // 降序，将所有dp[i-1][j2][0]加起来，1<=j2<j
                        for (int j2 = 1; j2 < j; j2++) {
                            dp[i][j][1] += dp[i - 1][j2][0];
                        }
                        // System.out.printf("dp[%d][%d][%d]=%d\n", i, j, k, dp[i][j][k]);
                    }
                }
            }
        }
    }

    private static void solve(int n, long c, int last, int k) {
        // n长度的所有排列个数
        // dp[n][1] + dp[n][2] + .. + dp[n][n-1] + dp[n][n].
        // 排序的时候，1开头的排序2开头的前面
        // System.out.printf("n=%d, c=%d, last=%d, k=%d \n",n,c,last,k);
        for (int i = 1; i <= n; i++) {
            // 表示i开头的
            if (k == 3) {
                if (dp[n][i][1] < c) {
                    c -= dp[n][i][1];
                } else {
                    // System.out.printf("%d, %d, %d\n", i, used[i], c);
                    int a = used[i];
                    System.out.printf("%d ", used[i]);
                    for (int s = i; s < n; s++) {
                        used[s] = used[s + 1];
                    }
                    solve(n - 1, c, a, 0);
                    break;
                }
                if (dp[n][i][0] < c) {
                    c -= dp[n][i][0];
                } else {
                    // System.out.printf("%d, %d, %d\n", i, used[i], c);
                    int a = used[i];
                    System.out.printf("%d ", used[i]);
                    for (int s = i; s < n; s++) {
                        used[s] = used[s + 1];
                    }
                    solve(n - 1, c, a, 1);
                    break;
                }
            }
            if (k == 1) {
                if (used[i] <= last)
                    continue;
                if (dp[n][i][1] < c) {
                    c -= dp[n][i][1];
                } else {
                    // System.out.printf("%d, %d, %d\n", i, used[i], c);
                    System.out.printf("%d ", used[i]);
                    int a = used[i];
                    for (int s = i; s < n; s++) {
                        used[s] = used[s + 1];
                    }
                    solve(n - 1, c, a, 0);
                    break;
                }
            }
            if (k == 0) {
                if (used[i] >= last)
                    continue;
                if (dp[n][i][0] < c) {
                    c -= dp[n][i][0];
                } else {
                    // System.out.printf("%d, %d, %d\n", i, used[i], c);
                    System.out.printf("%d ", used[i]);
                    int a = used[i];
                    for (int s = i; s < n; s++) {
                        used[s] = used[s + 1];
                    }
                    solve(n - 1, c, a, 1);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        dp();
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        int K = cin.nextInt();
        while (K-- > 0) {
            int N = cin.nextInt();
            long C = cin.nextLong();
            for (int i = 1; i <= N; i++) {
                used[i] = i;
            }
            solve(N, C, 0, 3);
            System.out.println();
        }
    }

}
