package com.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 13-11-14.
 */
public class PalindromePartition {

    public ArrayList<ArrayList<String>> partition(String s) {
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
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        ArrayList<String> list = sub(dp, map, s, 0, n - 1);
        ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
        for (String str : list) {
            ArrayList<String> e = new ArrayList<String>();
            String[] ps = str.split(" ");
            for (String w : ps) {
                e.add(w);
            }
            result.add(e);
        }
        return result;
    }

    private ArrayList<String> sub(boolean[][] dp, Map<String, ArrayList<String>> map, String s, int i, int j) {
        String key = i + "," + j;
        if (map.containsKey(key)) {
            return map.get(key);
        }
        ArrayList<String> list = new ArrayList<String>();
        if (dp[i][j])
            list.add(s.substring(i, j + 1));
        for (int k = i; k < j; k++) {
            if (dp[i][k]) {
                ArrayList<String> left = new ArrayList<String>();
                left.add(s.substring(i, k + 1));
                ArrayList<String> right = sub(dp, map, s, k + 1, j);
                merge(list, left, right);
            }
        }
        map.put(key, list);
        return list;
    }

    private void merge(ArrayList<String> list, ArrayList<String> left, ArrayList<String> right) {
        for (String a : left) {
            for (String b : right) {
                String c = a + " " + b;
                if (!list.contains(c))
                    list.add(c);
            }
        }
    }

    public static void main(String[] args) {
        String s = "kwtbjmsjvbrwriqwxadwnufplszhqccayvdhhvscxjaqsrmrrqngmuvxnugdzjfxeihogzsdjtvdmkudckjoggltcuybddbjoizu";
        PalindromePartition test = new PalindromePartition();
        System.out.println(test.partition(s));
    }
}
