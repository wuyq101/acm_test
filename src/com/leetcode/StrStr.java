package com.leetcode;

/**
 * Created by Administrator on 13-11-27.
 */
public class StrStr {
    public String strStr(String haystack, String needle) {
        if (needle == null || needle.length() == 0)
            return haystack;
        if (haystack == null || haystack.length() == 0)
            return null;
        if (needle.length() == 1) {
            char ch = needle.charAt(0);
            for (int i = 0; i < haystack.length(); i++) {
                if (haystack.charAt(i) == ch)
                    return haystack.substring(i);
            }
            return null;
        }
        int[] t = kpm_table(needle);
        int m = 0, i = 0, len = haystack.length(), lw = needle.length();
        while (m + i < len) {
            if (needle.charAt(i) == haystack.charAt(m + i)) {
                if (i == lw - 1)
                    return haystack.substring(m);
                i++;
            } else {
                m = m + i - t[i];
                i = t[i] > -1 ? t[i] : 0;
            }
        }
        return null;
    }

    private int[] kpm_table(String w) {
        int[] t = new int[w.length()];
        int pos = 2, cnd = 0;
        t[0] = -1;
        t[1] = 0;
        int len = w.length();
        while (pos < len) {
            if (w.charAt(pos - 1) == w.charAt(cnd)) {
                t[pos++] = ++cnd;
            } else if (cnd > 0) {
                cnd = t[cnd];
            } else {
                t[pos++] = 0;
            }
        }
        return t;
    }

    public static void main(String[] args) {
        String w = "a";
        String test = "a";
        StrStr t = new StrStr();
        t.strStr(test, w);
    }
}
