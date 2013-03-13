package com.poj.binary_indexed_tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <pre>
 *     http://poj.org/problem?id=2155
 * </pre>
 * User: wuyq101
 * Date: 13-3-7
 * Time: 下午1:39
 */
public class Main2155 {
    private static int N, T;
    private static StringBuilder sb = new StringBuilder();
    private static int[][] tree = new int[1001][1001];

    public static void main(String[] args) throws IOException {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(cin.readLine());
        int t = Integer.parseInt(st.nextToken()), x, y, x1, x2, y1, y2;
        String instruction;
        while (t-- > 0) {
            st = new StringTokenizer(cin.readLine());
            N = Integer.parseInt(st.nextToken());
            init();
            T = Integer.parseInt(st.nextToken());
            for (int i = 0; i < T; i++) {
                st = new StringTokenizer(cin.readLine());
                instruction = st.nextToken();
                if ("C".equals(instruction)) {
                    x1 = Integer.parseInt(st.nextToken());
                    y1 = Integer.parseInt(st.nextToken());
                    x2 = Integer.parseInt(st.nextToken());
                    y2 = Integer.parseInt(st.nextToken());
                    change(x1, y1, x2, y2);
                    continue;
                }
                x = Integer.parseInt(st.nextToken());
                y = Integer.parseInt(st.nextToken());
                query(x, y);
            }
            sb.append('\n');
        }
        System.out.printf(sb.toString());
    }

    private static void init() {
        for (int i = 1; i <= N; i++)
            for (int j = 1; j <= N; j++)
                tree[i][j] = 0;
    }

    private static void query(int x, int y) {
        int count = read(x, y);
        sb.append(count & 1).append('\n');
    }

    private static int read(int x, int y) {
        int sum = 0;
        for (int i = x; i <= N; i += i & -i)
            for (int j = y; j <= N; j += j & -j)
                sum ^= tree[i][j];
        return sum;
    }

    private static void change(int x1, int y1, int x2, int y2) {
        update(x2, y2);
        update(x2, y1 - 1);
        update(x1 - 1, y2);
        update(x1 - 1, y1 - 1);
    }

    private static void update(int x, int y) {
        for (int i = x; i > 0; i -= i & -i)
            for (int j = y; j > 0; j -= j & -j)
                tree[i][j] ^= 1;
    }
}
