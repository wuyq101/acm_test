package com.leetcode;

import java.util.ArrayList;

/**
 * Created by Administrator on 13-11-19.
 */
public class Triangle {
    public int minimumTotal(ArrayList<ArrayList<Integer>> triangle) {
        int[][] dp = new int[2][triangle.size() + 1];
        int idx = 0;
        for (int i = triangle.size() - 1; i >= 0; i--) {
            ArrayList<Integer> list = triangle.get(i);
            for (int j = 0; j < list.size(); j++) {
                dp[idx][j] = Math.min(dp[1 - idx][j], dp[1 - idx][j + 1]) + list.get(j);
            }
            idx = 1 - idx;
        }
        return dp[1 - idx][0];
    }

    public ArrayList<ArrayList<Integer>> generate(int numRows) {
        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < numRows; i++) {
            ArrayList<Integer> list = new ArrayList<Integer>(i);
            if (i == 0) {
                list.add(1);
            } else {
                list.add(1);
                ArrayList<Integer> pre = result.get(i - 1);
                for (int j = 1; j <= i - 1; j++) {
                    list.add(pre.get(j - 1) + pre.get(j));
                }
                list.add(1);
            }
            result.add(list);
        }
        return result;
    }

    public ArrayList<Integer> getRow(int rowIndex) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        int[][] dp = new int[2][rowIndex+1];
        int idx = 0;
        dp[idx][0] = 1;
        idx = 1 - idx;
        for (int i = 1; i <= rowIndex; i++) {
            dp[idx][0] = 1;
            for (int j = 1; j <= i; j++) {
                dp[idx][j] = dp[1 - idx][j - 1] + dp[1 - idx][j];
            }
            dp[idx][i] = 1;
            idx = 1 - idx;
        }
        for (int i = 0; i <= rowIndex; i++) {
            list.add(dp[1-idx][i]);
        }
        return list;
    }

    public static void main(String[] args) {
        Triangle test = new Triangle();
        System.out.println(test.getRow(20));
    }
}
