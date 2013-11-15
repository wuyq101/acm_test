package com.leetcode;

/**
 * Created by Administrator on 13-11-14.
 */
public class Candy {
    public int candy(int[] ratings) {
        if (ratings.length == 0 || ratings.length == 1)
            return ratings.length;
        int[] c = new int[ratings.length];
        int len = ratings.length;
        if (ratings[0] <= ratings[1]) {
            c[0] = 1;
        }
        if (ratings[len - 1] <= ratings[len - 2]) {
            c[len - 1] = 1;
        }
        for (int i = 1; i <= len - 2; i++) {
            if (ratings[i] <= ratings[i - 1] && ratings[i] <= ratings[i + 1]) {
                c[i] = 1;
            }
        }
        for (int i = 0; i < len; i++) {
            if (c[i] == 1) {
                int j = i + 1;
                while (j < len && ratings[j] > ratings[j - 1]) {
                    c[j] = Math.max(c[j - 1] + 1, c[j]);
                    j++;
                }
            }
        }
        for (int i = len - 1; i >= 0; i--) {
            if (c[i] == 1) {
                int j = i - 1;
                while (j >= 0 && ratings[j] > ratings[j + 1]) {
                    c[j] = Math.max(c[j + 1] + 1, c[j]);
                    j--;
                }
            }
        }
        int sum = 0;
        for (int i = 0; i < len; i++)
            sum += c[i];
        return sum;
    }

    public static void main(String[] args) {
        int[] ratings = {1, 0, 2};
        Candy test = new Candy();
        System.out.println(test.candy(ratings));
    }
}
