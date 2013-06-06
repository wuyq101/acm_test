package com.poj.dp;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 1887 求最长递减序列
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main1887 {
    // M[j]=k,所有可能的长度为j的最长递减序列中，结尾最小的那个数为X[k],
    // M递减
    private static int[] M = new int[5000];
    private static int[] X = new int[5000];
    private static int L;
    private static int n;

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        int m = 0;
        StringBuilder sb = new StringBuilder();
        while (cin.hasNextInt()) {
            int d = cin.nextInt();
            if (d == -1)
                break;
            n = 0;
            while (d != -1) {
                // 使用d和M做比较，并更新结果
                X[n++] = d;
                d = cin.nextInt();
            }
            lds();
            // print result
            sb.append("Test #" + (++m) + ":\n  maximum possible interceptions: " + L + "\n\n");
            // System.out.printf("Test #%d:\n  maximum possible interceptions: %d\n\n", ++m, L);
        }
        System.out.print(sb.toString());
    }

    // 最长递减序列
    private static void lds() {
        L = 1;
        M[1] = X[0];
        for (int i = 1; i < n; i++) {
            int j = 0;
            int l = 1, r = L;
            while (l <= r) {
                int mid = (l + r) / 2;
                if (M[mid] > X[i])
                    l = mid + 1;
                else
                    r = mid - 1;
            }
            j = r;
            if (j == L || X[i] > M[j + 1]) {
                M[j + 1] = X[i];
                if (L < j + 1)
                    L = j + 1;
            }
        }
    }

}
