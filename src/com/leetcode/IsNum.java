package com.leetcode;

import java.util.ArrayList;

/**
 * Created by Administrator on 13-12-13.
 */
public class IsNum {
    public boolean isNumber(String s) {
        if (s == null || s.trim().length() == 0) return false;
        s = s.trim();
        s = s.toLowerCase();
        if (s.charAt(0) == '-' || s.charAt(0) == '+')
            s = s.substring(1);
        if (s.length() == 0) return false;
        int dot = 0;
        int exp = 0;
        int digit = 0;
        //only digital
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (Character.isDigit(ch)) {
                digit++;
                continue;
            }
            if (ch == '.') {
                //if it is real number
                dot++;
                if (exp > 0) return false;
                if (dot > 1 || s.length() == 1) return false;
            } else if (ch == 'e') {
                //exp format
                exp++;
                //if e is at start or end
                if (exp > 1 || i == 0 || i == s.length() - 1 || digit == 0) return false;
                if (i + 1 < s.length() - 1) {
                    char next = s.charAt(i + 1);
                    if (next == '-' || next == '+')
                        i++;
                }
            } else
                return false;
        }
        return true;
    }


    public ArrayList<String> fullJustify(String[] words, int L) {
        ArrayList<String> list = new ArrayList<String>();
        if (words == null) return list;
        StringBuilder sb = new StringBuilder();
        int last = -1;
        for (int i = 0; i < words.length; i++) {
            if (sb.length() == 0 || sb.length() > 0 && sb.length() + 1 + words[i].length() <= L) {
                if (sb.length() == 0)
                    sb.append(words[i]);
                else
                    sb.append(' ').append(words[i]);
            } else {
                justify(sb, list, L);
                last = i - 1;
                sb.delete(0, sb.length());
                i--;
            }
        }
        if (last < words.length - 1) {
            list.add(sb.toString());
        }
        return list;
    }

    private void justify(StringBuilder sb, ArrayList<String> list, int L) {
        if (sb.length() == L) {
            list.add(sb.toString());
            return;
        }
        String[] words = sb.toString().split(" ");
        int slot_num = words.length - 1;
        if (slot_num == 0) {
            while (sb.length() < L)
                sb.append(' ');
            list.add(sb.toString());
            return;
        }
        int space = L - sb.length() + slot_num;
        int space_per_slot = space / slot_num;
        int left = space % slot_num;
        sb.delete(0, sb.length());
        for (int i = 0; i < words.length - 1; i++) {
            sb.append(words[i]);
            for (int j = 0; j < space_per_slot; j++) {
                sb.append(' ');
            }
            if (left > 0) {
                sb.append(' ');
                left--;
            }
        }
        sb.append(words[words.length - 1]);
        list.add(sb.toString());
    }

    public static void main(String[] args) {
        IsNum test = new IsNum();
        String[] s = {""};
        System.out.println(test.fullJustify(s, 2));
    }
}
