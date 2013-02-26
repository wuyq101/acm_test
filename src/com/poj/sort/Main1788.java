package com.poj.sort;

import java.util.Scanner;

/**
 * User: wuyq101
 * Date: 13-2-26
 * Time: 上午11:48
 */
public class Main1788 {
    private static int[] sorted = new int[100000];
    private static int n;
    private static int[][] posts = new int[2][100000];
    private static int len = 0;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (true) {
            n = cin.nextInt();
            if (n == 0) break;
            for (int i = 0; i < n; i++) {
                sorted[i] = i;
                posts[0][i] = cin.nextInt();
                posts[1][i] = cin.nextInt();
            }
            solve();
        }
    }

    private static void solve() {
        len = 0;
        quicksort(0, n - 1, 0);
        scan(0);
        quicksort(0, n - 1, 1);
        scan(1);
        System.out.printf("The length of the fence will be %d units.\n", len);
    }

    private static void scan(int flag) {
        flag = 1 - flag;
        for (int i = 0; i < n; i += 2) {
            len += (posts[flag][sorted[i + 1]] - posts[flag][sorted[i]]);
        }
    }

    private static void quicksort(int left, int right, int flag) {
        if (right <= left) return;
        int pivot = (left + right) / 2;
        pivot = partition(left, right, pivot, flag);
        quicksort(left, pivot - 1, flag);
        quicksort(pivot + 1, right, flag);
    }

    private static int partition(int left, int right, int pivot, int flag) {
        int v = sorted[pivot];
        swap(pivot, right);// Move pivot to end
        pivot = left;//开始重新定位pivot的真正位置，同时将小于它的元素swap到前面
        for (int i = left; i < right; i++) {
            if (isSmaller(sorted[i], v, flag)) {
                swap(i, pivot++);
            }
        }
        swap(pivot, right); // Move pivot to its final place
        return pivot;
    }

    private static boolean isSmaller(int i, int j, int flag) {
        if (posts[flag][i] < posts[flag][j])
            return true;
        if (posts[flag][i] > posts[flag][j])
            return false;
        return posts[1 - flag][i] < posts[1 - flag][j];
    }

    private static void swap(int i, int j) {
        int temp = sorted[i];
        sorted[i] = sorted[j];
        sorted[j] = temp;
    }
}
