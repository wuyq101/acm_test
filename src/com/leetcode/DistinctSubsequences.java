package com.leetcode;

import java.util.ArrayList;

/**
 * Created by Administrator on 13-12-21.
 */
public class DistinctSubsequences {
    public int numDistinct(String S, String T) {
        if (S == null || T == null) return 0;
        int m = S.length(), n = T.length();
        if (m < n) return 0;
        int[][] dp = new int[2][m];
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int idx = 0;
        for (int i = 0; i < n; i++) {
            int last = 1 - idx;
            for (int j = 0; j < m; j++) {
                dp[idx][j] = j > 0 ? dp[idx][j - 1] : 0;
                if (i == 0) {
                    if (t[i] == s[j])
                        dp[idx][j] += 1;
                    continue;
                }
                if (t[i] == s[j])
                    dp[idx][j] += j > 0 ? dp[last][j - 1] : 0;
            }
            idx = last;
        }
        return dp[1 - idx][m - 1];
    }


    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1 == null || s2 == null || s3 == null) return false;
        if (s1.length() + s2.length() != s3.length()) return false;
        boolean[][] dp = new boolean[s1.length() + 1][s2.length() + 1];
        dp[0][0] = true;
        for (int i = 1; i <= s1.length(); i++)
            dp[i][0] = dp[i - 1][0] && s1.charAt(i - 1) == s3.charAt(i - 1);
        for (int j = 1; j <= s2.length(); j++)
            dp[0][j] = dp[0][j - 1] && s2.charAt(j - 1) == s3.charAt(j - 1);
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                dp[i][j] = s1.charAt(i - 1) == s3.charAt(i + j - 1) && dp[i - 1][j] || s2.charAt(j - 1) == s3.charAt(i + j - 1) && dp[i][j - 1];
            }
        }
        return dp[s1.length()][s2.length()];
    }

    public ArrayList<String> restoreIpAddresses(String s) {
        ArrayList<String> list = new ArrayList<String>();
        if (s == null || s.length() == 0) return list;
        for (int i = 1; i < s.length(); i++) {
            String a = s.substring(0, i);
            if (a.charAt(0) == '0' && a.length() > 1) break;
            if (Integer.valueOf(a) > 255) break;
            for (int j = i + 1; j < s.length(); j++) {
                String b = s.substring(i, j);
                if (b.charAt(0) == '0' && b.length() > 1) break;
                if (Integer.valueOf(b) > 255) break;
                for (int k = j + 1; k < s.length(); k++) {
                    String c = s.substring(j, k);
                    if (c.charAt(0) == '0' && c.length() > 1) break;
                    if (Integer.valueOf(c) > 255) break;
                    String d = s.substring(k);
                    if (d.charAt(0) == '0' && d.length() > 1) continue;
                    if (d.charAt(0) != '0' && d.length() >= 4) continue;
                    if (Long.valueOf(d) > 255) continue;
                    list.add(a + "." + b + "." + c + "." + d);
                }
            }
        }
        return list;
    }

    public static void main(String[] args) {
        String S = "25525511135";
        String T = "rabbit";
        DistinctSubsequences test = new DistinctSubsequences();
        System.out.println(test.restoreIpAddresses("010010"));
    }
}
