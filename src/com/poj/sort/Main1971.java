package com.poj.sort;

import java.util.Scanner;

import static java.lang.Math.abs;
import static java.lang.Math.min;

/**
 * <pre>
 *     原题：http://poj.org/problem?id=1971
 *     题意：给出平面上的点的坐标，求其中四个点组成平行四边形的个数。
 *     分析：如果四个点可以组成平行四边形，那么两对角线一定相交在各自的中点上，也就说如果四个点，ab的中点和cd的中点相同的话，那么这四个
 *     点可以组成一个平行四边形。这样就把问题转化为，任意两点做线段，取中点。然后找这些中点中相同的个数。先排序，然后一遍扫描。
 * </pre>
 * User: wuyq101
 * Date: 13-3-4
 * Time: 下午1:30
 */
public class Main1971 {
    private static int n, MAX = 500000;
    private static int[] px = new int[1000], py = new int[1000], sorted = new int[MAX];
    private static double[] x = new double[MAX], y = new double[MAX];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int t = cin.nextInt();
        while (t-- > 0) {
            n = cin.nextInt();
            for (int i = 0; i < n; i++) {
                px[i] = cin.nextInt();
                py[i] = cin.nextInt();
            }
            solve();
        }
    }

    private static void solve() {
        //两两计算中点
        int k = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                x[k] = (px[i] + px[j]) / 2.0;
                y[k] = (py[i] + py[j]) / 2.0;
                sorted[k] = k;
                k++;
            }
        }
        quicksort(0, k - 1);
        int cnt = 0;
        for (int i = 0; i < k; i++) {
            double dx = x[sorted[i]], dy = y[sorted[i]];
            int s = 1;
            for (int j = i + 1; j < k; j++) {
                double sx = x[sorted[j]], sy = y[sorted[j]];
                if (dcmp(dx, sx) == 0 && dcmp(dy, sy) == 0) {
                    s++;
                    i = j;
                } else
                    break;
            }
            if (s > 1) {
                cnt += s * (s - 1) / 2;
            }
        }
        System.out.println(cnt);
    }

    private static int dcmp(double a, double b) {
        double s = a - b;
        if (abs(s) < 1e-6) return 0;
        if (s > 1e-6) return 1;
        else return -1;
    }

    private static void quicksort(int left, int right) {
        if (right <= left) return;
        int pivot = select_pivot(left, right);
        pivot = partition(left, right, pivot);
        quicksort(left, pivot - 1);
        quicksort(pivot + 1, right);
    }

    private static int select_pivot(int left, int right) {
        int mid = (left + right) / 2, pivot = mid;
        int min, max;
        if (isSmaller(sorted[left], sorted[right])) {
            min = left;
            max = right;
        } else {
            min = right;
            max = left;
        }
        if (isSmaller(sorted[max], sorted[mid])) {
            pivot = max;
        }
        if (isSmaller(sorted[mid], sorted[min])) {
            pivot = min;
        }
        return pivot;
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
        int a = dcmp(x[i], x[j]);
        if (a == -1)
            return true;
        if (a == 1)
            return false;
        int b = dcmp(y[i], y[j]);
        return b == -1;
    }

    private static void swap(int i, int j) {
        int t = sorted[i];
        sorted[i] = sorted[j];
        sorted[j] = t;
    }
}
