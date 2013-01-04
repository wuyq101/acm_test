package com.poj;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 3132, 完全背包问题
 */
public class Main3132 {
    // 1120之前的质数一共187个
    private static int[] p = new int[187];

    // f[k][j], k个质数，和为j的种数
    private static int[][] f;

    // <=1120
    private static int N;
    // <=14
    private static int K;

    public static void main(String[] args) {
        init();
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        while (cin.hasNextInt()) {
            N = cin.nextInt();
            K = cin.nextInt();
            if (N == 0 && K == 0)
                break;
            solve();
        }
    }

    private static void solve() {
        if (N == 1) {
            System.out.println(0);
            return;
        }
        f = new int[K + 1][N + 1];
        f[0][0] = 1;
        for (int i = 0; i < 187 && p[i] <= N; i++) {
            for (int v = N; v >= p[i]; v--) {
                for (int k = 1; k <= K; k++) {
                    f[k][v] += f[k - 1][v - p[i]];
                }
            }
        }
        System.out.println(f[K][N]);
    }

    // 打表求质数
    private static void init() {
        int k = 0;
        for (int i = 2; i <= 1120; i++) {
            if (isPrime(i)) {
                p[k++] = i;
            }
        }
    }

    private static boolean isPrime(int n) {
        if (n == 2 || n == 3)
            return true;
        if (n % 2 == 0 || n % 3 == 0)
            return false;
        // 因为每个质数mod6 == 1或者5
        // 按照步长2或者4交替进行
        // (i+1)^2 = i^2+2i+1
        for (int i = 5, j = 4, k = i * i; k <= n; j = 6 - j, i += j, k = i * i) {
            if (n % i == 0)
                return false;
        }
        return true;
    }
}