package com.leetcode;

import java.util.ArrayList;

/**
 * Created by Administrator on 13-12-14.
 */
public class LIS {
    public int lis(int[] a) {
        int[] max = new int[a.length];
        ArrayList<Integer>[] lists = new ArrayList[a.length];
        max[0] = a[0];
        lists[0] = new ArrayList<Integer>();
        lists[0].add(a[0]);
        int len = 1;
        for (int i = 1; i < a.length; i++) {
            if (a[i] > max[len - 1]) {
                lists[len] = (ArrayList<Integer>) lists[len - 1].clone();
                lists[len].add(a[i]);
                max[len++] = a[i];
            } else {
                int pos = search(max, len, a[i]);
                max[pos] = a[i];
                lists[pos].set(lists[pos].size() - 1, a[i]);
            }
        }
        for (int i = 0; i < len; i++)
            System.out.printf("%d ", max[i]);
        System.out.println();
        for (int i = 0; i < lists[len - 1].size(); i++)
            System.out.printf("%d ", lists[len - 1].get(i));
        System.out.println();
        return len;
    }

    private int search(int[] max, int len, int target) {
        int left = 0, right = len - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (max[mid] <= target)
                left = mid + 1;
            else
                right = mid - 1;
        }
        return left;
    }

    public static void main(String[] args) {
        int[] a = {0, 8, 4, 12, 2, 10, 6, 14, 1, 9, 5, 13, 3, 11, 7, 15};
        LIS test = new LIS();
        System.out.println(test.lis(a));
    }
}
