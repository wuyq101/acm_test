package com.poj.greedy;

import java.util.Scanner;

/**
 * <pre>
 *     原题：http://poj.org/problem?id=2376
 *     题意：给定n个线段的起点和终点，问最少需要几条可覆盖1-t的范围。
 *     分析：先将线段根据起点排序，如果起点相等的，则终点越靠后的排前面（长度长的排前面）。
 *               然后贪心地开始选择，每次都选择符合起点规则并且终点最大的线段，如果已经覆盖到末尾了，则退出。
 * </pre>
 * User: wuyq101
 * Date: 13-3-1
 * Time: 下午2:15
 */
public class Main2376 {
    private static int n, t;
    private static int[] start = new int[25000], finish = new int[25000], sorted = new int[25000];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        n = cin.nextInt();
        t = cin.nextInt();
        for (int i = 0; i < n; i++) {
            start[i] = cin.nextInt();
            finish[i] = cin.nextInt();
            sorted[i] = i;
        }
        solve();
    }

    private static void solve() {
        //排序
        quicksort(0, n - 1);
        //从最小的开始时间开始扫描,每次选择结束时间最晚的
        int min = 0, cnt = 0, max = 0;
        for (int i = 0; i < n; i++) {
            int next = -1;
            for (int j = i; j < n; j++) {
                if (start[sorted[j]] <= min + 1) {
                    if (finish[sorted[j]] >= max) {
                        max = finish[sorted[j]];
                        next = j;
                    }
                } else
                    break;
            }
            if (next == -1) {
                System.out.printf("-1\n");
                return;
            }
            i = next;
            min = max;
            cnt++;
            if (max >= t) {
                System.out.printf("%d\n", cnt);
                return;
            }
        }
        if (max >= t)
            System.out.printf("%d\n", cnt);
        else
            System.out.printf("-1\n");
    }

    private static void quicksort(int left, int right) {
        if (right <= left) return;
        int pivot = (left + right) / 2;
        pivot = partition(left, right, pivot);
        quicksort(left, pivot - 1);
        quicksort(pivot + 1, right);
    }

    private static int partition(int left, int right, int pivot) {
        int v = sorted[pivot];
        swap(pivot, right);
        pivot = left;
        for (int i = left; i < right; i++) {
            if (isSmaller(sorted[i], v))
                swap(pivot++, i);
        }
        swap(pivot, right);
        return pivot;
    }

    private static boolean isSmaller(int i, int j) {
        if (start[i] < start[j])
            return true;
        if (start[i] > start[j])
            return false;
        return finish[i] > finish[j];
    }

    private static void swap(int i, int j) {
        int t = sorted[i];
        sorted[i] = sorted[j];
        sorted[j] = t;
    }
}
