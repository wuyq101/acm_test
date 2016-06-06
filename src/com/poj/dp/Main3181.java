package com.poj.dp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * http://poj.org/problem?id=3181
 * 完全背包问题， 需要处理大整数加法
 * Created by wuyq on 16/6/4.
 */
public class Main3181 {
    static int N, K;

    public static void main(String[] args) throws Exception {
        input();
        solve();
    }

    private static void solve() {
        BigInteger[] dp = new BigInteger[N + 1];
        dp[0] = BigInteger.ONE;
        Arrays.fill(dp, 1, N + 1, BigInteger.ZERO);
        for (int i = 1; i <= K; i++) {
            for (int j = i; j <= N; j++) {
                dp[j] = dp[j].add(dp[j - i]);
            }
        }
        System.out.println(dp[N]);
    }

    private static void input() throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(cin.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        K = Integer.parseInt(tokenizer.nextToken());
    }
}
