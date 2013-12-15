package com.leetcode;

/**
 * Created by Administrator on 13-12-11.
 */
public class MList {
    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) return null;
        if (l1 == null || l2 == null) return l1 == null ? l2 : l1;
        ListNode head = null;
        ListNode cur = null, next = null;
        while (l1 != null && l2 != null) {
            next = l1.val <= l2.val ? l1 : l2;
            if (head == null) {
                head = next;
                cur = head;
            } else {
                cur.next = next;
                cur = cur.next;
            }
            if (l1 == next) l1 = l1.next;
            if (l2 == next) l2 = l2.next;
        }
        if (l1 != null) cur.next = l1;
        if (l2 != null) cur.next = l2;
        return head;
    }
}