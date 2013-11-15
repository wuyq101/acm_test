package com.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 13-11-14.
 */
public class PalindromePartitionII {
    public int minCut(String s) {
        int n = s.length();
        char[] ch = s.toCharArray();
        boolean[][] dp = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            //the palindrome length is 1
            dp[i][i] = true;
            int j = i - 1, k = i + 1;
            while (j >= 0 && k < n && ch[j] == ch[k]) {
                dp[j--][k++] = true;
            }
            j = i;
            k = i + 1;
            while (j >= 0 && k < n && ch[j] == ch[k]) {
                dp[j--][k++] = true;
            }
        }
        Map<String, Integer> map = new HashMap<String, Integer>();
        return sub(dp, map, s, 0, n - 1);
    }

    private int sub(boolean[][] dp, Map<String, Integer> map, String s, int i, int j) {
        String key = i + "," + j;
        if (map.containsKey(key))
            return map.get(key);
        if (dp[i][j])
            return 0;
        int min = Integer.MAX_VALUE;
        for (int k = i; k < j; k++) {
            if (dp[i][k]) {
                min = Math.min(min, 1 + sub(dp, map, s, k + 1, j));
            }
        }
        map.put(key, min);
        return min;
    }

    public static void main(String[] args) {
        PalindromePartitionII test = new PalindromePartitionII();
        String s = "kwtbjmsjvbrwriqwxadwnufplszhqccayvdhhvscxjaqsrmrrqngmuvxnugdzjfxeihogzsdjtvdmkudckjoggltcuybddbjoizu";
        System.out.println(test.minCut(s));
    }
}
