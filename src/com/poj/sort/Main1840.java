package com.poj.sort;

import java.util.Arrays;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * <pre>
 *      原题：http://poj.org/problem?id=1840
 *      题意：a1x13+ a2x23+ a3x33+ a4x43+ a5x53=0 ，xi∈[-50,50], xi != 0,  i∈{1,2,3,4,5}， 给出a1-a5求解的个数。
 *      分析：先算前三项，直接开一个100w的数组记录，然后排序，在计算后面两项，二分查找。
 *  </pre>
 * User: wuyq101
 * Date: 13-2-28
 * Time: 上午9:06
 */
public class Main1840 {
    private static int[] a = new int[6];
    private static int[] p = new int[51];
    private static int[] v = new int[1000000];
    private static int n;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int cnt = 0, t = 0;
        for (int i = 1; i <= 50; i++)
            p[i] = i * i * i;
        for (int i = 1; i <= 5; i++)
            a[i] = cin.nextInt();
        for (int i = -50; i <= 50; i++) {
            if (i == 0) continue;
            for (int j = -50; j <= 50; j++) {
                if (j == 0) continue;
                for (int k = -50; k <= 50; k++) {
                    if (k == 0) continue;
                    t = a[1] * pow(i) + a[2] * pow(j) + a[3] * pow(k);
                    v[n++] = t;
                }
            }
        }
        quicksort(0, n - 1);
        for (int i = -50; i <= 50; i++) {
            if (i == 0) continue;
            for (int j = -50; j <= 50; j++) {
                if (j == 0) continue;
                t = -a[4] * pow(i) - a[5] * pow(j);
                int idx = binarysearch(0, n - 1, t);
                if (idx < 0) continue;
                for (int k = idx; k >= 0; k--) {
                    if (v[k] == t)
                        cnt++;
                    else
                        break;
                }
                for (int k = idx + 1; k < n; k++) {
                    if (v[k] == t)
                        cnt++;
                    else
                        break;
                }
            }
        }
        System.out.println(cnt);
    }

    private static int binarysearch(int left, int right, int key) {
        int mid;
        while (left <= right) {
            mid = (left + right) / 2;
            if (v[mid] == key)
                return mid;
            if (v[mid] < key)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return -1;
    }

    private static void quicksort(int left, int right) {
        if (right <= left) return;
        int pivot = (left + right) / 2;
        pivot = partition(left, right, pivot);
        quicksort(left, pivot - 1);
        quicksort(pivot + 1, right);
    }

    private static int partition(int left, int right, int pivot) {
        int value = v[pivot];
        swap(pivot, right);
        pivot = left;
        for (int i = left; i < right; i++) {
            if (v[i] < value)
                swap(pivot++, i);
        }
        swap(pivot, right);
        return pivot;
    }

    private static void swap(int i, int j) {
        int t = v[i];
        v[i] = v[j];
        v[j] = t;
    }

    private static int pow(int i) {
        return i < 0 ? -p[-i] : p[i];
    }
}