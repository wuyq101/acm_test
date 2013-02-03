import java.util.Scanner;

/**
 * 原题：http://poj.org/problem?id=1418
 * 题意：平面上有一堆圆片，互相交叠，求可见的圆的个数。
 * 分析：
 * User: wuyq101
 * Date: 13-1-22
 * Time: 上午10:44
 */
public class Main1418 {

    /**
     * 坐标点kdj
     */
    private static class Point {
        double x, y;
    }

    /**
     * 圆
     */
    private static class Circle {
        int z;
        Point center;
        double r;
        boolean visible;
    }

    /**
     * 一段圆弧
     */
    private static class Arc {
        int z;
        Point a, b;
    }

    private static final int MAX = 100;
    private static final double esp = 5e-13;
    private static int n;
    private static Circle[] circles = new Circle[MAX];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (true) {
            n = cin.nextInt();
            if (n == 0)
                break;
            for (int i = 0; i < n; i++) {
                if (circles[i] == null) {
                    circles[i] = new Circle();
                    circles[i].center = new Point();
                }
                circles[i].center.x = cin.nextDouble();
                circles[i].center.y = cin.nextDouble();
                circles[i].r = cin.nextDouble();
                circles[i].z = i;
                circles[i].visible = false;
            }
            solve();
        }
    }

    /**
     * 两点之间的距离
     */
    private static double distance(Point a, Point b) {
        return Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }

    /**
     * 求点p是否再circle内
     *
     * @param p
     * @param circle
     * @return
     */
    private static boolean is_in_circle(Point p, Circle circle) {
        double d = distance(p, circle.center);
        d -= circle.r;
        if (d < -esp)
            return true;
        if (d > esp)
            return false;
        return true;
    }

    private static void solve() {
        //两两求圆的相交
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                Circle ca = circles[i], cb = circles[j];
                //相交
                if (distance(ca.center, cb.center) <= ca.r + cb.r) {

                }
            }
        }
    }

}
