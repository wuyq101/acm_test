package com.leetcode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 13-12-20.
 */
public class SubSet {
    public ArrayList<ArrayList<Integer>> subsetsWithDup(int[] num) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>();
        if (num == null || num.length == 0) return lists;
        Arrays.sort(num);
        ArrayList<Integer> list = new ArrayList<Integer>();
        lists.add(list);
        for (int i = 0; i < num.length; i++) {
            int cnt = 0;
            int k = i - 1;
            while (k >= 0 && num[k] == num[i]) {
                cnt++;
                k--;
            }
            int size = lists.size();
            for (int j = 0; j < size; j++) {
                list = new ArrayList<Integer>(lists.get(j));
                if (cnt == 0) {
                    list.add(num[i]);
                    lists.add(list);
                } else {
                    //if cnt==2, mean there are 2 same value before
                    // so any set with 0,1, same value should not extend
                    int t = count(list, num[i]);
                    if (t < cnt) continue;
                    list.add(num[i]);
                    lists.add(list);
                }
            }
        }
        return lists;
    }

    private int count(ArrayList<Integer> list, int v) {
        int cnt = 0;
        for (int i : list) {
            if (i == v)
                cnt++;
        }
        return cnt;
    }

    public boolean canJump(int[] A) {
        if (A == null || A.length == 0) return false;
        if (A.length == 1) return true;
        int right = 0;
        for (int i = 0; i < A.length; i++) {
            if (right < i) return false;
            if (i + A[i] > right)
                right = i + A[i];
            if (right >= A.length - 1) return true;
        }
        return false;
    }

    public int lengthOfLastWord(String s) {
        if (s == null || s.length() == 0) return 0;
        int len = 0;
        int j = s.length() - 1;
        while (j >= 0 && s.charAt(j) == ' ')
            j--;
        while (j >= 0 && s.charAt(j) != ' ') {
            len++;
            j--;
        }
        return len;
    }


    public static void main(String[] args) {
        int[] a = {1, 2, 2, 2, 2, 2, 2, 2};
        SubSet test = new SubSet();
        ArrayList<ArrayList<Integer>> lists = test.subsetsWithDup(a);
        System.out.println(lists);
    }

}
