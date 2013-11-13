package com.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Stack;

/**
 * Created by Administrator on 13-11-13.
 */
public class PostorderTraversal {
    public ArrayList<Integer> postorderTraversal(TreeNode root) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        HashSet<TreeNode> visited = new HashSet<TreeNode>();
        if(root!=null)
            stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();
            if (node.left != null && !visited.contains(node.left)) {
                stack.push(node.left);
                continue;
            }
            if (node.right != null && !visited.contains(node.right)) {
                stack.push(node.right);
                continue;
            }
            list.add(node.val);
            stack.pop();
            visited.add(node);
        }
        return list;
    }

    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
}
