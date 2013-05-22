package com.poj.enumeration;

import java.io.IOException;
import java.util.Scanner;

/**
 * http://poj.org/problem?id=2606
 * 给定平面上的点，求共线最多的点的个数。 枚举
 * User: wuyq101
 * Date: 13-3-15
 * Time: 下午3:33
 */
public class Main2606 {
    private static class Point {
        int x, y;
    }

    private static int n, max;
    private static Point[] points = new Point[200];

    public static void main(String[] args) throws IOException {
        Scanner cin = new Scanner(System.in);
        n = cin.nextInt();
        for (int i = 0; i < n; i++) {
            if (points[i] == null)
                points[i] = new Point();
            points[i].x = cin.nextInt();
            points[i].y = cin.nextInt();
        }
        solve();
        System.out.printf("%d\n", max);
    }

    private static void solve() {
        max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int temp = 2;
                for (int k = j + 1; k < n; k++) {
                    if (cross_product(points[i], points[j], points[k]) == 0)
                        temp++;
                }
                if (temp > max)
                    max = temp;
                if (max == n) return;
            }
        }
    }

    /**
     * 向量ab 向量ac的叉积,, 如果ab在ac的顺时针，正，
     * ab在ac的逆时针方向，负
     */
    public static int cross_product(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y);
    }

}
