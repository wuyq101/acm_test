package com.poj.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by wuyq on 15/10/4.
 */
public class Main2975 {
    private static int n;
    private static int[] k = new int[1000];

    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = cin.readLine();
            n = Integer.parseInt(line);
            if (n == 0) {
                return;
            }
            String[] strs = cin.readLine().split(" ");
            for (int i = 0; i < n; i++) {
                k[i] = Integer.parseInt(strs[i]);
            }
            solve();
        }
    }

    private static void solve() {
        //先对所有的ki求异或，判断是否为奇异局势（必败局）
        int r = 0;
        for (int i = 0; i < n; i++) {
            r ^= k[i];
        }
        if (r == 0) {
            System.out.println(0);
            return;
        }
        //如果所有异或不为0，是必胜局,
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if ((k[i] ^ r) < k[i]) {
                //说明k[i]中最后r中有1的位置，也都有1，改变k[i]，可以最终将所有异或的和变为0
                cnt++;
            }
        }
        System.out.println(cnt);
    }
}
