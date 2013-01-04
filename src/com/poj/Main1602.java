package com.poj;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1602 zip
 * 
 * @author wyq@palmdeal.com
 * @version 1.0
 */
public class Main1602 {
    private static int len;
    private static char[] s = new char[10000];
    private static int p;
    private static char[] t = new char[10000];
    private static int[] u = new int[10000];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        char ch = cin.next().charAt(0);
        if ('A' == ch) {
            len = cin.nextInt();
            cin.next().getChars(0, len, s, 0);
            zip();
        } else {
            len = cin.nextInt();
            cin.next().getChars(0, len, t, 0);
            p = cin.nextInt();
            unzip();
        }
    }

    private static void unzip() {
        if (len == 1) {
            System.out.printf("%c\n", t[0]);
            return;
        }
        char[] ss = new char[len];
        for (int i = 0; i < len; i++) {
            ss[i] = t[i];
            u[i] = 0;
        }
        Arrays.sort(ss, 0, len);
        p = p - 1;
        s[0] = t[p];
        int cnt = 1;
        int idx = 0;
        for (int i = 0; i < len; i++) {
            if (ss[i] == t[p]) {
                s[len - 1] = t[i];
                p = i;
                idx = len - 1;
                u[i] = 1;
                cnt++;
                break;
            }
        }
        while (cnt < len) {
            // 找p之前的那个字符
            char ch = t[p];
            for (int j = len - 1; j >= 0; j--) {
                if (u[j] == 0 && ss[j] == ch) {
                    s[--idx] = t[j];
                    u[j] = 1;
                    p = j;
                    cnt++;
                    break;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(s[i]);
        }
        System.out.println(sb.toString());
    }

    private static void zip() {
        if (len == 1) {
            System.out.printf("%c\n%d", s[0], 1);
            return;
        }
        for (int i = 0; i < len; i++)
            u[i] = 0;
        StringBuilder sb = new StringBuilder();
        char ch = (char) 256;
        int idx = len;
        for (int i = 0; i < len; i++) {
            ch = (char) 256;
            idx = len;
            // 找最小的
            for (int j = len - 1; j >= 0; j--) {
                if (u[j] == 0 && s[j] <= ch) {
                    ch = s[j];
                    idx = j;
                }
            }
            sb.append(idx - 1 >= 0 ? s[idx - 1] : s[len - 1]);
            if (idx - 1 == 0) {
                p = i + 1;
            }
            u[idx] = 1;
        }
        System.out.printf("%s\n%d", sb.toString(), p);
    }

}
