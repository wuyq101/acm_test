package com.leetcode;

/**
 * Created by Administrator on 13-11-26.
 */
public class MergeList {
    public int removeDuplicates(int[] A) {
        if (A == null || A.length == 0) return 0;
        int idx = 0;
        int i = 1;
        while (i < A.length) {
            if (A[i] == A[idx]) {
                i++;
                continue;
            } else {
                A[++idx] = A[i++];
            }
        }
        return idx + 1;
    }

    public int removeElement(int[] A, int elem) {
        if (A == null || A.length == 0) return 0;
        int idx = -1;
        int i = 0;
        while (i < A.length) {
            if (A[i] == elem) {
                i++;
                continue;
            } else {
                A[++idx] = A[i++];
            }
        }
        return idx + 1;
    }


    private static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode swapPairs(ListNode head) {
        if (head == null)
            return null;
        ListNode s = head;
        if (head.next != null)
            head = head.next;
        ListNode tmp, pre = null;
        while (s != null && s.next != null) {
            tmp = s.next.next;
            if (pre != null)
                pre.next = s.next;
            s.next.next = s;
            s.next = tmp;
            pre = s;
            s = tmp;
        }
        return head;
    }

    public static void main(String[] args) {
        int[] num = new int[]{1, 2};
        MergeList t = new MergeList();
        t.removeDuplicates(num);
    }

}
