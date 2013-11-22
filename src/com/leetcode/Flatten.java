package com.leetcode;

/**
 * Created by Administrator on 13-11-19.
 */
public class Flatten {
    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int x) {
            val = x;
        }
    }

    public void flatten(TreeNode root) {
        if (root == null) return;
        while (root != null) {
            if (root.left == null) {
                root = root.right;
                continue;
            }
            TreeNode left = root.left;
            TreeNode right = root.right;
            //find the right most in the left tree;
            TreeNode r = left;
            while (r != null && r.right != null)
                r = r.right;
            r.right = right;
            root.right = left;
            root.left = null;
            root = root.right;
        }
    }
}
