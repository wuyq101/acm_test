package com.leetcode;

/**
 * Created by Administrator on 13-11-12.
 */
public class LongestPalindrome {
    public String longestPalindrome(String s) {
        // IMPORTANT: Please reset any member data you declared, as
        // the same Solution instance will be reused for each test case.
        int len = s.length();
        char[] str = s.toCharArray();
        boolean[][] dp = new boolean[len][len];
        int max = 1, maxi = 0, maxj = 0;
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
            if (i + 1 < len && str[i] == str[i + 1]) {
                dp[i][i + 1] = true;
                max = 2;
                maxi = i;
                maxj = i + 1;
            }
        }
        for (int k = 2; k < len; k++) {
            for (int i = 0; i + k < len; i++) {
                if (str[i] == str[i + k]) {
                    dp[i][i + k] = dp[i + 1][i + k - 1];
                    if (dp[i][i + k]) {
                        if (k+1>max) {
                            max = k + 1;
                            maxi = i;
                            maxj = i + k;
                        }
                    }
                }
            }
        }
        return s.substring(maxi, maxj + 1);
    }

    public static void main(String[] args) {
        String s = "ccc";
        LongestPalindrome tester = new LongestPalindrome();
        System.out.println(tester.longestPalindrome(s));
    }
}
