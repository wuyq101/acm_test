package com.leetcode;

/**
 * Created by Administrator on 13-11-14.
 */
public class SingleNumberII {
    public int singleNumber(int[] A) {
        int len = A.length;
        int result = 0;
        for (int i = 0; i < 32; i++) {
            int mask = 1 << i;
            int sum = 0;
            for (int j = 0; j < len; j++) {
                sum += (A[j] & mask) == 0 ? 0 : 1;
            }
            sum %= 3;
            result += (sum << i);
        }
        return result;
    }

    public static void main(String[] args) {
        int[] A = {2, 1, 4, 5, 1, 4, 2, 2, 4, 1};
        SingleNumberII tester = new SingleNumberII();
        System.out.println(tester.singleNumber(A));
    }

}
