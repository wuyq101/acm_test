package com.poj;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1833 排列
 * 
 * @author wyq@palmdeal.com
 * @version 1.0
 */
public class Main1450 {
    private static int n;
    private static int k;
    private static int[] a = new int[1024];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int m = cin.nextInt();
        while (m-- > 0) {
            n = cin.nextInt();
            k = cin.nextInt();
            for (int i = 0; i < n; i++) {
                a[i] = cin.nextInt();
            }
            solve();
        }
    }

    private static void solve() {
        for (int i = 0; i < k; i++) {
            next_permutation();
        }
        // print
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(a[i]).append(' ');
        }
        System.out.println(sb.substring(0, sb.length() - 1));
    }

    private static void next_permutation() {
        // 从尾端找第一个a[j-1]<a[j]
        int j = 0;
        for (j = n - 1; j >= 1 && a[j - 1] > a[j]; j--)
            ;
        if (j >= 1) {
            int nMinLarge = a[j];
            int nMinId = j;
            // 从a[j]后，最小的比a[j]大的元素和下标
            for (int k = j; k < n; k++) {
                if (nMinLarge > a[k] && a[k] > a[j - 1]) {
                    nMinLarge = a[k];
                    nMinId = k;
                }
            }
            // 交换位置
            a[nMinId] = a[j - 1];
            a[j - 1] = nMinLarge;
            // j-1之后的排序
            Arrays.sort(a, j, n);
        } else {
            // 已经是最后一个排列，如 5 4 3 2 1
            for (j = 0; j < n; j++) {
                a[j] = j + 1;
            }
        }

    }
}
