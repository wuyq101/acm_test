package com.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 13-11-26.
 */
public class LetterCombination {
    private static Map<Character, char[]> map;

    public ArrayList<String> letterCombinations(String digits) {
        if (map == null)
            init();
        digits = digits.replaceAll("1", "");
        digits = digits.replaceAll("0", "");
        ArrayList<String> result = new ArrayList<String>();
        dfs(result, "", digits);
        return result;
    }

    private void dfs(ArrayList<String> result, String pre, String digits) {
        if (digits.length() == 0) {
            result.add(pre);
            return;
        }
        char[] ch = map.get(digits.charAt(0));
        String next = digits.substring(1);
        for (int i = 0; i < ch.length; i++) {
            dfs(result, pre + ch[i], next);
        }
    }

    private void init() {
        map = new HashMap<Character, char[]>();
        String[] strs = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        for (int i = 0; i < strs.length; i++)
            map.put((char) (i + '0'), strs[i].toCharArray());
    }

    public static void main(String[] args) {
        String d = "1233";
        LetterCombination t = new LetterCombination();
        System.out.println(t.letterCombinations(d));
    }

}
