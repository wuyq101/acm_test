package com.poj.sort;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * http://poj.org/problem?id=2379
 * User: wuyq101
 * Date: 13-3-14
 * Time: 下午5:43
 */
public class Main2379 {
    private static class Submit {
        int c, p, t, r;
    }

    private static int c, n;
    private static int[][] time = new int[1001][21], ac = new int[1001][21];
    private static int[] team_ac = new int[1001], team_time = new int[1001], sorted = new int[1001];
    private static Submit[] submits = new Submit[1000];


    public static void main(String[] args) throws IOException {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(cin.readLine());
        c = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(cin.readLine());
            submits[i] = new Submit();
            submits[i].c = Integer.parseInt(st.nextToken());
            submits[i].p = Integer.parseInt(st.nextToken());
            submits[i].t = Integer.parseInt(st.nextToken());
            submits[i].r = Integer.parseInt(st.nextToken());
        }
        rank();
    }

    private static void rank() {
        Arrays.sort(submits, 0, n, new Comparator<Submit>() {
            @Override
            public int compare(Submit a, Submit b) {
                if (a.t < b.t) return -1;
                if (a.t > b.t) return 1;
                return 0;
            }
        });
        for (int i = 0; i < n; i++)
            submit(submits[i]);
        for (int i = 1; i <= c; i++) {
            sorted[i] = i;
            for (int p = 1; p <= 20; p++)
                if (ac[i][p] == 1) {
                    team_time[i] += time[i][p];
                    team_ac[i] += 1;
                }
        }
        quick_sort(1, c);
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= c; i++)
            sb.append(sorted[i]).append(' ');
        System.out.println(sb.substring(0, sb.length() - 1));
    }

    private static void quick_sort(int left, int right) {
        if (right <= left) return;
        int pivot = (left + right) / 2;
        pivot = partition(left, right, pivot);
        quick_sort(left, pivot - 1);
        quick_sort(pivot + 1, right);
    }

    private static int partition(int left, int right, int pivot) {
        int v = sorted[pivot];
        swap(pivot, right);
        pivot = left;
        for (int i = left; i < right; i++) {
            if (team_ac[sorted[i]] > team_ac[v] || (team_ac[sorted[i]] == team_ac[v] && team_time[sorted[i]] < team_time[v]) ||
                    (team_ac[sorted[i]] == team_ac[v] && team_time[sorted[i]] == team_time[v] && sorted[i] < v))
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

    private static void submit(Submit s) {
        if (ac[s.c][s.p] == 1)
            return;
        time[s.c][s.p] += s.r == 0 ? 1200 : s.t;
        ac[s.c][s.p] += s.r;
    }
}
