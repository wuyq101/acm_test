package com.poj.sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * User: wuyq101
 * Date: 13-3-14
 * Time: 下午1:27
 */
public class Main2092 {
    private static int n, m;
    private static int[] cnt = new int[10001], sorted = new int[10001];

    public static void main(String[] args) throws IOException {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        while (true) {
            st = new StringTokenizer(cin.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            if (n == 0 && m == 0) break;
            init();
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(cin.readLine());
                for (int j = 0; j < m; j++)
                    cnt[Integer.parseInt(st.nextToken())]++;
            }
            solve();
        }
    }

    private static void init() {
        for (int i = 1; i <= 10000; i++) {
            sorted[i] = i;
            cnt[i] = 0;
        }
    }

    private static void solve() {
        quick_sort(1, 10000);
        int max = cnt[sorted[1]], start = 1;
        while (cnt[sorted[start + 1]] == max)
            start = start + 1;
        StringBuilder sb = new StringBuilder();
        start = start + 1;
        max = cnt[sorted[start]];
        while (max == cnt[sorted[start]]) {
            sb.append(sorted[start]).append(' ');
            start++;
        }
        System.out.println(sb.substring(0, sb.length() - 1));
    }

    private static void quick_sort(int left, int right) {
        if (right <= left) return;
        int pivot = (left + right) / 2;
        pivot = partition(left, right, pivot);
        quick_sort(left, pivot - 1);
        if (cnt[sorted[pivot]] > 0)
            quick_sort(pivot + 1, right);
    }

    private static int partition(int left, int right, int pivot) {
        int v = cnt[sorted[pivot]], p = sorted[pivot];
        swap(pivot, right);
        pivot = left;
        for (int i = left; i < right; i++) {
            if (cnt[sorted[i]] > v || (v > 0 && cnt[sorted[i]] == v && sorted[i] < p))
                swap(pivot++, i);
        }
        swap(pivot, right);
        return pivot;
    }

    private static void swap(int i, int j) {
        int t = sorted[i];
        sorted[i] = sorted[j];
        sorted[j] = t;
    }
}
