package com.leetcode;

/**
 * Created by Administrator on 13-11-19.
 */
public class SortList {
    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode cur = head.next;
        head.next = null;
        ListNode tmp;
        while (cur != null) {
            tmp = cur.next;
            cur.next = null;
            head = insertNode(head, cur);
            cur = tmp;
        }
        return head;
    }

    private ListNode insertNode(ListNode head, ListNode node) {
        if (node.val <= head.val) {
            node.next = head;
            return node;
        }
        ListNode pre = null, cur = head;
        while (cur != null) {
            if (cur.val <= node.val) {
                ListNode tmp = cur.next;
                pre = cur;
                cur = tmp;
            } else {
                break;
            }
        }
        cur = pre.next;
        pre.next = node;
        node.next = cur;
        return head;
    }


    public static void main(String[] args) {
        ListNode head = makeList(new int[]{1, 4, 5, 3, 2, 6});
        SortList test = new SortList();
        ListNode list = test.insertionSortList(head);
        while (list != null) {
            System.out.println(list.val);
            list = list.next;
        }
    }

    private static ListNode makeList(int[] num) {
        ListNode head = new ListNode(0);
        ListNode cur = head;
        for (int i = 0; i < num.length; i++) {
            ListNode node = new ListNode(num[i]);
            cur.next = node;
            cur = cur.next;
        }
        return head.next;
    }


}
