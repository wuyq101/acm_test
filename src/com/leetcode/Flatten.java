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

    public void recoverTree(TreeNode root) {
        if (root == null) return;
        first = null;
        second = null;
        pre = null;
        check(root);
        int t = first.val;
        first.val = second.val;
        second.val = t;
    }

    private void check(TreeNode root) {
        if (root == null) return;
        check(root.left);
        if (pre != null && pre.val > root.val) {
            if (first == null) {
                first = pre;
            }
            second = root;
        }
        pre = root;
        check(root.right);
    }

    private static TreeNode first, second;

    private static int cnt = 0;
    private static TreeNode result;

    public TreeNode findKth(TreeNode root, int k) {
        cnt = 0;
        result = null;
        findKthHelp(root, k);
        return result;
    }

    private void findKthHelp(TreeNode root, int k) {
        if (root == null) return;
        findKthHelp(root.left, k);
        cnt++;
        if (cnt == k) {
            result = root;
            return;
        }
        if (cnt > k) return;
        findKthHelp(root.right, k);
    }

    private static TreeNode pre, head;

    public void findPairs(TreeNode root, int k) {
        head = root;
        pre = null;
        findPairsHelp(root, k);
    }

    private void findPairsHelp(TreeNode root, int k) {
        if (root == null) return;
        findPairsHelp(root.left, k);
        if (pre != null) {
            int rest = k - pre.val;
            if (rest > pre.val && search(head, rest))
                System.out.printf("%d %d\n", pre.val, k - pre.val);
        }
        pre = root;
        findPairsHelp(root.right, k);
    }

    private boolean search(TreeNode root, int target) {
        if (root == null)
            return false;
        while (root != null) {
            if (root.val == target) return true;
            if (root.val < target)
                root = root.right;
            else
                root = root.left;
        }
        return false;
    }

    public static void main(String[] args) {
        Flatten t = new Flatten();
        TreeNode root = new TreeNode(20);
        root.left = new TreeNode(8);
        root.right = new TreeNode(22);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(12);
        root.left.right.left = new TreeNode(10);
        root.left.right.right = new TreeNode(14);
//        t.findPairs(root, 24);
        int[] a = new int[]{20, 8, 22, 4, 12, 10, 14};
        for (int i = 0; i < a.length; i++) {
            t.bstEncode(a, i);
            System.out.println("------" + a[i]);
        }
//        for (int i = 1; i <= 7; i++) {
//            TreeNode node = t.findKth(root, i);
//            System.out.println(node.val);
//        }
    }

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int[] left = new int[n];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            left[i] = gas[i] - cost[i];
            sum += left[i];
        }
        if (sum < 0) return -1;
        int loop = 0;
        int start = 0;
        sum = 0;
        for (int i = 0; true; i = (i + 1) % n) {
            if (sum + left[i] >= 0) {
                sum += left[i];
                if ((i + 1) % n == start)
                    return start;
            } else {
                sum = 0;
                start = (i + 1) % n;
                if (start == loop)
                    return -1;
            }
        }
    }

    public int sqrt(int x) {
        if (x <= 0) return 0;
        int left = 1, right = x / 2, mid;
        int root = 1;
        while (left <= right) {
            mid = left + (right - left) / 2;
            long p = ((long) mid) * ((long) mid);
            if (p <= x) {
                left = mid + 1;
                root = mid;
            } else {
                right = mid - 1;
            }
        }
        return root;
    }

    public void bstEncode(int[] a, int x) {
        int min = Integer.MIN_VALUE, max = Integer.MAX_VALUE;
        int t = a[x];
        for (int i = 0; i <= x; i++) {
            if (a[i] > min && a[i] < max) {
                if (a[i] < t) {
                    System.out.print(1);
                    min = a[i];
                }
                if (a[i] > t) {
                    System.out.print(0);
                    max = a[i];
                }
            }
        }
    }

}
