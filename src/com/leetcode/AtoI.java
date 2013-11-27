package com.leetcode;

/**
 * Created by Administrator on 13-11-25.
 */
public class AtoI {
    public int atoi(String str) {
        if (str == null) return 0;
        boolean first = true;
        boolean isNeg = false;
        boolean convert = true;
        long value = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (first) {
                if (Character.isWhitespace(ch))
                    continue;
                else {
                    first = false;
                    if (ch == '-' || ch == '+') {
                        isNeg = ch == '-';
                        continue;
                    }
                    if (!Character.isDigit(ch)) {
                        convert = false;
                        break;
                    } else {
                        value = ch - '0';
                    }
                }
            } else {
                if (Character.isDigit(ch)) {
                    value = value * 10 + ch - '0';
                    if (isNeg && -value <= Integer.MIN_VALUE)
                        return Integer.MIN_VALUE;
                    if (!isNeg && value >= Integer.MAX_VALUE)
                        return Integer.MAX_VALUE;
                } else {
                    break;
                }
            }
        }
        if (!convert)
            return 0;
        return (int) (isNeg ? -value : value);
    }

    public static void main(String[] args) {
        AtoI test = new AtoI();
        System.out.println(test.atoi("2147483648"));
    }
}
