package com.poj;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * <pre>
 * http://poj.org/problem?id=2187
 * 求点集中两点之间最大的距离
 * 先求凸包，然后在计算凸包中点的两两距离，求最大值
 * 使用graham scan方法求凸包.
 * </pre>
 * 
 * @author wyq101
 * @version 1.0
 */
public class Main2187 {
    private static int N;
    private static P[] points;
    private static List<P> stack = new ArrayList<P>();

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        N = cin.nextInt();
        points = new P[N];
        for (int i = 0; i < N; i++) {
            points[i] = new P(cin.nextInt(), cin.nextInt());
        }
        solve();
    }

    private static void solve() {
        swap_first();
        sort();
        graham_scan();
        calc_result();
    }

    /**
     * 计算stack中（凸包中点的两两距离）,取最大值
     */
    private static void calc_result() {
        long max = 0;
        int size = stack.size();
        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size; j++) {
                long d = distance(stack.get(i), stack.get(j));
                if (d > max) {
                    max = d;
                }
            }
        }
        System.out.println(max);
    }

    private static void graham_scan() {
        stack.add(points[0]);
        stack.add(points[1]);
        for (int i = 2; i < N; i++) {
            int m = stack.size();
            P p0 = stack.get(m - 2);
            P p1 = stack.get(m - 1);
            P p2 = points[i];
            long cp = cross_product(p0, p1, p2);
            if (cp >= 0) {
                stack.add(points[i]);
                continue;
            } else {
                stack.remove(m - 1);
                i--;
                continue;
            }
        }
    }

    /**
     * 根据当前点与farms[0]以及x轴的夹角排序,如果共线，距离近的优先
     */
    private static void sort() {
        Arrays.sort(points, 1, N, new Comparator<P>() {
            // 比较p1,p2与p0的夹角
            @Override
            public int compare(P p1, P p2) {
                long cp = cross_product(points[0], p1, p2);
                if (cp == 0) {
                    long d1 = distance(p1, points[0]);
                    long d2 = distance(p2, points[0]);
                    if (d1 == d2)
                        return 0;
                    return d1 > d2 ? 1 : -1;
                }
                return cp > 0 ? -1 : 1;
            }
        });
    }

    /**
     * 计算两点之间的距离
     * 
     * @param p1
     * @param p2
     * @return
     */
    private static long distance(P p1, P p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y);
    }

    /**
     * <pre>
     * 两个向量（x2-x1,y2-y1）,(x3-x1,y3-y1)的叉积cp。
     * 1. cp的一半是一个三角型的有向面积
     * 2. cp的符号代表向量旋转的方向，如果向量A乘以向量B，如果为正，则A逆时针转向B，否则为顺时针
     *    如果为0，则共线
     * </pre>
     * 
     * @param p1
     * @param p2
     * @param p3
     * @return
     */
    public static long cross_product(P p1, P p2, P p3) {
        return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
    }

    /**
     * 首先选取y坐标最小的点，如果y坐标相同，则选其中x坐标最小的，将它交换到farms[0]
     */
    private static void swap_first() {
        for (int i = 1; i < N; i++) {
            if (points[0].y > points[i].y || (points[0].y == points[i].y && points[0].x > points[i].x)) {
                swap(points[0], points[i]);
            }
        }
    }

    private static void swap(P p1, P p2) {
        int temp = p1.y;
        p1.y = p2.y;
        p2.y = temp;
        temp = p1.x;
        p1.x = p2.x;
        p2.x = temp;
    }

    private static class P {
        int x;
        int y;

        public P(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }
}
