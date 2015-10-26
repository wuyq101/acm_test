package com.poj.greedy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * http://poj.org/problem?id=3069
 * Created by wuyq on 15/10/25.
 */
public class Main3069 {
    private static int R;
    private static int n;
    private static int[] p = new int[1000];

    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String[] strs = cin.readLine().split(" ");
            R = Integer.parseInt(strs[0]);
            n = Integer.parseInt(strs[1]);
            if (R == -1 && n == -1) break;
            strs = cin.readLine().split(" ");
            for (int i = 0; i < n; i++) {
                p[i] = Integer.parseInt(strs[i]);
            }
            solve();
        }
    }

    private static void solve() {
        //sort
        Arrays.sort(p, 0, n);
        int cnt = 0;
        int i = 0;
        while (i < n) {
            int left = p[i++];
            while (i < n && p[i] <= left + R) i++;
            int mid = p[i - 1];
            while (i < n && p[i] <= mid + R) i++;
            cnt++;
        }
        System.out.println(cnt);
    }
}
