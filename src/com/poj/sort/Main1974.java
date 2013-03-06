package com.poj.sort;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * User: wuyq101
 * Date: 13-3-4
 * Time: 下午4:34
 */
public class Main1974 {
    private static class Stone {
        int x, y;
    }

    private static int m, n, k, cnt;
    private static Stone[] stones = new Stone[131072];


    public static void main(String[] args) throws Exception {
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String line = stdin.readLine();
        StringTokenizer st = new StringTokenizer(line);
        int t = Integer.parseInt(st.nextToken());
        while (t-- > 0) {
            line = stdin.readLine();
            st = new StringTokenizer(line);
            m = Integer.parseInt(st.nextToken());
            n = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());
            for (int i = 0; i < k; i++) {
                if (stones[i] == null)
                    stones[i] = new Stone();
                line = stdin.readLine();
                st = new StringTokenizer(line);
                stones[i].x = Integer.parseInt(st.nextToken());
                stones[i].y = Integer.parseInt(st.nextToken());
            }
            solve();
        }
    }

    private static void solve() {
        cnt = 0;
        //按行排序
        count_row();
        //按列排序
        count_col();
        System.out.printf("%d\n", cnt);
    }

    private static void count_row() {
        Arrays.sort(stones, 0, k, new Comparator<Stone>() {
            @Override
            public int compare(Stone a, Stone b) {
                if (a.x != b.x)
                    return a.x < b.x ? -1 : 1;
                return a.y < b.y ? -1 : 1;
            }
        });
        int lx = 1, ly = 0;
        for (int i = 0; i < k; i++) {
            if (stones[i].x == lx) {
                if (stones[i].y - ly > 2)
                    cnt++;
            } else {
                cnt += stones[i].x - lx - 1;
                if (n - ly >= 2)
                    cnt++;
                if (stones[i].y > 2)
                    cnt++;
            }
            lx = stones[i].x;
            ly = stones[i].y;
        }
        cnt += m - lx;
        if (n - ly >= 2)
            cnt++;
    }

    private static void count_col() {
        Arrays.sort(stones, 0, k, new Comparator<Stone>() {
            @Override
            public int compare(Stone a, Stone b) {
                if (a.y != b.y)
                    return a.y < b.y ? -1 : 1;
                return a.x < b.x ? -1 : 1;
            }
        });
        int lx = 0, ly = 1;
        for (int i = 0; i < k; i++) {
            if (stones[i].y == ly) {
                if (stones[i].x - lx > 2)
                    cnt++;
            } else {
                cnt += stones[i].y - ly - 1;
                if (m - lx >= 2)
                    cnt++;
                if (stones[i].x > 2)
                    cnt++;
            }
            lx = stones[i].x;
            ly = stones[i].y;
        }
        cnt += n - ly;
        if (m - lx >= 2)
            cnt++;
    }

}