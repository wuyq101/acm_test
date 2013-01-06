package com.poj;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * <pre>
 * http://poj.org/problem?id=2079
 * 题意：求点集中最大的三角形面积
 * 思路：先求凸包，然后在凸包中枚举三角形，求最大值，这里使用旋转卡壳的思想。
 * </pre>
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main2079 {
    private static int N;
    private static P[] points = new P[50000];
    private static List<P> stack = new ArrayList<P>();

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        N = cin.nextInt();
        while (N != -1) {
            for (int i = 0; i < N; i++) {
                if (points[i] == null)
                    points[i] = new P();
                points[i].x = cin.nextInt();
                points[i].y = cin.nextInt();
            }
            solve();
            N = cin.nextInt();
        }
    }

    private static void solve() {
        swap_first();
        sort();
        graham_scan();
        calc_result();
    }

    /**
     * <pre>
     * 计算凸包中的最大三角形面积
     * 固定前两点 i 和 j，然后顺着凸包枚举下一个点，求面积，可以发现面积是单峰性的。所以如果当前面积小于上面积，就直接 break；
     * 考虑还可以发现，当固定第一个点 i，枚举第二个点 j，依然存在单峰性，
     * 如果上次枚举 j 时取得的最大三角形面积对应的第三个点是 k，
     * 那么这次下一个 j 时就可以直接从 k + 1 开始，因此第二个循环里面也一样可以加上一个 break。
     * </pre>
     */
    private static void calc_result() {
        int len = stack.size();
        double max = -1;
        for (int i = 0; i <= len - 3; i++) {
            int lask_k = i + 2;
            double area_j = -1;
            for (int j = i + 1; j <= len - 2; j++) {
                double s = -1;
                for (int k = lask_k; k <= len - 1; k++) {
                    double d = area(stack.get(i), stack.get(j), stack.get(k));
                     System.out.println(i + "," + j + "," + k + "\t" + d);
                    if (d >= s) {
                        s = d;
                    } else {
                        lask_k = k - 1;
                        break;
                    }
                }
                if (s >= area_j) {
                    area_j = s;
                } else {
                    break;
                }
            }
            if (area_j >= max)
                max = area_j;
        }
        System.out.printf("%.2f\n", max);
    }

    private static void graham_scan() {
        stack.clear();
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
     * 首先选取y坐标最小的点，如果y坐标相同，则选其中x坐标最小的，将它交换到points[0]
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
     * 根据三点的坐标求三角形面积
     * 
     * @param p1
     * @param p2
     * @param p3
     * @return
     */
    private static double area(P p1, P p2, P p3) {
        long cp = cross_product(p1, p2, p3);
        return Math.abs(cp) / 2.0;
    }

    private static class P {
        int x;
        int y;

        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }
}
