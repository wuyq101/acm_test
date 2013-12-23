package com.leetcode;

/**
 * Created by Administrator on 13-12-22.
 */
public class MinWindow {
    public String minWindow(String S, String T) {
        if (S == null || T == null) return "";
        if (S.length() < T.length()) return "";
        int[] total = new int[265];
        int[] count = new int[256];
        for (int i = 0; i < T.length(); i++) {
            total[T.charAt(i)]++;
        }
        int cnt = 0;
        int best_start = -1, best_end = -1;
        for (int start = 0, end = 0; end < S.length(); end++) {
            char ch = S.charAt(end);
            if (total[ch] == 0) continue;
            count[ch]++;
            if (count[ch] <= total[ch])
                cnt++;
            if (cnt == T.length()) {
                //find a window
                //move start ro right as right as possible
                while (total[S.charAt(start)] == 0 || count[S.charAt(start)] > total[S.charAt(start)]) {
                    if (count[S.charAt(start)] > total[S.charAt(start)])
                        count[S.charAt(start)]--;
                    start++;
                }
                if (best_start == -1) {
                    best_start = start;
                    best_end = end;
                } else if (end - start < best_end - best_start) {
                    best_start = start;
                    best_end = end;
                }
            }
        }
        if (best_start == -1) return "";
        return S.substring(best_start, best_end + 1);
    }

    public static void main(String[] args) {
        MinWindow test = new MinWindow();
        String S = "ADOBECODEBANC";
        String T = "ABC";
        System.out.println(test.minWindow(S, T));
    }
}
