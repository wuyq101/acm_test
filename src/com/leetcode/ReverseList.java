package com.leetcode;

/**
 * Created by Administrator on 13-11-27.
 */
public class ReverseList {
    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null)
            return null;
        ListNode start = null;
        ListNode last = null;
        ListNode next = nextKGroup(head, k);
        while (next != null) {
            if (start == null) {
                start = reverse(head);
                last = start;
                while (last.next != null)
                    last = last.next;
            } else {
                last.next = reverse(head);
                while (last.next != null)
                    last = last.next;
            }
            head = next;
            next = nextKGroup(next, k);
        }
        if (head != null) {
            int count = 0;
            ListNode cur = head;
            ListNode pre = null;
            while (cur != null && count < k) {
                pre = cur;
                cur = cur.next;
                count++;
            }
            if (count == k) {
                if (start == null)
                    start = reverse(head);
                else
                    last.next = reverse(head);
            } else {
                if (start == null)
                    start = head;
                else
                    last.next = head;
            }
        }
        return start;
    }

    private ListNode reverse(ListNode head) {
        ListNode pre = null, cur = head;
        while (cur != null) {
            ListNode tmp = cur.next;
            cur.next = pre;
            pre = cur;
            cur = tmp;
        }
        return pre;
    }

    private ListNode nextKGroup(ListNode head, int k) {
        int count = 0;
        ListNode cur = head;
        ListNode pre = null;
        while (cur != null && count < k) {
            pre = cur;
            cur = cur.next;
            count++;
        }
        if (count == k) {
            pre.next = null;
        } else
            return null;
        return cur;
    }

    public static void main(String[] args) {
        ListNode list = makeList(new int[]{1, 2, 3, 4, 5, 6});
        ReverseList t = new ReverseList();
        ListNode head = t.reverseKGroup(list,3);
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    private static ListNode makeList(int[] n) {
        ListNode head = new ListNode(n[0]);
        ListNode cur = head;
        for (int i = 1; i < n.length; i++) {
            cur.next = new ListNode(n[i]);
            cur = cur.next;
        }
        return head;
    }
}
