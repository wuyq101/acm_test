package com.poj;
import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1159 DP 回文串
 * 
 * @author vanguard001
 * @version 1.0
 */
public class Main1159 {
    private static int[][] dp = new int[2][5003];
    private static String s;

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        cin.nextInt();
        s = cin.next();
        solve(s);
    }

    private static void solve(String line) {
        // 初始化
        int len = line.length();
        for (int i = 0; i < 2; i++) {
            Arrays.fill(dp[i], 0);
        }
        // ch[i]==ch[j], dp[i][j] = dp[i+1, j-1]
        // else dp[i][j] = 1+min{dp[i+1],[j], dp[i][j-1]};
        for (int i = len; i >= 1; i--) {
            for (int j = i; j <= len; j++) {
                int index1 = i % 2;
                int index2 = 1 - index1;
                if (s.charAt(i - 1) == s.charAt(j - 1)) {
                    dp[index1][j] = dp[index2][j - 1];
                } else {
                    dp[index1][j] = 1 + (dp[index1][j - 1] < dp[index2][j] ? dp[index1][j - 1] : dp[index2][j]);
                }
            }
        }
        System.out.println(dp[1][len]);
    }
}
