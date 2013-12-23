package com.leetcode;

import java.util.Arrays;

/**
 * Created by Administrator on 13-12-23.
 */
public class NQueenII {
    private static int count = 0;

    public int totalNQueens(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        int[] col = new int[n];
        count = 0;
        for (int i = 0; i < n; i++) {
            Arrays.fill(col, -1);
            col[i] = 0;
            nqueen(col, 1);
        }
        return count;
    }

    private void nqueen(int[] col, int row) {
        if (row == col.length) {
            count++;
            return;
        }
        for (int i = 0; i < col.length; i++) {
            if (check(col, row, i)) {
                col[i] = row;
                nqueen(col, row + 1);
                col[i] = -1;
            }
        }
    }

    private boolean check(int[] col, int row, int c) {
        if (col[c] >= 0) return false;
        for (int i = 0; i < col.length; i++) {
            if (i == c) continue;
            if (col[i] == -1) continue;
            int dist = Math.abs(i - c);
            if (dist == Math.abs(col[i] - row)) return false;
        }
        return true;
    }

    public static void main(String[] args) {
        NQueenII test = new NQueenII();
        for (int i = 1; i <= 20; i++) {
            int cnt = test.totalNQueens(i);
            System.out.println(cnt);
        }
    }
}
