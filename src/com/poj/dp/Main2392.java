package com.poj.dp;

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 2392 多重背包问题
 */
public class Main2392 {
    private static int K;

    private static Block[] b;

    private static int V;

    private static int[] f;

    private static int H;

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        V = 0;
        K = cin.nextInt();
        b = new Block[K];
        for (int i = 0; i < K; i++) {
            b[i] = new Block();
            b[i].height = cin.nextInt();
            b[i].altitude = cin.nextInt();
            b[i].amount = cin.nextInt();
            // 超过的也用不到
            if (b[i].height * b[i].amount > b[i].altitude) {
                b[i].amount = b[i].altitude / b[i].height;
            }
        }
        // 排序
        Arrays.sort(b);
        solve();
    }

    private static void solve() {
        // 确定V的大小
        V = b[K - 1].altitude;
        f = new int[V + 1];
        H = 0;
        for (int i = 0; i < K; i++) {
            multiplePack(i);
        }
        System.out.println(H);
    }

    private static void multiplePack(int i) {
        while (b[i].amount > 0) {
            zeroOnePack(b[i].height, b[i].altitude);
            b[i].amount -= 1;
        }
    }

    private static void zeroOnePack(int cost, int altitude) {
        int max = Math.min(V, altitude);
        for (int v = max; v >= cost; v--) {
            if (f[v - cost] + cost > f[v]) {
                f[v] = f[v - cost] + cost;
                H = Math.max(H, f[v]);
            }
        }
    }

    private static class Block implements Comparable<Block> {
        public int height;
        public int altitude;
        public int amount;

        @Override
        public int compareTo(Block o) {
            if (altitude == o.altitude)
                return 0;
            return altitude > o.altitude ? 1 : -1;
        }
    }
}

