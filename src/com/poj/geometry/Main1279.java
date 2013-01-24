package com.poj.geometry;

import java.util.Scanner;

/**
 * 原题：http://poj.org/problem?id=1279
 * 题意：求多边形中可以看到所有点的总面积，比如放一盏灯，等照射到多边形所有地方，求那些位置可以放灯，求这些位置的面积。
 * 分析：使用多边形的每条边去切割自己，最后剩下的多边形面积即是所要求的面积。
 * User: wuyq101
 * Date: 13-1-23
 * Time: 上午10:36
 */
public class Main1279 {
    private static final int MAX = 1501;

    private static class Point {
        double x, y;
    }

    private static class Polygon {
        int n;
        Point[] points = new Point[MAX];

        public Polygon() {
        }

        public Polygon(Polygon polygon) {
            n = polygon.n;
            for (int i = 0; i <= n; i++) {
                points[i] = polygon.points[i];
            }
        }
    }

    private static class Line {
        double a, b, c;
    }

    private static Polygon gallery;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int t = cin.nextInt();
        while (t-- > 0) {
            gallery = new Polygon();
            gallery.n = cin.nextInt();
            for (int i = 0; i < gallery.n; i++) {
                gallery.points[i] = new Point();
                gallery.points[i].x = cin.nextInt();
                gallery.points[i].y = cin.nextInt();
            }
            gallery.points[gallery.n] = gallery.points[0];
            solve();
        }
    }

    private static void solve() {
        swap(gallery);
        Polygon result = new Polygon(gallery);
        for (int i = 0; i < gallery.n; i++) {
            result = cut_polygon(result, gallery.points[i], gallery.points[i + 1]);
        }
        System.out.printf("%.2f\n", getArea(result));
    }

    /**
     * 调整多边形顶点为顺时针排序
     */
    private static void swap(Polygon polygon) {
        double area = getArea(polygon);
        if (area <= 0) {
            int i = 1, j = polygon.n - 1;
            while (i < j) {
                Point temp = polygon.points[i];
                polygon.points[i++] = polygon.points[j];
                polygon.points[j--] = temp;
            }
        }
    }

    private static double getArea(Polygon polygon) {
        if (polygon.n < 3)
            return 0;
        Point a = polygon.points[0];
        double area = 0;
        for (int i = polygon.n - 1; i > 0; i--) {
            area += cross_product(a, polygon.points[i], polygon.points[i - 1]);
        }
        return area / 2;
    }

    private static Polygon cut_polygon(Polygon polygon, Point a, Point b) {
        Polygon result = new Polygon();
        for (int i = 0; i < polygon.n; i++) {
            double cp1 = cross_product(a, b, polygon.points[i]);
            double cp2 = cross_product(a, b, polygon.points[i + 1]);
            //a，b，p[i]在ab线的上方,p[i]点继续保留
            if (cp1 <= 0) {
                result.points[result.n++] = polygon.points[i];
            }
            //边i和直线ab相交
            if (cp1 * cp2 < 0) {
                Point p = intersect(getLine(a, b), getLine(polygon.points[i], polygon.points[i + 1]));
                result.points[result.n++] = p;
            }
        }
        result.points[result.n] = result.points[0];
        return result;
    }

    private static Line getLine(Point a, Point b) {
        Line line = new Line();
        line.a = a.y - b.y;
        line.b = b.x - a.x;
        line.c = a.x * b.y - b.x * a.y;
        return line;
    }

    private static Point intersect(Line A, Line B) {
        Point p = new Point();
        double d = A.a * B.b - B.a * A.b;
        p.x = (B.c * A.b - A.c * B.b) / d;
        p.y = (A.c * B.a - B.c * A.a) / d;
        return p;
    }

    /**
     * 向量ab 向量ac的叉积,, 如果ab在ac的顺时针，正，
     * ab在ac的逆时针方向，负
     */
    public static double cross_product(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y);
    }
}
