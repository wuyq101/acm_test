package com.poj.sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * http://poj.org/problem?id=2371
 * 桶排序
 * User: wuyq101
 * Date: 13-3-14
 * Time: 下午9:45
 */
public class Main2371 {
    private static int[] cnt = new int[5001];

    public static void main(String[] args) throws IOException {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(cin.readLine());
        int n = Integer.parseInt(st.nextToken());
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(cin.readLine());
            cnt[Integer.parseInt(st.nextToken())]++;
        }
        for (int i = 1; i <= 5000; i++)
            cnt[i] = cnt[i - 1] + cnt[i];
        cin.readLine();
        st = new StringTokenizer(cin.readLine());
        int k = Integer.parseInt(st.nextToken());
        for (int i = 0; i < k; i++) {
            st = new StringTokenizer(cin.readLine());
            query(Integer.parseInt(st.nextToken()));
        }
    }

    private static void query(int i) {
        for (int j = 1; j <= 5000; j++) {
            if (cnt[j - 1] < i && i <= cnt[j]) {
                System.out.printf("%d\n", j);
                return;
            }
        }
    }
}