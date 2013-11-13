package com.leetcode;

/**
 * Created by Administrator on 13-11-12.
 */
public class ZigZagConversion {
    public String convert(String s, int row) {
        if (row == 1)
            return s;
        char[] ch = new char[s.length()];
        int idx = 0, len = s.length();
        for (int i = 0; i < row; i++) {
            if (i == 0 || i == row - 1) {
                int start = i, gap = 2 * (row - 1);
                while (start < len) {
                    ch[idx++] = s.charAt(start);
                    start += gap;
                }
                continue;
            }
            int start = i, gap = 2 * (row - i - 1);
            while (start < len) {
                ch[idx++] = s.charAt(start);
                if (start + gap < len)
                    ch[idx++] = s.charAt(start + gap);
                start += 2 * (row - 1);
            }
        }
        return new String(ch);
    }

    public static void main(String[] args) {
        ZigZagConversion tester = new ZigZagConversion();
        String s = "1234";
        System.out.println(tester.convert(s, 2));
    }

}
