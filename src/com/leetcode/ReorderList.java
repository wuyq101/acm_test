package com.leetcode;

/**
 * Created by Administrator on 13-11-13.
 */
public class ReorderList {
    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public void reorderList(ListNode head) {
        if (head == null)
            return;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        //slow is the list to reverse head
        ListNode reverseHead = slow.next;
        slow.next = null;
        //fast is the list to reverse tail
        reverseHead = reverse(reverseHead);
        merge(head, reverseHead);
    }

    private void merge(ListNode head1, ListNode head2) {
        while (head2 != null) {
            ListNode node1 = head1;
            head1 = head1.next;
            ListNode node2 = head2;
            head2 = head2.next;
            node2.next = node1.next;
            node1.next = node2;
        }
    }

    private ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }
}
