package com.leetcode;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Administrator on 13-11-26.
 */
public class MergeList {
    public int removeDuplicates(int[] A) {
        if (A == null || A.length == 0) return 0;
        int idx = 0;
        int i = 1;
        while (i < A.length) {
            if (A[i] == A[idx]) {
                i++;
                continue;
            } else {
                A[++idx] = A[i++];
            }
        }
        return idx + 1;
    }

    public int removeElement(int[] A, int elem) {
        if (A == null || A.length == 0) return 0;
        int idx = -1;
        int i = 0;
        while (i < A.length) {
            if (A[i] == elem) {
                i++;
                continue;
            } else {
                A[++idx] = A[i++];
            }
        }
        return idx + 1;
    }


    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode swapPairs(ListNode head) {
        if (head == null)
            return null;
        ListNode s = head;
        if (head.next != null)
            head = head.next;
        ListNode tmp, pre = null;
        while (s != null && s.next != null) {
            tmp = s.next.next;
            if (pre != null)
                pre.next = s.next;
            s.next.next = s;
            s.next = tmp;
            pre = s;
            s = tmp;
        }
        return head;
    }

    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (head == null || m == n) return head;
        ListNode start = head;
        ListNode prev = null;
        for (int i = 1; i < m; i++) {
            prev = start;
            start = start.next;
        }
        ListNode before = prev;
        ListNode cur = start;
        prev = null;
        ListNode next;
        for (int i = m; i <= n; i++) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        start.next = cur;
        if (before != null)
            before.next = prev;
        else
            head = prev;
        return head;
    }

    public ListNode partition(ListNode head, int x) {
        if (head == null) return head;
        ListNode small = null, big = null;
        ListNode big_head = null;
        ListNode cur = head;
        head = null;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = null;
            if (cur.val < x) {
                if (head == null) {
                    head = cur;
                    small = head;
                } else {
                    small.next = cur;
                    small = small.next;
                }
            } else {
                if (big_head == null) {
                    big_head = cur;
                    big = big_head;
                } else {
                    big.next = cur;
                    big = big.next;
                }
            }
            cur = next;
        }
        if (head == null) return big_head;
        small.next = big_head;
        return head;
    }


    private static void print(ListNode head) {
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    private static ListNode makelist(int[] a) {
        ListNode head = new ListNode(a[0]);
        ListNode cur = head;
        for (int i = 1; i < a.length; i++) {
            cur.next = new ListNode(a[i]);
            cur = cur.next;
        }
        return head;
    }

    private static Map<String, Boolean> map = new HashMap<String, Boolean>();

    public boolean isScramble(String s1, String s2) {
        if (s1 == null || s2 == null || s1.length() != s2.length()) return false;
        map.clear();
        return isScrambleHelper(s1, s2);
    }

    public boolean isScrambleHelper(String s1, String s2) {
        String key;
        if (s1.compareTo(s2) < 0)
            key = s1 + "," + s2;
        else
            key = s2 + "," + s1;
        if (map.containsKey(key)) {
            return map.get(key);
        }
        if (s1.length() == 1) return s1.equals(s2);
        for (int i = 1; i < s1.length(); i++) {
            String left = s1.substring(0, i);
            String right = s1.substring(i);
            String a = s2.substring(0, i);
            String b = s2.substring(i);
            if (isScrambleHelper(left, a) && isScrambleHelper(right, b)) {
                map.put(key, true);
                return true;
            }
            a = s2.substring(right.length());
            b = s2.substring(0, right.length());
            if (isScrambleHelper(left, a) && isScrambleHelper(right, b)) {
                map.put(key, true);
                return true;
            }
        }
        map.put(key, false);
        return false;
    }

    public int maximalRectangle(char[][] matrix) {
        int r = matrix.length;
        int c = r == 0 ? 0 : matrix[0].length;
        if (r == 0 || c == 0) return 0;
        int[] height = new int[c];
        int best = Integer.MIN_VALUE;
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                if (matrix[i][j] == '0') {
                    height[j] = 0;
                } else {
                    height[j] += 1;
                }
            }
            int area = largestRectangleArea(height);
            if (area > best)
                best = area;
        }
        return best;
    }

    public int largestRectangleArea(int[] height) {
        Stack<Integer> num = new Stack<Integer>();
        Stack<Integer> idx = new Stack<Integer>();
        int area = 0;
        for (int i = 0; i < height.length; i++) {
            if (num.isEmpty() || height[i] > num.peek()) {
                num.push(height[i]);
                idx.push(i);
                continue;
            }
            if (height[i] < num.peek()) {
                int last = i;
                while (!num.isEmpty() && num.peek() > height[i]) {
                    int temp = (i - idx.peek()) * num.peek();
                    if (temp > area) area = temp;
                    last = idx.pop();
                    num.pop();
                }
                num.push(height[i]);
                idx.push(last);
            }
        }
        int i = height.length;
        while (!num.isEmpty()) {
            int temp = (i - idx.peek()) * num.peek();
            if (temp > area) area = temp;
            num.pop();
            idx.pop();
        }
        return area;
    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) return null;
        ListNode cur = head;
        head = null;
        ListNode prev = null;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = null;
            if (next == null || (next != null && next.val != cur.val)) {
                if (head == null) {
                    head = cur;
                    prev = head;
                } else {
                    prev.next = cur;
                    prev = prev.next;
                }
                cur = next;
            } else {
                //need to delete the cur and next...
                while (next.next != null && next.next.val == cur.val)
                    next = next.next;
                cur = next.next;
            }
        }
        return head;
    }

    public boolean exist(char[][] board, String word) {
        if (board == null || board.length == 0) return false;
        int m = board.length, n = board[0].length;
        if (n == 0 || word == null || word.length() == 0) return false;
        if (m * n < word.length()) return false;
        boolean[][] used = new boolean[m][n];
        char[] ch = word.toCharArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, ch, used, i, j, 0))
                    return true;
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, char[] ch, boolean[][] used, int r, int c, int i) {
        if (i == ch.length) return true;
        if (r < 0 || r >= board.length) return false;
        if (c < 0 || c >= board[0].length) return false;
        if (used[r][c])
            return false;
        if (!used[r][c] && board[r][c] == ch[i]) {
            used[r][c] = true;
            for (int k = 0; k < 4; k++) {
                if (dfs(board, ch, used, r + dr[k], c + dc[k], i + 1))
                    return true;
            }
            used[r][c] = false;
        }
        return false;
    }

    private static int[] dr = {-1, 0, 1, 0};
    private static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) {
        MergeList t = new MergeList();
        char[][] board = new char[][]{{'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}};
        System.out.println(t.exist(board, "ABCB"));
    }

}
