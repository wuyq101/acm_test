package com.leetcode;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Administrator on 13-12-14.
 */
public class Zigzag {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public boolean existPath(TreeNode root, int sum) {
        if (root == null) return false;
        // it is a leaf node
        if (root.left == null && root.right == null) {
            return sum == root.val;
        }
        sum -= root.val;
        return existPath(root.left, sum) || existPath(root.right, sum);
    }

    public int[] longestPath(TreeNode root) {
        if (root == null) {
            return new int[]{0, 0};
        }
        //first max height, second max path
        int[] left = longestPath(root.left);
        int[] right = longestPath(root.right);
        int[] result = new int[2];
        result[0] = Math.max(left[0], right[0]) + 1;
        result[1] = Math.max(Math.max(left[1], right[1]), left[0] + right[0]);
        return result;
    }

    public void build(TreeNode root, int[][] arr) {
        if (root == null) return;
        build(root.left, arr);
        build(root.right, arr);
        int val = root.val;
        if (root.left != null)
            arr[val][root.left.val] = 1;
        if (root.right != null)
            arr[val][root.right.val] = 1;
        for (int i = 1; i < arr.length; i++) {
            if (root.left != null && arr[root.left.val][i] == 1)
                arr[root.val][i] = 1;
            if (root.right != null && arr[root.right.val][i] == 1)
                arr[root.val][i] = 1;
        }
    }


    public boolean search(int[] A, int target) {
        return search(A, 0, A.length - 1, target);
    }

    private boolean search(int[] A, int left, int right, int target) {
        if (left > right) return false;
        if (left == right) return A[left] == target;
        int mid = left + (right - left) / 2;
        if (A[mid] == target) return true;
        int L = mid - 1;
        while (L - 1 >= left && A[L - 1] == A[L])
            L--;
        int R = mid + 1;
        while (R + 1 <= right && A[R + 1] == A[R])
            R++;
        return search(A, left, L, target) || search(A, R, right, target);
    }

    public ArrayList<ArrayList<Integer>> zigzagLevelOrder(TreeNode root) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        if (root == null) return list;
        Stack<TreeNode> current = new Stack<TreeNode>();
        Stack<TreeNode> next = new Stack<TreeNode>();
        Stack<TreeNode> tmp;
        boolean left2right = true;
        current.add(root);
        while (!current.isEmpty()) {
            ArrayList<Integer> layer = new ArrayList<Integer>(current.size());
            while (!current.isEmpty()) {
                TreeNode node = current.pop();
                layer.add(node.val);
                if (left2right) {
                    if (node.left != null)
                        next.push(node.left);
                    if (node.right != null)
                        next.push(node.right);
                } else {
                    if (node.right != null)
                        next.push(node.right);
                    if (node.left != null)
                        next.push(node.left);
                }
            }
            list.add(layer);
            left2right = !left2right;
            tmp = current;
            current = next;
            next = tmp;
        }
        return list;
    }

    public int countBitSet(int n) {
        if (n <= 0) return 0;
        int t = 0, m = 0;
        while (((t << 1) | 1) <= n) {
            m++;
            t = (t << 1) | 1;
        }
        int sum = m * (1 << (m - 1)) + n - t;
        if (n == t) return sum;
        return sum + countBitSet(n & ~(1 << m));
    }

    public static void main(String[] args) {
        Zigzag test = new Zigzag();
        System.out.println(test.search(new int[]{1, 3}, 3));
    }
}
