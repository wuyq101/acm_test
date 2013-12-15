package com.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 13-12-8.
 */
public class Anagrams {
    public ArrayList<String> anagrams(String[] strs) {
        ArrayList<String> list = new ArrayList<String>();
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        for (String s : strs) {
            String h = hash(s);
            List<String> group = map.get(h);
            if (group == null) {
                group = new ArrayList<String>();
                map.put(h, group);
            }
            group.add(s);
        }
        for (List<String> group : map.values()) {
            if (group.size() > 1) {
                for (String s : group)
                    list.add(s);
            }
        }
        return list;
    }

    private static String hash(String s) {
        char[] ch = s.toCharArray();
        Arrays.sort(ch);
        return new String(ch);
    }
}
