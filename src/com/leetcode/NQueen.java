package com.leetcode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 13-12-23.
 */
public class NQueen {
    public ArrayList<String[]> solveNQueens(int n) {
        ArrayList<String[]> list = new ArrayList<String[]>();
        int[] col = new int[n];
        Arrays.fill(col, -1);
        for (int i = 0; i < n; i++) {
            col[i] = 0;
            nqueen(list, col, 1);
            Arrays.fill(col, -1);
        }
        return list;
    }

    private void nqueen(ArrayList<String[]> list, int[] col, int row) {
        if (row == col.length) {
            addResult(list, col);
            return;
        }
        for (int i = 0; i < col.length; i++) {
            if (check(col, i, row)) {
                col[i] = row;
                nqueen(list, col, row + 1);
                col[i] = -1;
            }
        }
    }

    private boolean check(int[] col, int i, int row) {
        if (col[i] >= 0) return false;
        //check if col[i]==row
        for (int j = 0; j < col.length; j++) {
            if (j == i) continue;
            if (col[j] == -1) continue;
            if (col[j] == row) return false;
            int col_dist = Math.abs(i - j);
            int row_dist = Math.abs(row - col[j]);
            if (col_dist == row_dist) return false;
        }
        return true;
    }

    private void addResult(ArrayList<String[]> list, int[] col) {
        String s = "";
        for (int i = 0; i < col.length; i++)
            s += ".";
        String[] strs = new String[col.length];
        for (int i = 0; i < col.length; i++) {
            StringBuilder sb = new StringBuilder(s);
            strs[i] = sb.replace(col[i], col[i] + 1, "Q").toString();
        }
        list.add(strs);
    }

    public static void main(String[] args) {
        NQueen test = new NQueen();
        ArrayList<String[]> list = test.solveNQueens(4);
        for (String[] strs : list) {
            for (String s : strs) {
                System.out.println(s);
            }
            System.out.println("-------------------------------------------");
        }
        System.out.println(list.size());
    }
}
