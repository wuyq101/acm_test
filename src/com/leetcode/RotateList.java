package com.leetcode;

import java.util.Stack;

/**
 * Created by Administrator on 13-12-22.
 */
public class RotateList {
    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || k == 0) return head;
        int len = length(head);
        if (k % len == 0) return head;
        k = k % len;
        ListNode kth = head;
        for (int i = 1; i < k; i++) {
            kth = kth.next;
        }
        ListNode cur = head;
        ListNode prev = null;
        while (kth != null && kth.next != null) {
            kth = kth.next;
            prev = cur;
            cur = cur.next;
        }
        if (prev != null)
            prev.next = null;
        if (kth != head)
            kth.next = head;
        head = cur;
        return head;
    }

    private int length(ListNode head) {
        int len = 0;
        while (head != null) {
            len++;
            head = head.next;
        }
        return len;
    }

    public int longestValidParentheses(String s) {
        if (s == null || s.length() == 0) return 0;
        int len = 0;
        Stack<Character> stack = new Stack<Character>();
        Stack<Integer> idx = new Stack<Integer>();
        stack.push(')');
        idx.push(-1);
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                stack.push('(');
                idx.push(i);
                continue;
            }
            if (stack.peek() == '(') {
                stack.pop();
                idx.pop();
                int temp = i - idx.peek();
                if (temp > len) len = temp;
                continue;
            }
            stack.push(')');
            idx.push(i);
        }
        return len;
    }

    public static void main(String[] args) {
        ListNode head = new ListNode(1);
        head.next = new ListNode(2);
        RotateList test = new RotateList();
        System.out.println(test.longestValidParentheses("()()"));
    }

}
