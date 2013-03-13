package com.poj.binary_indexed_tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <pre>
 *     http://poj.org/problem?id=1195
 *     树状数组
 * </pre>
 * User: wuyq101
 * Date: 13-3-7
 * Time: 下午12:40
 */
public class Main1195 {
    private static int[][] tree = new int[1025][1025];
    private static int S;

    public static void main(String[] args) throws IOException {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int instruction, x, y, a, l, b, r, t;
        while (true) {
            st = new StringTokenizer(cin.readLine());
            instruction = Integer.parseInt(st.nextToken());
            if (instruction == 3)
                break;
            if (instruction == 0) {
                S = Integer.parseInt(st.nextToken());
                continue;
            }
            if (instruction == 1) {
                x = Integer.parseInt(st.nextToken()) + 1;
                y = Integer.parseInt(st.nextToken()) + 1;
                a = Integer.parseInt(st.nextToken());
                update(x, y, a);
                continue;
            }
            if (instruction == 2) {
                l = Integer.parseInt(st.nextToken()) + 1;
                b = Integer.parseInt(st.nextToken()) + 1;
                r = Integer.parseInt(st.nextToken()) + 1;
                t = Integer.parseInt(st.nextToken()) + 1;
                query(l, b, r, t);
                continue;
            }
        }
    }

    private static void query(int l, int b, int r, int t) {
        System.out.println(read(r, t) - read(l-1, t) - read(r, b-1) + read(l-1, b-1));
    }

    private static int read(int x, int y) {
        int sum = 0;
        int y1 = y;
        while (x > 0) {
            y = y1;
            while (y > 0) {
                sum += tree[x][y];
                y -= y & -y;
            }
            x -= x & -x;
        }
        return sum;
    }

    private static void update(int x, int y, int val) {
        int y1 = y;
        while (x <= S) {
            y = y1;
            while (y <= S) {
                tree[x][y] += val;
                y += y & -y;
            }
            x += x & -x;
        }
    }
}
