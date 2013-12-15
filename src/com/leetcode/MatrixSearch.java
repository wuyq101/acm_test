package com.leetcode;

/**
 * Created by Administrator on 13-12-12.
 */
public class MatrixSearch {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null) return false;
        int m = matrix.length;
        if (m == 0) return false;
        int n = matrix[0].length;
        if (n == 0) return false;
        return searchMatrix(matrix, target, 0, m - 1, 0, n - 1);
    }

    private boolean searchMatrix(int[][] matrix, int target, int top, int bottom, int left, int right) {
        if (top > bottom || left > right) return false;
        int s = 0, t = Math.min(right - left, bottom - top);
        int mid = s;
        while (s <= t) {
            mid = (s + t) / 2;
            if (matrix[top + mid][left + mid] == target) return true;
            if (matrix[top + mid][left + mid] < target) {
                s = mid + 1;
            } else {
                t = mid - 1;
            }
        }
        if (matrix[top + mid][left + mid] < target) {
            return searchMatrix(matrix, target, top, top + mid, left + mid + 1, right) ||
                    searchMatrix(matrix, target, top + mid + 1, bottom, left, left + mid);
        } else {
            return searchMatrix(matrix, target, top, top + mid - 1, left + mid, right) ||
                    searchMatrix(matrix, target, top + mid, bottom, left, left + mid - 1);
        }
    }

    public static void main(String[] args) {
        MatrixSearch m = new MatrixSearch();
        int[][] matrix = {{1, 3}};
        System.out.println(m.addBinary("11", "100011"));
    }

    public String addBinary(String a, String b) {
        if (a == null && b == null) return null;
        if (a.length() == 0 || b.length() == 0)
            return a.length() == 0 ? b : a;
        int x = a.charAt(a.length() - 1) - '0';
        int y = b.charAt(b.length() - 1) - '0';
        String c = addBinary(a.substring(0, a.length() - 1), b.substring(0, b.length() - 1));
        if (x == 1 && y == 1) {
            return addBinary(c + "0", "10");
        }
        if (x == 0 && y == 0) {
            return c + "0";
        }
        return c + "1";
    }

}
