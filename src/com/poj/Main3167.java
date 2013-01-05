package com.poj;
import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 3167 kmp
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main3167 {
    private static int N;
    private static int K;
//    private static int S;
    private static int[] n;
    private static int[] k;
    private static int count;

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        N = cin.nextInt();
        K = cin.nextInt();
//        S = cin.nextInt();
        cin.nextInt();
        n = new int[N + 1];
        k = new int[K + 1];
        for (int i = 1; i <= N; i++) {
            n[i] = cin.nextInt();
        }
        for (int i = 1; i <= K; i++) {
            k[i] = cin.nextInt();
        }
        if (K > N) {
            System.out.println(0);
            return;
        }
        count = 0;
        if (K == 1)
            search_1();
        else if (K == 2)
            search_2();
        else
            kmp_search();
    }

    private static char[] s;
    private static char[] w;
    private static int[] t;

    private static void kmp_search() {
        init();
        kmp_table();
        StringBuilder sb = new StringBuilder();
        int m = 0;
        int i = 0;
        int ls = s.length, lw = w.length;
        while (m + i < ls) {
            if (w[i] == s[m + i]) {
                if (!check(m + 1, i + 1)) {
                    m = m + i - t[i];
                    i = t[i] > -1 ? t[i] : 0;
                    continue;
                }
                if (i == lw - 1) {
                    // found a match ? 还需要再全部检查一遍 n(1+logn)
                    count++;
                    sb.append(m + 1).append('\n');
                    m = m + i - t[i];
                    i = t[i] > -1 ? t[i] : 0;
                    continue;
                }
                i++;
                continue;
            }
            m = m + i - t[i];
            i = t[i] > -1 ? t[i] : 0;
        }
        System.out.println(count);
        System.out.println(sb.toString());
    }

    private static boolean check(int m, int i) {
        if (equal[i] > 0)
            if (n[m - 1 + i] != n[m - 1 + equal[i]])
                return false;
        if (less[i] > 0)
            if (n[m - 1 + i] >= n[m - 1 + less[i]])
                return false;
        if (great[i] > 0)
            if (n[m - 1 + i] <= n[m - 1 + great[i]])
                return false;
        // 需要全部都检查一遍
        int[] temp = Arrays.copyOfRange(n, m, m + i);
        int[] p = temp.clone();
        Arrays.sort(temp);
        int index = 1;
        for (int h = 0; h < i; h++) {
            for (int j = 0; j < i; j++) {
                if (p[j] == temp[j])
                    p[j] = index;
            }
            if (h + 1 < i && temp[h + 1] != temp[h])
                index++;
        }
        for (int j = 0; j < i; j++) {
            if (p[j] != k[j + 1])
                return false;
        }
        return true;
    }

    private static void kmp_table() {
        t = new int[w.length + 1];
        t[0] = -1;
        t[1] = 0;
        int pos = 2;
        int cnd = 0;
        while (pos <= w.length) {
            // 比较pos-1和cnd
            if (w[pos - 1] == w[cnd]) {
                cnd++;
                t[pos++] = cnd;
            } else if (cnd > 0) {
                cnd = t[cnd];
            } else {
                t[pos++] = 0;
            }
        }
    }

    private static void search_1() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= N; i++) {
            sb.append(i).append('\n');
        }
        System.out.println(N);
        System.out.println(sb.toString());
    }

    private static void search_2() {
        StringBuilder sb = new StringBuilder();
        if (k[1] == k[2]) {
            for (int i = 1; i < N; i++) {
                if (n[i] == n[i + 1]) {
                    count++;
                    sb.append(i).append('\n');
                }
            }
        } else if (k[1] > k[2]) {
            for (int i = 1; i < N; i++) {
                if (n[i] > n[i + 1]) {
                    count++;
                    sb.append(i).append('\n');
                }
            }
        } else {
            for (int i = 1; i < N; i++) {
                if (n[i] < n[i + 1]) {
                    count++;
                    sb.append(i).append('\n');
                }
            }
        }
        System.out.println(count);
        System.out.println(sb.toString());
    }

    private static int[] less;
    private static int[] great;
    private static int[] equal;

    private static void init() {
        int[] temp = k.clone();
        Arrays.sort(temp, 1, K + 1);
        int index = 1;
        for (int i = 1; i <= K; i++) {
            for (int j = 1; j <= K; j++) {
                if (k[j] == temp[i])
                    k[j] = index;
            }
            if (i + 1 <= K && temp[i + 1] != temp[i])
                index++;
        }

        w = new char[K - 1];
        s = new char[N - 1];
        for (int i = 2; i <= K; i++) {
            if (k[i] == k[i - 1])
                w[i - 2] = '=';
            else if (k[i] > k[i - 1]) {
                w[i - 2] = '>';
            } else {
                w[i - 2] = '<';
            }
        }

        for (int i = 2; i <= N; i++) {
            if (n[i] == n[i - 1])
                s[i - 2] = '=';
            else if (n[i] > n[i - 1])
                s[i - 2] = '>';
            else
                s[i - 2] = '<';
        }
        // 1 3 2 4数组
        // less: 0 0 2 0
        // great 0 1 1 2
        less = new int[K + 1];
        great = new int[K + 1];
        equal = new int[K + 1];
        less[1] = 0;
        great[1] = 0;
        equal[1] = 0;
        for (int i = 2; i <= K; i++) {
            for (int j = 1; j < i; j++) {
                if (equal[i] == 0 && k[i] == k[j]) {
                    equal[i] = j;
                }
                // less
                if ((less[i] == 0 && k[i] < k[j]) || (less[i] > 0 && k[i] < k[j] && k[j] < k[less[i]])) {
                    less[i] = j;
                }
                // great
                if ((great[i] == 0 && k[i] > k[j]) || (great[i] > 0 && k[i] > k[j] && k[j] > k[great[i]])) {
                    great[i] = j;
                }
            }
        }
    }

}
