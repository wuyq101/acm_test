package com.leetcode;

/**
 * Created by Administrator on 13-11-26.
 */
public class Prefix {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0)
            return "";
        int end = strs.length - 1;
        while (end > 0) {
            for (int i = 0, j = end; i <= j; i++, j--) {
                strs[i] = prefix(strs[i], strs[j]);
                if (i == j - 1 || i == j) {
                    end = i;
                }
            }
        }
        return strs[0];
    }

    private String prefix(String a, String b) {
        int idx = 0;
        while (idx < a.length() && idx < b.length() && a.charAt(idx) == b.charAt(idx))
            idx++;
        return a.substring(0, idx);
    }

    public static void main(String[] args) {
        Prefix t = new Prefix();
        String[] strs = {"aaacf", "aaab", "aa", "aaabb"};
        System.out.println(t.longestCommonPrefix(strs));
    }
}
