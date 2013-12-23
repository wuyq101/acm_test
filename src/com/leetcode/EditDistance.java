package com.leetcode;

/**
 * Created by Administrator on 13-12-23.
 */
public class EditDistance {
    public int minDistance(String word1, String word2) {
        if (word1 == null || word1.length() == 0)
            return word2 == null ? 0 : word2.length();
        if (word2 == null || word2.length() == 0)
            return word1 == null ? 0 : word1.length();
        int[][] dp = new int[2][word2.length() + 1];
        int idx = 0;
        for (int i = 0; i <= word1.length(); i++) {
            int last = 1 - idx;
            for (int j = 0; j <= word2.length(); j++) {
                if (i == 0) {
                    dp[idx][j] = j;
                    continue;
                }
                if (j == 0) {
                    dp[idx][j] = i;
                    continue;
                }
                dp[idx][j] = Math.min(dp[last][j], dp[idx][j - 1]) + 1;
                dp[idx][j] = Math.min(dp[idx][j], dp[last][j - 1] + (word1.charAt(i - 1) == word2.charAt(j - 1) ? 0 : 1));
            }
            idx = last;
        }
        return dp[1 - idx][word2.length()];
    }

    public static void main(String[] args) {
        String a = "mart";
        String b = "karma";
        EditDistance test = new EditDistance();
        System.out.println(test.minDistance(a, b));
    }
}
