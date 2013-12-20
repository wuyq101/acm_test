package com.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

/**
 * Created by Administrator on 13-12-19.
 */
public class Inorder {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public ArrayList<ArrayList<Integer>> levelOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        if (root == null) return list;
        ArrayList<TreeNode> layer = new ArrayList<TreeNode>();
        layer.add(root);
        while (!layer.isEmpty()) {
            ArrayList<Integer> list_int = new ArrayList<Integer>();
            ArrayList<TreeNode> next = new ArrayList<TreeNode>();
            for (TreeNode node : layer) {
                list_int.add(node.val);
                if (node.left != null)
                    next.add(node.left);
                if (node.right != null)
                    next.add(node.right);
            }
            list.add(list_int);
            layer = next;
        }
        return list;
    }

    public ArrayList<ArrayList<Integer>> levelOrderBottom(TreeNode root) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        if (root == null) return list;
        ArrayList<TreeNode> layer = new ArrayList<TreeNode>();
        layer.add(root);
        while (!layer.isEmpty()) {
            ArrayList<Integer> list_int = new ArrayList<Integer>();
            ArrayList<TreeNode> next = new ArrayList<TreeNode>();
            for (TreeNode node : layer) {
                list_int.add(node.val);
                if (node.left != null)
                    next.add(node.left);
                if (node.right != null)
                    next.add(node.right);
            }
            list.add(0, list_int);
            layer = next;
        }
        return list;
    }

    public ArrayList<Integer> inorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (root == null) return list;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        TreeNode node = root;
        while (node.left != null) {
            stack.push(node.left);
            node = node.left;
        }
        while (!stack.isEmpty()) {
            node = stack.pop();
            list.add(node.val);
            if (node.right != null) {
                stack.push(node.right);
                node = node.right;
                while (node.left != null) {
                    stack.push(node.left);
                    node = node.left;
                }
            }
        }
        return list;
    }


    public ArrayList<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (root == null) return list;
        Stack<TreeNode> stack = new Stack<TreeNode>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            list.add(node.val);
            if (node.right != null)
                stack.push(node.right);
            if (node.left != null)
                stack.push(node.left);
        }
        return list;
    }

    public ArrayList<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (root == null) return list;
        Stack<TreeNode> first = new Stack<TreeNode>();
        Stack<TreeNode> second = new Stack<TreeNode>();
        first.push(root);
        while (!first.isEmpty()) {
            TreeNode node = first.pop();
            second.push(node);
            if (node.left != null)
                first.push(node.left);
            if (node.right != null)
                first.push(node.right);
        }
        while (!second.isEmpty()) {
            TreeNode node = second.pop();
            list.add(node.val);
        }
        return list;
    }

    public int climbStairs(int n) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        if (n == 2) return 2;
        int a = 1, b = 2, c = a + b;
        for (int i = 2; i < n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return c;
    }

    public int maxSubArray(int[] A) {
        int max = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = 0; i < A.length; i++) {
            sum += A[i];
            if (sum > max) {
                max = sum;
            }
            if (sum < 0)
                sum = 0;
        }
        return max;
    }

    public void merge(int A[], int m, int B[], int n) {
        int i = m - 1, j = n - 1;
        int idx = m + n - 1;
        while (i >= 0 && j >= 0) {
            if (A[i] > B[j]) {
                A[idx--] = A[i--];
            } else {
                A[idx--] = B[j--];
            }
        }
        while (j >= 0) {
            A[idx--] = B[j--];
        }
    }


    public ArrayList<ArrayList<Integer>> permute(int[] num) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        permute(list, num, 0);
        for (ArrayList<Integer> e : list) {
            System.out.println(e);
        }
        return list;
    }

    private void permute(ArrayList<ArrayList<Integer>> list, int[] num, int i) {
        if (i == num.length) {
            ArrayList<Integer> one_permute = new ArrayList<Integer>();
            for (int k = 0; k < num.length; k++) {
                one_permute.add(num[k]);
            }
            list.add(one_permute);
            return;
        }
        for (int k = i; k < num.length; k++) {
            boolean flag = false;
            if (k > i) {
                for (int j = k - 1; j >= i; j--)
                    if (num[j] == num[k]) {
                        flag = true;
                    }
            }
            if (flag) continue;
            swap(num, i, k);
            permute(list, num, i + 1);
            swap(num, i, k);
        }
    }

    private void swap(int[] num, int i, int j) {
        int t = num[j];
        num[j] = num[i];
        num[i] = t;
    }


    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int layer = 0; layer < n / 2; layer++) {
            for (int i = layer; i < n - 1 - layer; i++) {
                int temp = matrix[layer][i];
                //left to top
                matrix[layer][i] = matrix[n - 1 - layer - (i - layer)][layer];
                //bottom to left
                matrix[n - 1 - layer - (i - layer)][layer] = matrix[n - 1 - layer][n - 1 - layer - (i - layer)];
                //right to bottom
                matrix[n - 1 - layer][n - 1 - layer - (i - layer)] = matrix[i][n - 1 - layer];
                //top to right
                matrix[i][n - 1 - layer] = temp;
            }
        }
    }


    public void setZeroes(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return;
        int m = matrix.length, n = matrix[0].length;
        int r = -1, c = -1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    if (r == -1) {
                        r = i;
                        c = j;
                    } else {
                        matrix[i][c] = 0;
                        matrix[r][j] = 0;
                    }
                }
            }
        }
        if (r == -1) return;
        for (int i = 0; i < m; i++) {
            if (i == r) continue;
            if (matrix[i][c] == 0) {
                //set row i to zero
                for (int k = 0; k < n; k++)
                    matrix[i][k] = 0;
            }
        }
        for (int j = 0; j < n; j++) {
            if (j == c) continue;
            if (matrix[r][j] == 0) {
                //set column j to zero
                for (int i = 0; i < m; i++)
                    matrix[i][j] = 0;
            }
        }
        //set row r to zero
        for (int k = 0; k < n; k++)
            matrix[r][k] = 0;
        //set column c to zero
        for (int i = 0; i < m; i++)
            matrix[i][c] = 0;
    }


    public int[] plusOne(int[] digits) {
        int carry = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            int sum = digits[i] + carry;
            if (sum <= 9) {
                digits[i] = sum;
                return digits;
            } else {
                digits[i] = 0;
                carry = 1;
            }
        }
        if (carry == 1) {
            int[] result = new int[digits.length + 1];
            result[0] = 1;
            for (int i = 1; i < result.length; i++)
                result[i] = digits[i - 1];
            return result;
        }
        return null;
    }

    public ArrayList<ArrayList<Integer>> combine(int n, int k) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>();
        if (k == 0) return lists;
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = i + 1;
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        combine(lists, list, a, 0, k);
        return lists;
    }

    private void combine(ArrayList<ArrayList<Integer>> lists, ArrayList<Integer> list, int[] a, int i, int k) {
        if (list.size() == k) {
            ArrayList<Integer> temp = new ArrayList<Integer>(list);
            lists.add(temp);
            return;
        }
        for (int j = i; j < a.length; j++) {
            list.add(a[j]);
            combine(lists, list, a, j + 1, k);
            list.remove(list.size() - 1);
        }
    }

    public ArrayList<ArrayList<Integer>> subsets(int[] S) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>();
        if (S == null || S.length == 0) return lists;
        Arrays.sort(S);
        for (int k = 0; k <= S.length; k++) {
            ArrayList<Integer> list = new ArrayList<Integer>();
            combine(lists, list, S, 0, k);
        }
        return lists;
    }


    public void sortColors(int[] A) {
        int[] color = new int[3];
        for (int i = 0; i < A.length; i++)
            color[A[i]]++;
        int idx = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < color[i]; j++) {
                A[idx++] = i;
            }
        }
    }

    public int removeDuplicates(int[] A) {
        int len = 0;
        if (A.length == 0) return len;
        int last = A.length - 1;
        int pre = A[0];
        int cnt = 1;
        len = 1;
        for (int i = 1; i <= last; i++) {
            if (A[i] == pre) {
                if (cnt == 2) {
                    //we need to delete the A[i]
                    delete(A, i);
                    last--;
                    i--;
                } else {
                    cnt++;
                    len++;
                }
            } else {
                pre = A[i];
                cnt = 1;
                len++;
            }
        }
        return len;
    }

    private static void delete(int[] A, int i) {
        for (int j = i; j < A.length - 1; j++) {
            A[j] = A[j + 1];
        }
    }


    public int[][] generateMatrix(int n) {
        int[][] matrix = new int[n][n];
        int idx = 1;
        for (int layer = 0; layer <= n / 2; layer++) {
            int first = layer, last = n - 1 - layer;
            if (first == last) {
                matrix[first][last] = idx++;
                continue;
            }
            //top
            for (int i = first; i < last; i++)
                matrix[first][i] = idx++;
            //right
            for (int i = first; i < last; i++)
                matrix[i][last] = idx++;
            //bottom
            for (int i = last; i > first; i--)
                matrix[last][i] = idx++;
            //left
            for (int i = last; i > first; i--)
                matrix[i][first] = idx++;
        }
        return matrix;
    }

    public int trap(int[] A) {
        if (A == null || A.length == 0) return 0;
        int[] left = new int[A.length];
        left[0] = 0;
        int leftmostHigh = 0;
        for (int i = 0; i < A.length; i++) {
            left[i] = leftmostHigh;
            if (A[i] > leftmostHigh)
                leftmostHigh = A[i];
        }
        int sum = 0;
        int rightMostHigh = 0;
        for (int i = A.length - 1; i >= 0; i--) {
            int high = Math.min(left[i], rightMostHigh);
            if (high > A[i]) {
                sum += high - A[i];
            }
            if (A[i] > rightMostHigh)
                rightMostHigh = A[i];
        }
        return sum;
    }


    public static void main(String[] args) {
        Inorder test = new Inorder();
        int[] a = {1, 2, 2};
        ArrayList<ArrayList<Integer>> t = test.subsets(a);
        System.out.println(t);
    }

}
