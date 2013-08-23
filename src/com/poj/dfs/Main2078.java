package com.poj.dfs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * <pre>
 *     http://poj.org/problem?id=2078
 *     题意：给n*n矩阵，通过每行的移位操作，求最大列和的最小值。
 * </pre>
 * Created by Administrator on 13-8-22.
 */
public class Main2078 {
    private static int n, min_max_col_sum;
    private static int[][] A = new int[7][7];
    private static int[] sum = new int[7];
    private static int[] temp = new int[7];

    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        while (true) {
            st = new StringTokenizer(cin.readLine());
            n = Integer.parseInt(st.nextToken());
            if (-1 == n) break;
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(cin.readLine());
                for (int j = 0; j < n; j++)
                    A[i][j] = Integer.parseInt(st.nextToken());
            }
            solve();
        }
    }

    private static void solve() {
        min_max_col_sum = Integer.MAX_VALUE;
        Arrays.fill(sum, 0);
        dfs(0);
        System.out.println(min_max_col_sum);
    }

    private static void dfs(int row) {
        if (row == n) return;
        for (int k = 0; k < n; k++) {
            shift(row, k);
            int max = 0;
            for (int j = 0; j < n; j++) {
                sum[j] += A[row][j];
                if (sum[j] > max)
                    max = sum[j];
            }
            if (row == n - 1 && max < min_max_col_sum)
                min_max_col_sum = max;
            if (max < min_max_col_sum)
                dfs(row + 1);
            for (int j = 0; j < n; j++)
                sum[j] -= A[row][j];
            shift(row, -k);
        }
    }

    private static void shift(int row, int k) {
        if (k == 0) return;
        System.arraycopy(A[row], 0, temp, 0, n);
        int idx;
        for (int j = 0; j < n; j++) {
            idx = j - k;
            if (idx < 0) idx += n;
            if (idx >= n) idx -= n;
            A[row][j] = temp[idx];
        }
    }
}
