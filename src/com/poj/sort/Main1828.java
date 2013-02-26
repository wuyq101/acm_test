package com.poj.sort;

import java.util.Scanner;

/**
 * <pre>
 *     原题：http://poj.org/problem?id=1828
 *     题意：给出每个猴子的坐标，如果一个猴子(x0,y0)可以当猴王,仅当没有其他猴子的坐标值(x>=x0 &&  y>y0)。
 *     分析：排序，先根据x从小到大排序，如果x相等，根据y从小到大排序。然后从最后一个元素扫描计数。
 *              记录当前最大的max_y，如果前面的点y值大于max_y,则这个点是一个新的猴王，更新max_y，继续往前找。复杂度O（NlgN+N）。
 * </pre>
 * User: wuyq101
 * Date: 13-2-26
 * Time: 下午4:24
 */
public class Main1828 {
    private static int n;
    private static int[][] points = new int[2][50000];
    private static int[] sorted = new int[50000];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (true) {
            n = cin.nextInt();
            if (n == 0) break;
            for (int i = 0; i < n; i++) {
                sorted[i]=i;
                points[0][i] = cin.nextInt();
                points[1][i] = cin.nextInt();
            }
            solve();
        }
    }

    private static void solve() {
        quicksort(0, n - 1);
        int cnt = 1;
        int x = points[0][sorted[n - 1]], y = points[1][sorted[n - 1]];
        for (int i = n - 2; i >= 0; i--) {
            if (points[0][sorted[i]] != x && points[1][sorted[i]] > y) {
                cnt++;
                x = points[0][sorted[i]];
                y = points[1][sorted[i]];
            }
        }
        System.out.println(cnt);
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
        swap(pivot, right);// Move pivot to end
        pivot = left;
        for (int i = left; i < right; i++) {
            if (isSmaller(sorted[i], v))
                swap(i, pivot++);
        }
        swap(pivot, right); // Move pivot to its final place
        return pivot;
    }

    private static boolean isSmaller(int i, int j) {
        if (points[0][i] < points[0][j])
            return true;
        if (points[0][i] > points[0][j])
            return false;
        return points[1][i] < points[1][j];
    }

    private static void swap(int i, int j) {
        int temp = sorted[i];
        sorted[i] = sorted[j];
        sorted[j] = temp;
    }
}
