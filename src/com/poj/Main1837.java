package com.poj;
import java.io.BufferedInputStream;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * 1837
 */
public class Main1837 {
    private static int C;
    private static int G;
    // hooks
    private static int[] h;
    // weights
    private static int[] w;
    private static int sum;

    // f[i][j]挂满前i个砝码，平衡度为j的数量, 平衡度最大值sum*(h中绝对值最大的那个)
    // f[0..20][-7500...7500], 因为j不能为负数，所以数组整体右移
    private static int[][] f;

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        C = cin.nextInt();
        G = cin.nextInt();
        h = new int[C + 1];
        for (int i = 1; i <= C; i++) {
            h[i] = cin.nextInt();
        }
        w = new int[G + 1];
        sum = 0;
        for (int i = 1; i <= G; i++) {
            w[i] = cin.nextInt();
            sum += w[i];
        }
        solve();
    }

    @SuppressWarnings("unchecked")
    private static void solve() {
        // 初始化
        // 取h绝对值最大
        int hook = Math.max(Math.abs(h[1]), Math.abs(h[C]));
        int MAX_VALUE = sum * hook;
        f = new int[G + 1][MAX_VALUE * 2 + 1];
        // 表示不用物体时，平衡度为0有一种挂法
        f[0][MAX_VALUE] = 1;
        Set<Integer>[] sets = new Set[2];
        sets[0] = new HashSet<Integer>();
        sets[1] = new HashSet<Integer>();
        sets[0].add(0);
        for (int i = 1; i <= G; i++) {
            // 针对每一个砝码
            Set<Integer> set0 = sets[(i - 1) % 2];
            Set<Integer> set1 = sets[i % 2];
            set1.clear();
            for (int j : set0) {
                int index = j + MAX_VALUE;
                for (int k = 1; k <= C; k++) {
                    // 针对每一个hook的位置
                    int new_index = h[k] * w[i] + index;
                    f[i][new_index] += f[i - 1][index];
                    set1.add(new_index - MAX_VALUE);
                }
            }
        }
        System.out.println(f[G][MAX_VALUE]);
    }

}
