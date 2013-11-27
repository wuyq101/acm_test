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
        int count = 0;
        ListNode slow = head, fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            count++;
        }
        count = fast != null ? (count << 1) + 1 : count << 1;
        n = count - n;
        ListNode cur = head, pre = null;
        count = 0;
        while (count < n) {
            ListNode t = cur.next;
            pre = cur;
            cur = t;
            count++;
        }
        if (pre == null)
            return head.next;
        pre.next = pre.next.next;
        return head;
    }

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};
        ListNode head = makeList(nums);
        RemoveList t = new RemoveList();
        t.removeNthFromEnd(head, 5);
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
}
