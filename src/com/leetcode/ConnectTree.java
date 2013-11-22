package com.leetcode;

/**
 * Created by Administrator on 13-11-19.
 */
public class ConnectTree {
    private static class TreeLinkNode {
        int val;
        TreeLinkNode left, right, next;

        TreeLinkNode(int x) {
            val = x;
        }
    }

    public void connect(TreeLinkNode root) {
        if (root == null) return;
        TreeLinkNode left = root.left;
        TreeLinkNode right = root.right;
        TreeLinkNode next = root.next;
        if (left != null) {
            if (right != null) {
                left.next = right;
            } else {
                if (next != null) {
                    while (next != null && next.left == null && next.right == null)
                        next = next.next;
                    if (next != null)
                        left.next = next.left != null ? next.left : next.right;
                }
            }
        }
        if (right != null) {
            if (next != null) {
                while (next != null && next.left == null && next.right == null)
                    next = next.next;
                if (next != null)
                    right.next = next.left != null ? next.left : next.right;
            }
        }
        connect(right);
        connect(left);
    }

    public static void main(String[] args) {
        TreeLinkNode node = new TreeLinkNode(2);
        node.left = new TreeLinkNode(1);
        node.right = new TreeLinkNode(3);
        node.left.left = new TreeLinkNode(0);
        node.left.right = new TreeLinkNode(7);
        node.right.left = new TreeLinkNode(9);
        node.right.right = new TreeLinkNode(1);
        node.left.left.left = new TreeLinkNode(2);
        node.left.right.left = new TreeLinkNode(1);
        node.left.right.right = new TreeLinkNode(0);
        node.left.right.right.left = new TreeLinkNode(7);
        node.right.right.left = new TreeLinkNode(8);
        node.right.right.right = new TreeLinkNode(8);
        ConnectTree test = new ConnectTree();
        test.connect(node);
    }

}
