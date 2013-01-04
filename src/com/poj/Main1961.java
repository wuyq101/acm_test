package com.poj;
import java.util.Scanner;

/**
 * 1961 KMP
 * 
 * @author wyq@palmdeal.com
 * @version 1.0
 */
public class Main1961 {
    private static int N;
    private static char[] s;
    private static int[] t = new int[1000001];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int idx = 1;
        while (true) {
            N = cin.nextInt();
            if (N == 0)
                break;
            s = cin.next().toCharArray();
            System.out.printf("Test case #%d\n", idx++);
            solve();
            System.out.printf("\n");
        }
    }

    private static void solve() {
        kmp_table();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            if (t[i] > 0 && i % (i - t[i]) == 0) {
//                System.out.printf("%d %d\n", i, i / (i - t[i]));
                sb.append(i).append(' ').append(i / (i - t[i])).append('\n');
            }
        }
        System.out.print(sb.toString());
    }

    private static void kmp_table() {
        //t = new int[s.length + 1];
        t[0] = -1;
        t[1] = 0;
        // the current position we are computing in T
        int pos = 2;
        // the zero-based index in W of the next character of the current candidate substring
        int cnd = 0;
        int ls = N;
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
