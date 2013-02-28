package com.poj.sort;

import java.util.Scanner;

/**
 *  <pre>
 *      原题：http://poj.org/problem?id=1838
 *      题意：给出香蕉树的坐标，然后前后左右可相连，算做一个区域，求前k大区域的和。
 *      分析：以x为第一关键字，y为第二关键字，排序，扫描每个点和它前一个点是否x相等，y相差1，如果是则连接。
 *                以y为第一关键字，x为第二关键字，排序，扫描每个点和它前一个点是否y相等，x相差1，如果是，则找这两个点的根，如果根相同，则表示
 *                这两个点已经连接，如果不相同，则将两个根连接。
 *                扫描之后，开始计算每个连通区域的大小，记录在sum[]数组中，然后求前k的数的和。可以使用快速排序的方法求一个数组中的前k大的数。
 *  </pre>
 * User: wuyq101
 * Date: 13-2-26
 * Time: 下午6:11
 */
public class Main1838 {
    private static int n, k;
    private static final int MAX = 16000;
    private static int[][] points = new int[2][MAX];
    private static int[] sorted = new int[MAX];
    private static int[] sum = new int[MAX];
    private static int[] parent = new int[MAX];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        n = cin.nextInt();
        k = cin.nextInt();
        for (int i = 0; i < n; i++) {
            sorted[i] = i;
            parent[i] = i;
            points[0][i] = cin.nextInt();
            points[1][i] = cin.nextInt();
        }
        solve();
    }

    private static void solve() {
        quicksort(0, n - 1, 0);
        for (int i = 1; i < n; i++) {
            //x相等，y相差1
            if (points[0][sorted[i - 1]] == points[0][sorted[i]] && points[1][sorted[i - 1]] + 1 == points[1][sorted[i]])
                parent[sorted[i]] = getParent(sorted[i - 1]);
        }
        quicksort(0, n - 1, 1);
        for (int i = 1; i < n; i++) {
            //y相等，x相差1
            if (points[1][sorted[i - 1]] == points[1][sorted[i]] && points[0][sorted[i - 1]] + 1 == points[0][sorted[i]]) {
                int p1 = getParent(sorted[i - 1]), p2 = getParent(sorted[i]);
                //如果两个的根是同一个，那就不用在添加了，如果根不一样，那么将两个根合并
                if (p1 != p2)
                    parent[p2] = p1;
            }
        }
        union();
        System.out.printf("%d\n", first_k_sum(0, n - 1, k));
    }

    private static void union() {
        for (int i = 0; i < n; i++) {
            int p = parent[i];
            while (p != parent[p])
                p = parent[p];
            sum[p]++;
        }
    }

    private static int getParent(int i) {
        int p = parent[i];
        if (i == p) return i;
        p = getParent(parent[p]);
        parent[i] = p;
        return p;
    }

    private static int first_k_sum(int left, int right, int k) {
        if (k == 0 || left > right) return 0;
        if (k >= right - left + 1) {
            return sub_sum(left, right);
        }
        int pivot = (left + right) / 2;
        pivot = partition_(left, right, pivot);
        if (right - pivot == k)
            return sub_sum(pivot + 1, right);
        else if (right - pivot > k)
            return first_k_sum(pivot + 1, right, k);
        else
            return sub_sum(pivot, right) + first_k_sum(left, pivot - 1, k - (right - pivot + 1));
    }

    private static int sub_sum(int left, int right) {
        int s = 0;
        for (int i = left; i <= right; i++)
            s += sum[i];
        return s;
    }

    private static int partition_(int left, int right, int pivot) {
        int v = sum[pivot];
        swap_(pivot, right);
        pivot = left;
        for (int i = left; i < right; i++) {
            if (sum[i] <= v)
                swap_(i, pivot++);
        }
        swap_(pivot, right);
        return pivot;
    }

    private static void swap_(int i, int j) {
        int t = sum[i];
        sum[i] = sum[j];
        sum[j] = t;
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
            if (isSmaller(sorted[i], v, flag))
                swap(i, pivot++);
        }
        swap(pivot, right); // Move pivot to its final place
        return pivot;
    }

    private static boolean isSmaller(int i, int j, int flag) {
        if (points[flag][i] < points[flag][j])
            return true;
        if (points[flag][i] > points[flag][j])
            return false;
        return points[1 - flag][i] < points[1 - flag][j];
    }

    private static void swap(int i, int j) {
        int temp = sorted[i];
        sorted[i] = sorted[j];
        sorted[j] = temp;
    }
}