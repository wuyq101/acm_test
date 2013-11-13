package com.leetcode;

/**
 * Created by Administrator on 13-11-12.
 */
public class LengthOfLongestSubstring {
    public int lengthOfLongestSubstring(String s) {
        // IMPORTANT: Please reset any member data you declared, as
        // the same Solution instance will be reused for each test case.
        int start = 0, end = 0, len = s.length();
        if (len <= 1) return len;
        if (len == 2) return s.charAt(0) == s.charAt(1) ? 1 : 2;
        char[] str = s.toCharArray();
        int max = 1;
        for (int i = 1; i < len; i++) {
            boolean repeat = false;
            for (int j = start; j < i; j++) {
                if (str[j] == str[i]) {
                    start = j + 1;
                    repeat = true;
                    break;
                }
            }
            if (!repeat) {
                if (i - start + 1 > max)
                    max = i - start + 1;
            }
        }
        return max;
    }

    public static void main(String[] args) {
        LengthOfLongestSubstring tester = new LengthOfLongestSubstring();
        int result = tester.lengthOfLongestSubstring("abcdeafgbcda");
        System.out.println(result);
    }
}
