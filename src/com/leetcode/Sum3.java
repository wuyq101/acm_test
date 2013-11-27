package com.leetcode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 13-11-18.
 */
public class Sum3 {
    public ArrayList<ArrayList<Integer>> fourSum(int[] num, int target) {
        Arrays.sort(num);
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < num.length - 3; i++) {
            if (i > 0 && num[i] == num[i - 1]) continue;
            for (int j = i + 1; j < num.length - 2; j++) {
                if (j > i + 1 && num[j] == num[j - 1]) continue;
                int s = j + 1, t = num.length - 1;
                while (s < t) {
                    if (s > j + 1 && num[s] == num[s - 1]) {
                        s++;
                        continue;
                    }
                    if (t < num.length - 1 && num[t] == num[t + 1]) {
                        t--;
                        continue;
                    }
                    int sum = num[i] + num[j] + num[s] + num[t];
                    if (sum < target) {
                        s++;
                        continue;
                    }
                    if (sum > target) {
                        t--;
                        continue;
                    }
                    ArrayList<Integer> list = new ArrayList<Integer>();
                    list.add(num[i]);
                    list.add(num[j]);
                    list.add(num[s]);
                    list.add(num[t]);
                    result.add(list);
                    s++;
                    t--;
                }
            }
        }
        return result;
    }

    public int threeSumClosest(int[] num, int target) {
        Arrays.sort(num);
        int best = Integer.MAX_VALUE, ans = 0;
        for (int i = 0; i < num.length - 1; i++) {
            if (i > 0 && num[i] == num[i - 1]) continue;
            int j = i + 1, k = num.length - 1;
            while (j < k) {
                if (j > i + 1 && num[j] == num[j - 1]) {
                    j++;
                    continue;
                }
                if (k < num.length - 1 && num[k] == num[k + 1]) {
                    k--;
                    continue;
                }
                int sum = num[i] + num[j] + num[k];
                if (Math.abs(sum - target) < best) {
                    ans = sum;
                    best = Math.abs(sum - target);
                }
                if (sum < target) {
                    j++;
                    continue;
                }
                if (sum > target) {
                    k--;
                    continue;
                }
                return target;
            }
        }
        return ans;
    }


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
        int[] num = {1, 0, -1, 0, -2, 2};
        Sum3 test = new Sum3();
        System.out.println(test.fourSum(num, 0));
    }
}
