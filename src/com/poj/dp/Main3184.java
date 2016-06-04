package com.poj.dp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * http://poj.org/problem?id=3184
 * dp[i][j] 代表前i个已经调整好，并且最后一个处在位置j的时候，最小的调整距离
 * dp[i+1][j] = |p[i]-j|+min{dp[i][j-D],dp[i][j-D-1]}
 * D = L/(N-1)
 * 因为要使中间的缝隙最大，所以两端的牛必须在边上。
 * 这样也限定了第i头牛，它的范围就是在[D*i,(D+1)*i]之间，计算的时候，只需要遍历这部分就够了。
 * Created by wuyq on 16/6/1.
 */
public class Main3184 {
    static int N, L;
    static int[] p;
    static int[][] dp;

    public static void main(String[] args) throws Exception {
        input();
        solve();
    }

    private static void solve() {
        dp = new int[2][L + 1];
        dp[0][0] = p[0];
        Arrays.fill(dp[0], 1, L + 1, -1);
        int k = 1;
        int D = L / (N - 1);
        for (int i = 1; i < N; i++) {
            int t = D * i - 1;
            while (t >= 0 && D * i - t <= D + 1) {
                dp[k][t] = -1;
                t--;
            }
            t = (D + 1) * i;
            while (t <= L && t <= (D + 1) * (i + 1)) {
                dp[k][t] = -1;
                t++;
            }
            for (int j = D * i; j <= (D + 1) * i && j <= L; j++) {
                dp[k][j] = -1;
                if (j - D >= 0 && dp[1 - k][j - D] >= 0) {
                    dp[k][j] = Math.abs(p[i] - j) + dp[1 - k][j - D];
                }
                if (j - D - 1 >= 0 && dp[1 - k][j - D - 1] >= 0) {
                    if (dp[k][j] == -1) {
                        dp[k][j] = Math.abs(p[i] - j) + dp[1 - k][j - D - 1];
                    } else {
                        dp[k][j] = Math.min(dp[k][j], Math.abs(p[i] - j) + dp[1 - k][j - D - 1]);
                    }
                }
            }
            k = 1 - k;
        }
        System.out.println(dp[1 - k][L]);
    }

    private static void input() throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        String[] s = cin.readLine().split(" ");
        N = Integer.parseInt(s[0]);
        L = Integer.parseInt(s[1]);
        p = new int[N];
        for (int i = 0; i < N; i++) {
            p[i] = Integer.parseInt(cin.readLine());
        }
    }
}
