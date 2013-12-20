package com.leetcode;

/**
 * Created by Administrator on 13-11-26.
 */
public class RemoveList {
    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }


    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode slow = head, fast = head;
        for (int i = 1; i < n; i++)
            fast = fast.next;
        ListNode pre = null;
        while (fast.next != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next;
        }
        //remove slow;
        if (pre != null) {
            pre.next = slow.next;
            return head;
        } else
            return head.next;
    }


    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};
        ListNode head = makeList(nums);
        RemoveList t = new RemoveList();
        head = t.removeNthFromEnd(head, 5);
        print(head);
    }

    private static void print(ListNode head) {
        while (head != null) {
            System.out.println(head.val);
            head = head.next;
        }
    }

    private static ListNode makeList(int[] nums) {
        ListNode head = new ListNode(nums[0]);
        ListNode cur = head;
        for (int i = 1; i < nums.length; i++) {
            ListNode node = new ListNode(nums[i]);
            cur.next = node;
            cur = cur.next;
        }
        return head;
    }

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode slow = head, fast = head;
        ListNode pre = null;
        while (fast != null && fast.next != null) {
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        pre.next = null;
        head = sortList(head);
        slow = sortList(slow);
        return merge(head, slow);
    }

    private ListNode merge(ListNode a, ListNode b) {
        if (a == null) return b;
        if (b == null) return a;
        ListNode head = null;
        if (a.val <= b.val) {
            head = a;
            a = a.next;
        } else {
            head = b;
            b = b.next;
        }
        ListNode cur = head;
        while (a != null && b != null) {
            if (a.val <= b.val) {
                cur.next = a;
                a = a.next;
            } else {
                cur.next = b;
                b = b.next;
            }
            cur = cur.next;
        }
        if (a != null)
            cur.next = a;
        if (b != null)
            cur.next = b;
        return head;
    }
}
