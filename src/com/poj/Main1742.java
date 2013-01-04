package com.poj;
import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;
import static java.lang.Math.max;

/**
 * 1742 多重背包问题
 */
public class Main1742 {
    private static int N;
    private static int M;
    private static int[] a;
    private static int[] c;
    private static int[] f = new int[100001];
    private static int[] dqueue = new int[100001];
    private static int[] pl = new int[100001];

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        while (cin.hasNextInt()) {
            N = cin.nextInt();
            M = cin.nextInt();
            if (N == 0 && M == 0)
                break;
            a = new int[N + 1];
            c = new int[N + 1];
            for (int i = 1; i <= N; i++) {
                a[i] = cin.nextInt();
            }
            for (int i = 1; i <= N; i++) {
                c[i] = cin.nextInt();
            }
            Arrays.fill(f, 0);
            Arrays.fill(f, 0);
            f[0] = 1;
            for (int i = 1; i <= N; i++) {
                int maxi = a[i] * c[i];
                if (c[i] == 1) {
                    // 01背包
                    for (int j = M; j >= a[i]; j--)
                        f[j] = max(f[j], f[j - a[i]]);
                } else if (maxi < M) {
                    // 多重背包, 单调队列
                    for (int j = 0; j < a[i]; j++) {
                        int head = 0, tail = 0;
                        for (int k = j; k <= M; k += a[i]) {
                            if (tail != head) {
                                if (k - pl[head] > maxi)
                                    head++;
                            }
                            if (f[k] > 0) {
                                dqueue[tail] = f[k];
                                pl[tail] = k;
                                tail++;
                            } else if (tail != head)
                                f[k] = 1;
                        }
                    }
                } else {
                    // 完全背包
                    for (int j = a[i]; j <= M; j++)
                        f[j] = max(f[j], f[j - a[i]]);
                }
            }
            int ans = 0;
            for (int j = 1; j <= M; j++) {
                if (f[j] > 0)
                    ans++;
            }
            System.out.println(ans);
        }
    }

}