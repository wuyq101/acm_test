package com.poj;
import java.util.Scanner;

/**
 * 1721 置换群
 * 
 * <pre>
 * x[] 被洗牌 S次之后，变成p[]，已知p[],求原来的x[]
 * 
 * 假设p被洗了k次之后，再变回p,k是一次循环
 * 
 * s--->s%k
 * 
 * 然后在将p洗牌  k-s次，就回到了x
 * </pre>
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main1721 {
    private static int N;
    private static int S;
    private static int[] p = new int[1001];
    private static int[][] x = new int[2][1001];
    // 每个每只上的循环
    private static int[] cycle = new int[1001];
    private static int K;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        N = cin.nextInt();
        S = cin.nextInt();
        for (int i = 1; i <= N; i++)
            p[i] = cin.nextInt();
        solve();
    }

    private static void cycle() {
        System.arraycopy(p, 1, x[0], 1, N);
        int cnt = 0, idx0 = 0, idx1 = 0;
        for (int k = 1; k <= N && cnt < N; k++) {
            idx1 = k % 2;
            idx0 = 1 - idx1;
            for (int i = 1; i <= N; i++) {
                x[idx1][i] = x[idx0][x[idx0][i]];
                if (p[i] == x[idx1][i] && cycle[i] == 0) {
                    cnt++;
                    cycle[i] = k;
                }
            }
        }
        K = cycle[1];
        for (int i = 2; i <= N; i++) {
            K = lcm(K, cycle[i]);
        }
    }

    private static int gcd(int a, int b) {
        if (a % b == 0)
            return b;
        return gcd(b, a % b);
    }

    private static int lcm(int a, int b) {
        if (a % b == 0)
            return a;
        return a * b / gcd(a, b);
    }

    private static void solve() {
        cycle();
        S = S % K;
        S = K - S;
        System.arraycopy(p, 1, x[0], 1, N);
        int idx0 = 0, idx1 = 0;
        for (int k = 1; k <= S; k++) {
            idx1 = k % 2;
            idx0 = 1 - idx1;
            for (int i = 1; i <= N; i++) {
                x[idx1][i] = x[idx0][x[idx0][i]];
            }
        }
        StringBuilder sb = new StringBuilder();
        idx1 = S % 2;
        for (int i = 1; i <= N; i++) {
            sb.append(x[idx1][i]).append('\n');
        }
        System.out.printf(sb.toString());
    }

}
