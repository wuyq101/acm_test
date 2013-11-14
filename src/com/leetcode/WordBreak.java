package com.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Administrator on 13-11-13.
 */
public class WordBreak {
    public boolean wordBreak(String s, Set<String> dict) {
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        for (int k = 0; k < len; k++) {
            for (int i = 0; i < len; i++) {
                int j = i + k;
                if (j >= len) break;
                if (dict.contains(s.substring(i, j + 1)))
                    dp[i][j] = true;
                else {
                    for (int h = i; h < j; h++) {
                        if (dp[i][h] && dp[h + 1][j]) {
                            dp[i][j] = true;
                            break;
                        }
                    }
                }
            }
        }
        return dp[0][len - 1];
    }

    public static void main(String[] args) {
        WordBreak test = new WordBreak();
        Set<String> dict = new HashSet<String>();
        dict.add("leet");
        dict.add("code");
        String s = "codeleet";
        System.out.println(test.wordBreak(s, dict));
    }
}
