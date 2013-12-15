package com.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 13-12-9.
 */
public class MinDepth {
    private static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;
        int left = root.left != null ? 1 + minDepth(root.left) : Integer.MAX_VALUE;
        int right = root.right != null ? 1 + minDepth(root.right) : Integer.MAX_VALUE;
        return Math.min(left, right);
    }

    public boolean isMatch(String s, String p) {
        if (s == null || p == null) return false;
        int n = s.length(), m = p.length();
        //star最后一个'*'的位置
        //sp当前开始匹配的位置
        int i = 0, j = 0, star = -1, sp = 0;
        while (i < n) {
            while (j < m && p.charAt(j) == '*') {
                star = j++;
                sp = i;
            }
            if (j == m || (p.charAt(j) != s.charAt(i) && p.charAt(j) != '?')) {
                if (star < 0) return false;
                //匹配串从最后一个星之后，开始，使用*去匹配当前不匹配的内容
                j = star + 1;
                //被匹配串往后移动一个
                i = ++sp;
            } else {
                i++;
                j++;
            }
        }
        while (j < m && p.charAt(j) == '*')
            j++;
        return j == m;
    }

    public static void main(String[] args) {
        MinDepth test = new MinDepth();
        String s = "11";
        System.out.println(test.numDecodings(s));
    }

    public int numDecodings(String s, Map<String, Integer> dp) {
        if (dp.containsKey(s))
            return dp.get(s);
        if (s.length() == 0) return 0;
        if (s.length() == 1) {
            return s.charAt(0) == '0' ? 0 : 1;
        }
        char ch = s.charAt(0);
        char next = s.charAt(1);
        if (s.length() == 2) {
            if (ch == '0') return 0;
            if (next == '0') return ch == '1' || ch == '2' ? 1 : 0;
            if (ch == '1' || (ch == '2' && next >= '1' && next <= '6')) return 2;
            return 1;
        }
        //长度大于2的
        if (ch == '0')
            return 0;
        if ((ch == '1' || ch == '2') && next == '0') {
            int r = numDecodings(s.substring(2), dp);
            dp.put(s, r);
            return r;
        }
        if (ch == '1' || (ch == '2' && next >= '1' && next <= '6')) {
            int r = numDecodings(s.substring(1), dp) + numDecodings(s.substring(2), dp);
            dp.put(s, r);
            return r;
        }
        int r = numDecodings(s.substring(1), dp);
        dp.put(s, r);
        return r;
    }

    public int numDecodings(String s) {
        if (s == null || s.length() == 0) return 0;
        Map<String, Integer> map = new HashMap<String, Integer>();
        return numDecodings(s, map);
    }
}
