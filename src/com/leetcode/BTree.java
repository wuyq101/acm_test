package com.leetcode;

/**
 * Created by Administrator on 13-12-11.
 */
public class BTree {

    public int firstMissingPositive(int[] A) {
        if (A == null || A.length == 0) return 1;
        int n = A.length;
        int i = 0, j = 0;
        while (i < n) {
            //不在范围内，缩小数组n的范围
            if (A[i] <= 0 || A[i] > n) {
                swap(A, i, n - 1);
                n--;
                continue;
            }
            //第一个数字为1，第二个数字为2，这种情况，就直接找下一个
            if (A[i] == i + 1) {
                i++;
                j = i;
                continue;
            }
            //1,n之间的情况,对调之后，A[i]这个值就到了它正确的位置下次查找就可以跳过
            if (A[i] < i + 1 || A[A[i] - 1] == A[i]) {
                swap(A, i, n - 1);
                n--;
                continue;
            }
            swap(A, i, A[i] - 1);
        }
        return j + 1;
    }

    private void swap(int[] A, int i, int j) {
        int t = A[i];
        A[i] = A[j];
        A[j] = t;
    }


    public static void main(String[] args) {
        int[] s = {1, 1, 2, 2, 2, 2, 2, 3, 6};
        BTree test = new BTree();
        int t = test.firstMissingPositive(s);
        System.out.println(t);
    }
}
