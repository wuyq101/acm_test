package com.poj.geometry;

import java.util.Scanner;

/**
 * 原题：http://poj.org/problem?id=1654
 * 题意：求多边形面积
 * User: wuyq101
 * Date: 13-1-24
 * Time: 下午9:32
 */
public class Main1654 {
    private static class Point {
        int x, y;
    }

    private static int[][] direction = {{0, 0}, {-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {0, 0}, {1, 0}, {-1, 1}, {0, 1}, {1, 1}};

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int t = cin.nextInt();
        while (t-- > 0) {
            String s = cin.next();
            solve(s);
        }
    }

    private static void solve(String s) {
        Point a = new Point();
        Point b = new Point();
        int x = 0, y = 0, idx;
        char pre = s.charAt(0), ch;
        long area = 0;
        idx = pre - '0';
        x += direction[idx][0];
        y += direction[idx][1];
        for (int i = 1, len = s.length() - 1; i < len; i++) {
            ch = s.charAt(i);
            //新增一个点
            if (ch != pre) {
                b.x = x;
                b.y = y;
                area += cross_product(a, b);
                a.x = x;
                a.y = y;
            }
            idx = ch - '0';
            x += direction[idx][0];
            y += direction[idx][1];
            pre = ch;
        }
        b.x = 0;
        b.y = 0;
        area += cross_product(a, b);
        area = Math.abs(area);
        if (area % 2 == 0) {
            System.out.printf("%d\n", area / 2);
        } else {
            System.out.printf("%d.5\n", (area - 1) / 2);
        }
    }

    private static long cross_product(Point a, Point b) {
        return a.x * b.y - b.x * a.y;
    }
}
