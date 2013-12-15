package com.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 13-11-27.
 */
public class SubString {


    public static void main(String[] args) {
        String t = "lingmindraboofooowingdingbarrwingmonkeypoundcake";
        String[] L = {"fooo", "barr", "wing", "ding", "wing"};
        SubString test = new SubString();
        System.out.println(test.findSubstring(t, L));
    }

    private ArrayList<Integer> findSubstring(String S, String[] L) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (L == null || L.length == 0)
            return list;
        Map<String, Integer> count = new HashMap<String, Integer>();
        Map<String, Integer> tmp = new HashMap<String, Integer>();
        for (String s : L) {
            Integer n = count.get(s);
            count.put(s, n == null ? 1 : 1 + n);
        }
        int w = L[0].length();
        int n = S.length();
        int end = n - w * L.length;
        for (int i = 0; i < w; i++) {
            for (int j = i; j < n; j += w) {
                String word = S.substring(j, j + w);
                if (!count.containsKey(word)) {
                    tmp.clear();
                    continue;
                }
                Integer cnt = tmp.get(word);
                if (cnt == null) {
                    tmp.put(word, 1);
                    continue;
                }
                if (cnt < count.get(word)) {
                    tmp.put(word, n + 1);
                    continue;
                }

            }
        }
        return list;
    }
}
