package com.poj.simulate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 2081 Recaman's Sequence
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main2081 {
    private static int[] k = new int[500001];

    private static List<Integer> list = new ArrayList<Integer>();

    private static int max = 0;

    private static int[] used = new int[3012600 / 32];

    public static void main(String[] args) {
        init();
        Scanner cin = new Scanner(System.in);
        int d = cin.nextInt();
        while (d != -1) {
            list.add(d);
            if (max < d)
                max = d;
            d = cin.nextInt();
        }
        solve();
        print();
    }

    private static void print() {
        StringBuilder sb = new StringBuilder();
        for (int d : list) {
            // System.out.println(k[d]);
            sb.append(k[d]).append('\n');
        }
        System.out.print(sb.toString());
    }

    private static void solve() {
        k[0] = 0;
        for (int m = 1; m <= max; m++) {
            int d = k[m - 1] - m;
            if (d <= 0) {
                d = k[m - 1] + m;
                use(d);
                k[m] = d;
                continue;
            }
            if (isUsed(d)) {
                d = k[m - 1] + m;
                use(d);
                k[m] = d;
                continue;
            }
            k[m] = d;
            use(d);
        }
    }

    private static boolean isUsed(int k) {
        // 00---31 ---> 1
        // 32---63 --->
        int idx = k / 32;
        if (k % 32 == 0)
            idx += 1;
        int i = k % 32;
        return (used[idx] & p[i]) != 0;
    }

    private static int[] p = new int[32];

    private static void init() {
        for (int i = 0; i < 32; i++)
            p[i] = 1 << i;
    }

    private static void use(int k) {
        int idx = k / 32;
        if (k % 32 == 0)
            idx += 1;
        int i = k % 32;
        used[idx] |= p[i];
    }
}
