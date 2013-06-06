package com.poj.kmp;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * 2752 KMP
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main2752 {
    private static char[] s = new char[400000];
    private static int N;
    private static int[] t = new int[400001];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext()) {
            String w = cin.next();
            N = w.length();
            w.getChars(0, N, s, 0);
            if (N == 1) {
                System.out.println(1);
                continue;
            }
            kmp_table();
            solve();
        }
    }

    private static void solve() {
        Set<Integer> set = new TreeSet<Integer>();
        int i = N;
        while (t[i] > 0) {
            set.add(t[i]);
            i = t[i];
        }
        set.add(N);
        StringBuilder sb = new StringBuilder();
        for (int j : set) {
            sb.append(j).append(' ');
        }
        System.out.println(sb.substring(0, sb.length() - 1));
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
