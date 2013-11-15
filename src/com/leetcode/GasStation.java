package com.leetcode;

/**
 * Created by Administrator on 13-11-14.
 */
public class GasStation {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int n = gas.length;
        int[] left = new int[n];
        int sum = 0;
        for (int i = 0; i < n; i++) {
            left[i] = gas[i] - cost[i];
            sum += left[i];
        }
        if (sum < 0)
            return -1;
        for (int i = 0; i < n; i++) {
            if (left[i] < 0) continue;
            if (i > 0 && left[i - 1] >= 0) continue;
            sum = left[i];
            int j = (i + 1) % n;
            while (sum >= 0) {
                sum += left[j];
                if (sum >= 0 && j == i)
                    return i;
                j = (j + 1) % n;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] gas = {1, 2, 3, 4, 5};
        int[] cost = {5, 4, 3, 2, 1};
        GasStation test = new GasStation();
        System.out.println(test.canCompleteCircuit(gas, cost));
    }
}
