package com.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 13-11-13.
 */
public class WordBreakII {
    public ArrayList<String> wordBreak(String s, Set<String> dict) {
        int len = s.length();
        boolean[][] dp = new boolean[len][len];
        canWordBreak(dp, s, dict);
        if (!dp[0][len - 1])
            return new ArrayList<String>();
        Map<String, ArrayList<String>> visited = new HashMap<String, ArrayList<String>>();
        return sub(visited, dp, s, dict, 0, len - 1);
    }

    private ArrayList<String> sub(Map<String, ArrayList<String>> visited, boolean[][] dp, String s, Set<String> dict, int i, int j) {
        if (visited.containsKey(i + "," + j)) {
            return visited.get(i + "," + j);
        }
        ArrayList<String> list = new ArrayList<String>();
        if (dp[i][j] && dict.contains(s.substring(i, j + 1))) {
            list.add(s.substring(i, j + 1));
        }
        for (int k = i; k < j; k++) {
            if (dp[i][k] && dp[k + 1][j]) {
                ArrayList<String> left = sub(visited, dp, s, dict, i, k);
                ArrayList<String> right = sub(visited, dp, s, dict, k + 1, j);
                //merge
                for (String a : left) {
                    for (String b : right) {
                        String c = a + " " + b;
                        if (!list.contains(c))
                            list.add(c);
                    }
                }
            }
        }
        visited.put(i + "," + j, list);
        return list;
    }

    private void canWordBreak(boolean[][] dp, String s, Set<String> dict) {
        int len = s.length();
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
    }

    public static void main(String[] args) {
        WordBreakII test = new WordBreakII();
        String s = "catsanddog";
        Set<String> dict = new HashSet<String>();
        dict.add("cat");
        dict.add("cats");
        dict.add("and");
        dict.add("sand");
        dict.add("dog");
        ArrayList<String> list = test.wordBreak(s, dict);
        for (String str : list) {
            System.out.println(str);
        }
    }
}
