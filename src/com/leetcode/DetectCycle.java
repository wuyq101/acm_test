package com.leetcode;

/**
 * Created by Administrator on 13-11-13.
 */
public class DetectCycle {
    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode detectCycle(ListNode head) {
        if (head == null) return null;
        ListNode slow = head;
        ListNode fast = head;
        do {
            if (slow == null || fast == null)
                return null;
            slow = slow.next;
            fast = fast.next;
            if (fast == null)
                return null;
            fast = fast.next;
        } while (slow != fast);
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}
