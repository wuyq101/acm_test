package com.leetcode;

/**
 * Created by Administrator on 13-11-20.
 */
public class ReverseInteger {
    public int reverse(int x) {
        boolean flag = x < 0;
        if (flag) x = -x;
        int div = 1;
        while (div * 10 <= x) div *= 10;
        int result = 0;
        int count = 1;
        while (x > 0) {
            if (x < 10) {
                result += x * count * div;
                x /= 10;
                continue;
            }
            result += ((x % 10) * div + x / div) * count;
            count *= 10;
            x = (x - (x / div) * div - x % 10) / 10;
            div /= 100;
        }
        if (flag) result = -result;
        return result;
    }

    public static void main(String[] args) {
        ReverseInteger test = new ReverseInteger();
        System.out.println(test.reverse(1008000121));
    }

}
