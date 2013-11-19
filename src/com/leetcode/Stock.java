package com.leetcode;

/**
 * Created by Administrator on 13-11-18.
 */
public class Stock {
    public int maxProfit(int[] prices) {
        int profit = 0;
        for (int i = 0; i < prices.length; i++) {
            if (i > 0 && prices[i] <= prices[i - 1]) continue;
            //j-->i, i+1--->k
            int min = prices[0], p = 0;
            for (int j = 0; j <= i; j++) {
                if (prices[j] < min)
                    min = prices[j];
                if (prices[j] - min > p)
                    p = prices[j] - min;
            }
            int q = 0;
            min = prices[i];
            for (int j = i; j < prices.length; j++) {
                if (prices[j] < min)
                    min = prices[j];
                if (prices[j] - min > q)
                    q = prices[j] - min;
            }
            if (p + q > profit)
                profit = p + q;
        }
        return profit;
    }

    public static void main(String[] args) {
        int[] prices = {2, 1, 2, 0, 1};
        Stock test = new Stock();
        System.out.println(test.maxProfit(prices));
    }
}
