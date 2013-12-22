package com.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 13-12-21.
 */
public class UniqueBST {
    private static class TreeNode {
        int val;
        TreeNode left, right;

        TreeNode(int x) {
            val = x;
        }
    }

    public ArrayList<TreeNode> generateTrees(int n) {
        if (n <= 0) {
            ArrayList<TreeNode> list = new ArrayList<TreeNode>();
            list.add(null);
            return list;
        }
        return generateTrees(1, n);
    }

    private static Map<String, ArrayList<TreeNode>> map = new HashMap<String, ArrayList<TreeNode>>();

    public ArrayList<TreeNode> generateTrees(int min, int max) {
        String key = min + "," + max;
        if (map.containsKey(key))
            return map.get(key);
        ArrayList<TreeNode> list = new ArrayList<TreeNode>();
        if (min > max) return list;
        for (int i = min; i <= max; i++) {
            //i as root
            ArrayList<TreeNode> left_list = generateTrees(min, i - 1);
            ArrayList<TreeNode> right_list = generateTrees(i + 1, max);
            if (left_list.size() > 0 && right_list.size() > 0) {
                for (TreeNode left : left_list) {
                    for (TreeNode right : right_list) {
                        TreeNode root = new TreeNode(i);
                        root.left = left;
                        root.right = right;
                        list.add(root);
                    }
                }
                continue;
            }
            if (left_list.size() > 0) {
                for (TreeNode left : left_list) {
                    TreeNode root = new TreeNode(i);
                    root.left = left;
                    list.add(root);
                }
                continue;
            }
            if (right_list.size() > 0) {
                for (TreeNode right : right_list) {
                    TreeNode root = new TreeNode(i);
                    root.right = right;
                    list.add(root);
                }
                continue;
            }
            TreeNode root = new TreeNode(i);
            list.add(root);
        }
        map.put(key, list);
        return list;
    }

    public int[] searchRange(int[] A, int target) {
        int[] result = {-1, -1};
        if (A == null || A.length == 0) return result;
        int left = 0, right = A.length - 1;
        int mid = 0;
        boolean found = false;
        while (left <= right) {
            mid = left + (right - left) / 2;
            if (A[mid] == target) {
                found = true;
                break;
            }
            if (A[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        if (!found) return result;
        left = 0;
        right = mid;
        while (left <= right) {
            int t = (left + right) / 2;
            if (A[t] == A[mid]) {
                result[0] = t;
                right = t - 1;
            } else {
                left = t + 1;
            }
        }
        left = mid;
        right = A.length - 1;
        while (left <= right) {
            int t = (left + right) / 2;
            if (A[t] == A[mid]) {
                result[1] = t;
                left = t + 1;
            } else {
                right = t - 1;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        UniqueBST test = new UniqueBST();
        ArrayList<TreeNode> list = test.generateTrees(3);
        System.out.println(list.size());
    }
}
