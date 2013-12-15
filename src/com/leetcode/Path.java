package com.leetcode;

import java.util.BitSet;

/**
 * Created by Administrator on 13-12-10.
 */
public class Path {
    public int findKthSmall(int[] A, int[] B, int k) {
        //check error
        return findKthSmall(A, 0, B, 0, k);

    }

    private int findKthSmall(int[] a, int i, int[] b, int j, int k) {
        if (k == 1)
            return Math.min(a[i], b[j]);
        if (k == 2)
            return Math.max(a[i], b[j]);
        int half = k / 2;
        int i1 = i;
        if (i + half < a.length)
            i1 = i + half;
        int j1 = j;
        if (j + half < b.length)
            j1 = j + half;
        if (a[i1] == b[j1]) {
            return a[i];
        }
        if (a[i1] < b[j1]) {
            return findKthSmall(a, i1, b, j, k - (i1 - i));
        } else {
            return findKthSmall(a, i, b, j1, k - (j1 - j));
        }
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid == null) return 0;
        int m = obstacleGrid.length;
        if (m == 0) return 0;
        int n = obstacleGrid[0].length;
        if (n == 0) return 0;
        if (obstacleGrid[0][0] == 1 || obstacleGrid[m - 1][n - 1] == 1) return 0;
        int[][] dp = new int[2][n];
        int index = 0;
        dp[1 - index][0] = 1;
        for (int i = 0; i < m; i++) {
            int last = 1 - index;
            for (int j = 0; j < n; j++) {
                dp[index][j] = 0;
                if (obstacleGrid[i][j] == 1) {
                    continue;
                }
                if (j > 0 && obstacleGrid[i][j - 1] == 0)
                    dp[index][j] += dp[index][j - 1];
                if (i > 0 && obstacleGrid[i - 1][j] == 0)
                    dp[index][j] += dp[last][j];
                else if (i == 0)
                    dp[index][j] += dp[last][j];
            }
            index = last;
        }
        return dp[1 - index][n - 1];
    }


    public static void main(String[] args) {
        Path test = new Path();
        int[] a = {1, 2, 4, 8, 13};
        int[] b = {7, 9, 12, 16, 34, 50};
        int t = test.findKthSmall(a, b, 10);
        System.out.println(t);
    }
}
