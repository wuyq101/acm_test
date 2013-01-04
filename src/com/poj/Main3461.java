package com.poj;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 3461 KMP算法
 */
public class Main3461 {
    private static char[] w;
    private static char[] s;
    private static int[] t;
    private static int count;

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        int c = cin.nextInt();
        while (c-- > 0) {
            w = cin.next().toCharArray();
            s = cin.next().toCharArray();
            kmp_search();
        }
    }

    private static void kmp_search() {
        count = 0;
        if (w.length == 1) {
            char ch = w[0];
            for (int i = 0; i < s.length; i++) {
                if (ch == s[i]) {
                    count++;
                }
            }
            System.out.println(count);
            return;
        }
        kmp_table();
        int m = 0;
        int i = 0;
        int ls = s.length, lw = w.length;
        while (m + i < ls) {
            if (w[i] == s[m + i]) {
                if (i == lw - 1) {
                    // found a match
                    count++;
                    m = m + i - t[i];
                    i = t[i] > -1 ? t[i] : 0;
                    continue;
                }
                i = i + 1;
                continue;
            }
            m = m + i - t[i];
            i = t[i] > -1 ? t[i] : 0;
        }
        System.out.println(count);
    }

    /**
     * a0,a1,...,ak-1,ak === aj-k,aj-k+1,...,aj-1,aj, 找最大的k 则对于pattern的前j+1序列字符，则有如下可能 ⑴ pattern[k+1]==pattern[j+1]
     * 此时overlay(j+1)=k+1=overlay(j)+1 ⑵ pattern[k+1]≠pattern[j+1] 此时只能在pattern前k+1个子符组所的子串中找到相应的overlay函数，
     * h=overlay(k),如果此时pattern[h+1]==pattern[j+1],则overlay(j+1)=h+1否则重复(2)过程.
     */
    private static void kmp_table() {
        t = new int[w.length + 1];
        t[0] = -1;
        t[1] = 0;
        // the current position we are computing in T
        int pos = 2;
        // the zero-based index in W of the next character of the current candidate substring
        int cnd = 0;
        int lw = w.length;
        while (pos <= lw) {
            // first case: the substring continues
            if (w[pos - 1] == w[cnd]) {
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