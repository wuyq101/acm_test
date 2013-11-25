package com.leetcode;

import java.util.ArrayList;

/**
 * Created by Administrator on 13-11-22.
 */
public class PathSum {
    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int x) {
            val = x;
        }
    }

    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) return false;
        if (root.left == null && root.right == null) {
            return root.val - sum == 0;
        }
        if (root.left != null) {
            if (hasPathSum(root.left, sum - root.val))
                return true;
        }
        if (root.right != null) {
            if (hasPathSum(root.right, sum - root.val))
                return true;
        }
        return false;
    }

    public ArrayList<ArrayList<Integer>> pathSum(TreeNode root, int sum) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
        pathSum(list, new ArrayList<Integer>(), root, sum);
        return list;
    }

    private void pathSum(ArrayList<ArrayList<Integer>> list, ArrayList<Integer> pre, TreeNode root, int sum) {
        if (root == null) return;
        //leaf node
        if (root.left == null && root.right == null) {
            if (root.val - sum == 0) {
                pre.add(root.val);
                list.add((ArrayList<Integer>) pre.clone());
                pre.remove(pre.size() - 1);
                return;
            }
        }
        pre.add(root.val);
        if (root.left != null) {
            pathSum(list, pre, root.left, sum - root.val);
        }
        if (root.right != null) {
            pathSum(list, pre, root.right, sum - root.val);
        }
        pre.remove(pre.size() - 1);
    }
}
