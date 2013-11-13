package com.leetcode;

import java.util.ArrayList;

/**
 * Created by Administrator on 13-11-13.
 */
public class PreorderTraversal {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public ArrayList<Integer> preorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (root != null) {
            list.add(root.val);
            if (root.left != null)
                list.addAll(preorderTraversal(root.left));
            if (root.right != null)
                list.addAll(preorderTraversal(root.right));
        }
        return list;
    }
}