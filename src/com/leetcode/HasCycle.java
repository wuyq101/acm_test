package com.leetcode;

/**
 * Created by Administrator on 13-11-13.
 */
public class HasCycle {
    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public boolean hasCycle(ListNode head) {
        if (head == null)
            return false;
        ListNode slow = head;
        ListNode fast = head;
        do {
            if (slow == null || fast == null)
                return false;
            slow = slow.next;
            fast = fast.next;
            if (fast == null)
                return false;
            fast = fast.next;
        } while (slow != fast);
        return true;
    }
}
