package com.leetcode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 13-11-18.
 */
public class Sum3 {
    public ArrayList<ArrayList<Integer>> threeSum(int[] num) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        Arrays.sort(num);
        for (int i = 0; i < num.length - 1; i++) {
            if (i > 0 && num[i] == num[i - 1]) continue;
            int j = i + 1, k = num.length - 1;
            while (j < k) {
                int sum = num[i] + num[j];
                int rest = -sum;
                if (num[k] > rest) {
                    k--;
                    while (k > j && num[k] == num[k + 1])
                        k--;
                    continue;
                }
                if (num[k] < rest) {
                    j++;
                    while (j < k && num[j] == num[j - 1])
                        j++;
                    continue;
                }
                ArrayList<Integer> list = new ArrayList<Integer>();
                list.add(num[i]);
                list.add(num[j]);
                list.add(num[k]);
                result.add(list);
                j++;
                while (j < k && num[j] == num[j - 1])
                    j++;
                k--;
                while (k > j && num[k] == num[k + 1])
                    k--;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] num = {-1, 0, -1, -1, -1, 1, 2, 2, 2, 2, -1, -4};
        Sum3 test = new Sum3();
        System.out.println(test.threeSum(num));
    }
}
