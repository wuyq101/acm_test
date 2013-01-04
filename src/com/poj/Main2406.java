package com.poj;
import java.util.Scanner;

/**
 * 2406 KMP
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main2406 {
    private static char[] s;
    private static int N;
    private static int[] t = new int[1000001];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (true) {
            String str = cin.next();
            if (".".equals(str))
                break;
            s = str.toCharArray();
            N = s.length;
            kmp_table();
            System.out.println(N % (N - t[N])==0 ? N / (N - t[N]) : 1);
        }
    }

    private static void kmp_table() {
        t[0] = -1;
        t[1] = 0;
        // the current position we are computing in T
        int pos = 2;
        // the zero-based index in W of the next character of the current candidate substring
        int cnd = 0;
        int ls = s.length;
        while (pos <= ls) {
            // first case: the substring continues
            if (s[pos - 1] == s[cnd]) {
                cnd = cnd + 1;
                t[pos] = cnd;
                pos = pos + 1;
            } else if (cnd > 0) {
                cnd = t[cnd];
            } else {
                t[pos] = 0;
                pos = pos + 1;
            }
        }
    }

}
