package com.leetcode;

/**
 * Created by Administrator on 13-11-12.
 */
public class AddTwoNumbers {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // IMPORTANT: Please reset any member data you declared, as
        // the same Solution instance will be reused for each test case.
        ListNode result = new ListNode(0);
        ListNode cur = result;
        while (l1 != null && l2 != null) {
            int sum = l1.val + l2.val + cur.val;
            l1 = l1.next;
            l2 = l2.next;
            if (sum < 10) {
                cur.val = sum;
            } else {
                cur.val = sum - 10;
            }
            if (sum >= 10 || l1 != null || l2 != null) {
                cur.next = new ListNode(0);
                if (sum >= 10)
                    cur.next.val = 1;
            }
            cur = cur.next;
        }
        while (l1 != null) {
            int sum = l1.val + cur.val;
            l1 = l1.next;
            cur.val = sum >= 10 ? sum - 10 : sum;
            if (sum >= 10 || l1 != null) {
                cur.next = new ListNode(0);
                if (sum >= 10)
                    cur.next.val = 1;
            }
            cur = cur.next;
        }
        while (l2 != null) {
            int sum = l2.val + cur.val;
            l2 = l2.next;
            cur.val = sum >= 10 ? sum - 10 : sum;
            if (sum >= 10 || l2 != null) {
                cur.next = new ListNode(0);
                if (sum >= 10)
                    cur.next.val = 1;
            }
            cur = cur.next;
        }
        return result;
    }

    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }
}




