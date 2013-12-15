package com.leetcode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 13-12-10.
 */
public class Sum {
    public ArrayList<ArrayList<Integer>> combinationSum2(int[] num, int target) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        int[] index = new int[num.length];
        Arrays.sort(num);
        solve(result, target, 0, num, index, 0);
        return result;
    }

    private void solve(ArrayList<ArrayList<Integer>> result, int target, int sum, int[] num, int[] index, int n) {
        if (sum > target) return;
        if (sum == target) {
            //find a solution
            addList(result, num, index);
            return;
        }
        if (n >= num.length) return;
        //use n
        if (index[n] == 0) {
            boolean flag = true;
            if (n > 0 && num[n] == num[n - 1]) {
                if (index[n - 1] == 0) {
                    flag = false;
                }
            }
            if (flag) {
                index[n] = 1;
                solve(result, target, sum + num[n], num, index, n + 1);
                index[n] = 0;
            }
        }
        solve(result, target, sum, num, index, n + 1);
    }

    private void addList(ArrayList<ArrayList<Integer>> result, int[] num, int[] index) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < index.length; i++) {
            if (index[i] == 1) {
                list.add(num[i]);
            }
        }
        result.add(list);
    }
}
