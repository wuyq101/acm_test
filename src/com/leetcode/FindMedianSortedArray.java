package com.leetcode;

/**
 * Created by Administrator on 13-11-12.
 */
public class FindMedianSortedArray {
    public double findMedianSortedArrays(int[] A, int[] B) {
        // IMPORTANT: Please reset any member data you declared, as
        // the same Solution instance will be reused for each test case.
        int[] sorted = new int[A.length + B.length];
        int i = 0, j = 0, idx = 0;
        int mid = (A.length + B.length) / 2;
        while (i < A.length && j < B.length) {
            if (A[i] < B[j]) {
                sorted[idx++] = A[i++];
            } else
                sorted[idx++] = B[j++];
            if (idx > mid) break;
        }
        if (i < A.length) {
            for (int k = i; k < A.length; k++) {
                sorted[idx++] = A[k];
                if (idx > mid) break;
            }
        }
        if (j < B.length) {
            for (int k = j; k < B.length; k++) {
                sorted[idx++] = B[k];
                if (idx > mid) break;
            }
        }
        if((A.length+B.length)%2==1){
            return sorted[mid];
        }else {
            return (sorted[mid-1]+sorted[mid])/2.0;
        }
    }

    public static void main(String[] args) {
        int[] A = {1, 2, 3, 4};
        int[] B = {6, 7, 8, 9, 10};
        FindMedianSortedArray tester = new FindMedianSortedArray();
        double result = tester.findMedianSortedArrays(A, B);
        System.out.println(result);
    }
}
