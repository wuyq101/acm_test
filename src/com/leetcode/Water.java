package com.leetcode;

/**
 * Created by Administrator on 13-11-25.
 */
public class Water {
    public int maxArea(int[] height) {
        int i = 0, j = height.length - 1;
        int max = 0, tmp;
        while (i < j) {
            tmp = (j - i) * Math.min(height[i], height[j]);
            if (tmp > max)
                max = tmp;
            if (height[i] < height[j]) {
                i++;
                continue;
            }
            if (height[i] > height[j]) {
                j--;
                continue;
            }
            i++;
            j--;
        }
        return max;
    }
}
