package com.poj.sort;

import java.util.Scanner;

import static java.lang.Math.abs;

/**
 * <pre>
 *     原题：http://poj.org/problem?id=1928
 *     题意：给出一块地，每个点上有花生，给出花生的个数，让狗在规定时间内，从多到少去采摘，严格按照这个顺序。求能采摘的最大个数。
 *     分析：先排序，后模拟。注意点：将二维对应到一维，然后排序。使用快排的时候，因为是从大到小排序，如果基数的值是0了，那之后的部分
 *     pivot+1~right部分就不用在递归调用了，因为这之后的值肯定都是0.
 * </pre>
 * User: wuyq101
 * Date: 13-3-4
 * Time: 上午10:03
 */
public class Main1928 {
    private static int m, n, k;
    private static int[] field = new int[2500], sorted = new int[2500];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int t = cin.nextInt();
        while (t-- > 0) {
            m = cin.nextInt();
            n = cin.nextInt();
            k = cin.nextInt();
            for (int i = 0, size = m * n; i < size; i++) {
                field[i] = cin.nextInt();
                sorted[i] = i;
            }
            solve();
        }
    }

    private static void solve() {
        //排序
        quicksort(0, m * n - 1);
        int sum = 0, start_x = -1, start_y = -1;
        for (int i = 0, size = m * n; i < size && k > 0; i++) {
            if (field[sorted[i]] == 0) break;
            int x = sorted[i] % n, y = sorted[i] / n;
            if (start_y == -1) {
                if (k - (y + 1 + 1 + y + 1) >= 0) {
                    sum += field[sorted[i]];
                    k -= (y + 1 + 1);
                    start_x = x;
                    start_y = y;
                } else break;
            } else {
                if (k - (abs(start_x - x) + abs(start_y - y)) - 1 - 1 - y >= 0) {
                    sum += field[sorted[i]];
                    k -= (abs(start_x - x) + abs(start_y - y) + 1);
                    start_x = x;
                    start_y = y;
                } else break;
            }
        }
        System.out.printf("%d\n", sum);
    }

    private static void quicksort(int left, int right) {
        if (right <= left) return;
        int pivot = (left + right) / 2;
        pivot = partition(left, right, pivot);
        quicksort(left, pivot - 1);
        if (field[sorted[pivot]] > 0)
            quicksort(pivot + 1, right);
    }

    private static int partition(int left, int right, int pivot) {
        int v = sorted[pivot];
        swap(pivot, right);
        pivot = left;
        for (int i = left; i < right; i++) {
            if (field[sorted[i]] > field[v])
                swap(pivot++, i);
        }
        swap(pivot, right);
        return pivot;
    }

    private static void swap(int i, int j) {
        int t = sorted[i];
        sorted[i] = sorted[j];
        sorted[j] = t;
    }
}
