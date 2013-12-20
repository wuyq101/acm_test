package com.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 13-12-19.
 */
public class NumTrees {
    private static Map<Integer, Integer> map = new HashMap<Integer, Integer>();

    public int numTrees(int n) {
        if (map.containsKey(n))
            return map.get(n);
        if (n == 0) {
            map.put(0, 1);
            return 1;
        }
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += numTrees(i - 1) * numTrees(n - i);
        }
        map.put(n, sum);
        return sum;
    }

    public static void main(String[] args) {
        NumTrees t = new NumTrees();
        for (int i = 0; i <= 10; i++) {
            System.out.println(t.numTrees(i));
        }

        int[] a = {1, 3};
        System.out.println(t.searchInsert(a, 5));
        System.out.println(t.searchInsert(a, 2));
        System.out.println(t.searchInsert(a, 7));
        System.out.println(t.searchInsert(a, 0));

    }


    public int searchInsert(int[] A, int target) {
        int left = 0, right = A.length - 1;
        int mid = 0;
        while (left <= right) {
            mid = (left + right) / 2;
            if (A[mid] == target)
                return mid;
            if (A[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        if (A[mid] >= target)
            return mid;
        else
            return mid + 1;
    }
}
