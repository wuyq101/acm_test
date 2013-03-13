package com.poj.binary_indexed_tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * <pre>
 *     http://poj.org/problem?id=2352
 *     树状数组的应用
 * </pre>
 * User: wuyq101
 * Date: 13-3-7
 * Time: 上午11:18
 */
public class Main2352 {
    private static int n, x, y, MAX = 32001;
    private static int[] tree = new int[32002], level = new int[15000];


    public static void main(String[] args) throws IOException {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(cin.readLine());
        n = Integer.parseInt(st.nextToken());
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(cin.readLine());
            x = Integer.parseInt(st.nextToken()) + 1;
            y = Integer.parseInt(st.nextToken());
            level[read(x)]++;
            update(x, 1);
        }
        for (int i = 0; i < n; i++) {
            sb.append(level[i]).append('\n');
        }
        System.out.printf(sb.toString());
    }

    private static void update(int x, int v) {
        for (int i = x; i <= MAX; i += i & -i)
            tree[i] += v;
    }

    private static int read(int x) {
        int sum = 0;
        for (int i = x; i > 0; i -= i & -i)
            sum += tree[i];
        return sum;
    }
}
