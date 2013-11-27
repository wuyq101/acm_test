package com.leetcode;

/**
 * Created by Administrator on 13-11-25.
 */
public class Roman {
    /*
    I = 1
V = 5
X = 10
L = 50
C = 100
D = 500
M = 1000
Only I, X, and C can be used as the leading numeral in part of a subtractive pair.
I can only be placed before V and X.
X can only be placed before L and C.
C can only be placed before D and M.
     */
    public String intToRoman(int num) {
        return roman("", num);
    }

    public String roman(String pre, int num) {
        if (num == 0)
            return pre;
        if (num < 5) {
            if (num == 4) {
                if (pre.length() > 0) {
                    if (pre.charAt(pre.length() - 1) == 'V') {
                        return pre.substring(0, pre.length() - 1) + "IX";
                    } else {
                        return pre + "IV";
                    }
                } else {
                    return "IV";
                }
            }
            return pre + multiply('I', num);
        }
        if (num < 10) {
            return roman(pre + "V", num - 5);
        }
        if (num < 50) {
            if (num >= 40) {
                if (pre.length() > 0 && pre.charAt(pre.length() - 1) == 'L') {
                    return roman(pre.substring(0, pre.length() - 1) + "XC", num - 40);
                } else {
                    return roman(pre + "XL", num - 40);
                }
            }
            return roman(pre + "X", num - 10);
        }
        if (num < 100) {
            return roman(pre + "L", num - 50);
        }
        if (num < 500) {
            if (num >= 400) {
                if (pre.length() > 0 && pre.charAt(pre.length() - 1) == 'D') {
                    return roman(pre.substring(0, pre.length() - 1) + "CM", num - 400);
                } else {
                    return roman(pre + "CD", num - 400);
                }
            }
            return roman(pre + "C", num - 100);
        }
        if (num < 1000) {
            return roman(pre + "D", num - 500);
        }
        return roman(pre + "M", num - 1000);
    }

    public String multiply(char ch, int n) {
        String s = "";
        for (int i = 0; i < n; i++)
            s += ch;
        return s;
    }

    public static void main(String[] args) {
        Roman t = new Roman();
        for (int i = 1; i <= 300; i++) {
            System.out.println(t.intToRoman(i));
            System.out.println(t.romanToInt(t.intToRoman(i)));
        }
    }

    public int romanToInt(String s) {
        if (s.length() == 0)
            return 0;
        char ch = s.charAt(0);
        if (ch == 'I' || ch == 'X' || ch == 'C') {
            if (s.length() >= 2) {
                char b = s.charAt(1);
                if (ch == 'I' && (b == 'V' || b == 'X'))
                    return -getV(ch) + romanToInt(s.substring(1));
                if (ch == 'X' && (b == 'L' || b == 'C'))
                    return -getV(ch) + romanToInt(s.substring(1));
                if (ch == 'C' && (b == 'D' || b == 'M'))
                    return -getV(ch) + romanToInt(s.substring(1));
            }
        }
        return getV(ch) + romanToInt(s.substring(1));
    }

    private int getV(char ch) {
        if (ch == 'I')
            return 1;
        if (ch == 'V')
            return 5;
        if (ch == 'X')
            return 10;
        if (ch == 'L')
            return 50;
        if (ch == 'C')
            return 100;
        if (ch == 'D')
            return 500;
        if (ch == 'M')
            return 1000;
        return 0;
    }
}
