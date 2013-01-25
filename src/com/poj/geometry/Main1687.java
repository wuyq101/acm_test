package com.poj.geometry;

import java.util.Scanner;

/**
 * 原题：http://poj.org/problem?id=1687
 * 题意：给定多个多边形，求最外围的多边形
 * 分析：面积最大的即是最外围的那个多边形
 * User: wuyq101
 * Date: 13-1-25
 * Time: 下午11:14
 */
public class Main1687 {
    private static class Point {
        int x, y;
    }

    private static final int MAX = 51;
    private static int n, m;
    private static Point[] points = new Point[MAX];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int N = cin.nextInt();
        while (N-- > 0) {
            n = cin.nextInt();
            for (int i = 1; i <= n; i++) {
                if (points[i] == null) {
                    points[i] = new Point();
                }
                points[i].x = cin.nextInt();
                points[i].y = cin.nextInt();
            }
            long max = 0;
            int answer = 0;
            m = cin.nextInt();
            for (int i = 0; i < m; i++) {
                long area = 0;
                int cnt = cin.nextInt();
                int first = cin.nextInt();
                int pre = first, cur = 0;
                for (int j = 1; j < cnt; j++) {
                    cur = cin.nextInt();
                    area += cross_product(pre, cur);
                    pre = cur;
                }
                area += cross_product(pre, first);
                area = Math.abs(area);
                if (area > max) {
                    max = area;
                    answer = i + 1;
                }
            }
            System.out.println(answer);
        }
    }

    private static long cross_product(int i, int j) {
        return points[i].x * points[j].y - points[i].y * points[j].x;
    }
}
