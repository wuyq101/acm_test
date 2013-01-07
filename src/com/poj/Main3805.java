package com.poj;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * <pre>
 * http://poj.org/problem?id=3805
 * 题意：给定平面上两个点集P和Q，判断是否存在一条直线把他们分割开。
 * 分析：1). P中点两两连接，得到线段集合A，Q中点两两连接，得到线段集合B，两两判断AB集合中的线段是否相交。
 *          复杂度O(n^4)。另外还没有考虑包含的情况。
 *       
 *       2). 分别求P和Q的凸包，再判断凸包是否相交。如果凸包不相交，则存在这样的直线。
 *           凸包P和凸包Q是否相交判断过程如下：P中的所有边与Q中的所有边两两判断是否有交点。如果有说明相交。
 *           如果没有，再做下面的判断：
 *           2.1). 在P中任意找一点，判断它是否再Q内，如果在Q内，则Q包含P，相交。如果不在，再做如下判断：
 *           2.2). 在Q中任意找一点，判断它是否再P内，如果在P内，则P包含Q，相交。如果不在，则两个凸包不相交。
 *           
 *       3). 判断点S是否再凸包P中的方法：将点和凸包上的相邻两个点连成两个向量，求三角形的有向面积，如果所有面积都同号，
 *           则S在凸包内，如果有一个面积是0，则点在凸包上，如果异号，则在凸包外。
 * </pre>
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main3805 {
    private static int n;
    private static int m;
    private static P[] black = new P[100];
    private static P[] white = new P[100];
    private static List<P> stack_black = new ArrayList<P>();
    private static List<P> stack_white = new ArrayList<P>();

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        n = cin.nextInt();
        m = cin.nextInt();
        while (n != 0 && m != 0) {
            for (int i = 0; i < n; i++) {
                if (black[i] == null)
                    black[i] = new P();
                black[i].x = cin.nextInt();
                black[i].y = cin.nextInt();
            }
            for (int i = 0; i < m; i++) {
                if (white[i] == null)
                    white[i] = new P();
                white[i].x = cin.nextInt();
                white[i].y = cin.nextInt();
            }
            solve();
            n = cin.nextInt();
            m = cin.nextInt();
        }
    }

    private static void solve() {
        if (n == 1 && m == 1) {
            System.out.println("YES");
            return;
        }
        if (n == 1) {
            if (m == 2) {
                // 判断点是否在线段上
                System.out.println(is_online(white[0], white[1], black[0]) ? "NO" : "YES");
                return;
            }
            // 判断点是否在凸包中
            swap_first(white, m);
            sort(white, m);
            graham_scan(stack_white, white, m);
            System.out.println(convex_hull_contains_point(stack_white, black[0]) ? "NO" : "YES");
            return;
        }
        if (m == 1) {
            if (n == 2) {
                // 判断点是否在线段上
                System.out.println(is_online(black[0], black[1], white[0]) ? "NO" : "YES");
                return;
            }
            // 判断点是否在凸包中
            swap_first(black, n);
            sort(black, n);
            graham_scan(stack_black, black, n);
            System.out.println(convex_hull_contains_point(stack_black, white[0]) ? "NO" : "YES");
            return;
        }
        if (n == 2) {
            if (m == 2) {
                // 判断线段是否相交
                System.out.println(seg_cross(black[0], black[1], white[0], white[1]) ? "NO" : "YES");
                return;
            }
            // 判断线段和凸包是否相交
            swap_first(white, m);
            sort(white, m);
            graham_scan(stack_white, white, m);
            System.out.println(convex_hull_cross_seg(stack_white, black[0], black[1]) ? "NO" : "YES");
            return;
        }
        if (m == 2) {
            // 判断线段和凸包是否相交
            swap_first(black, n);
            sort(black, n);
            graham_scan(stack_black, black, n);
            System.out.println(convex_hull_cross_seg(stack_black, white[0], white[1]) ? "NO" : "YES");
            return;
        }
        // 先求两个点集的凸包
        swap_first(black, n);
        sort(black, n);
        graham_scan(stack_black, black, n);
        swap_first(white, m);
        sort(white, m);
        graham_scan(stack_white, white, m);
        System.out.println(convex_hull_cross(stack_black, stack_white) ? "NO" : "YES");
    }

    /**
     * 判断凸包p和线段ab是否相交
     * 
     * @param p
     * @param a
     * @param b
     * @return
     */
    private static boolean convex_hull_cross_seg(List<P> p, P a, P b) {
        int size = p.size();
        for (int i = 0; i <= size - 1; i++) {
            P c = p.get(i);
            P d = i == size - 1 ? p.get(0) : p.get(i + 1);
            if (seg_cross(a, b, c, d))
                return true;
        }
        if (convex_hull_contains_point(p, a))
            return true;
        return false;
    }

    /**
     * 判断两个凸包是否相交
     * 
     * @param convex1
     * @param convex2
     * @return
     */
    private static boolean convex_hull_cross(List<P> p, List<P> q) {
        int len_p = p.size();
        int len_q = q.size();
        // P中的所有边与Q中的所有边两两判断是否有交点
        for (int i = 0; i <= len_p - 1; i++) {
            // P中的边（i,i+1）
            P a = p.get(i);
            P b = i == len_p - 1 ? p.get(0) : p.get(i + 1);
            for (int j = 0; j <= len_q - 1; j++) {
                // Q中的边（j,j+1)
                P c = q.get(j);
                P d = j == len_q - 1 ? q.get(0) : q.get(j + 1);
                boolean cross = seg_cross(a, b, c, d);
                if (cross)
                    return true;
            }
        }
        // 在P中取一点，判断它是否在Q内
        if (convex_hull_contains_point(q, p.get(0))) {
            return true;
        }
        // 在Q中取一点，判断它是否在P内
        if (convex_hull_contains_point(p, q.get(0))) {
            return true;
        }
        return false;
    }

    /**
     * <pre>
     * 判断点S是否再凸包P中的方法：将点和凸包上的相邻两个点连成两个向量，求三角形的有向面积，如果所有面积都同号，
     * 则S在凸包内，如果有一个面积是0，则点在凸包上，如果异号，则在凸包外。
     * </pre>
     * 
     * @param q
     * @param s
     * @return
     */
    private static boolean convex_hull_contains_point(List<P> convex, P s) {
        int size = convex.size();
        int sig = sig(cross_product(s, convex.get(0), convex.get(1)));
        for (int i = 1; i <= size - 1; i++) {
            P a = convex.get(i);
            P b = i == size - 1 ? convex.get(0) : convex.get(i + 1);
            // 向量sa乘以sb
            if (sig != sig(cross_product(s, a, b))) {
                return false;
            }
        }
        return true;
    }

    /**
     * <pre>
     * 判断线段ab,cd是否相交
     * 线段ab和cd规范相交等价于：a和b分属线段cd所在直线的两侧；c和d分属线段ab的两侧。
     * 判断a和b是否再直线cd的两侧可以判断向量cd和ca的叉积与向量cd和向量cb的叉积符号是否异号。
     * </pre>
     * 
     * @param a
     * @param b
     * @param c
     * @param d
     * @return false 代表不相交， true代表相交
     */
    private static boolean seg_cross(P a, P b, P c, P d) {
        // a和b否在直线cd的两侧-->向量cd乘以ca 向量cd和向量cb 叉积异号
        int sig_cd_ca = sig(cross_product(c, d, a));
        int sig_cd_cb = sig(cross_product(c, d, b));
        // 都在同一侧，认为不相交
        if (sig_cd_ca * sig_cd_cb > 0) {
            return false;
        }
        // 判断a是否在cd上
        if (sig_cd_ca == 0 && is_online(c, d, a)) {
            return true;
        }
        // 判断b是否再cd上
        if (sig_cd_cb == 0 && is_online(c, d, b)) {
            return true;
        }
        // 判断c和d是否再直接ab的两侧 ---> 向量ab乘以向量ac 向量ab乘以向量ad 叉积异号
        int sig_ab_ac = sig(cross_product(a, b, c));
        int sig_ab_ad = sig(cross_product(a, b, d));
        if (sig_ab_ac * sig_ab_ad > 0) {
            return false;
        }
        // 判断c是否在ab上
        if (sig_ab_ac == 0 && is_online(a, b, c)) {
            return true;
        }
        // 判断d是否在ab上
        if (sig_ab_ad == 0 && is_online(a, b, d)) {
            return true;
        }
        // 规范相交
        if (sig_cd_ca * sig_cd_cb < 0 && sig_ab_ac * sig_ab_ad < 0) {
            return true;
        }
        return false;
    }

    /**
     * <pre>
     * 判断点c是否在线段ab上 
     * 前提:a,b,c三点共线(向量ab乘以向量ac叉积为0)
     * 方法：向量ca和向量cb的点积，如果点积为正，c在线段外部，点积为0，则c和a或者b重合，点积为负，则c在线段内部
     * </pre>
     * 
     * @param a
     * @param b
     * @param c
     * @return
     */
    private static boolean is_online(P a, P b, P c) {
        return dot_product(c, a, b) <= 0;
    }

    /**
     * 求value的符号
     * 
     * @param value
     * @return
     */
    private static int sig(long value) {
        if (value == 0)
            return 0;
        return value > 0 ? 1 : -1;
    }

    private static void graham_scan(List<P> stack, P[] points, int len) {
        stack.clear();
        stack.add(points[0]);
        stack.add(points[1]);
        for (int i = 2; i < len; i++) {
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

    private static void sort(final P[] points, int len) {
        Arrays.sort(points, 1, len, new Comparator<P>() {
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

    private static void swap_first(P[] points, int len) {
        for (int i = 1; i < len; i++) {
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
     * 两个向量p1p2（x2-x1,y2-y1）,p1p3(x3-x1,y3-y1)的叉积cp。
     * 1. cp的一半是一个三角型的有向面积
     * 2. cp的符号代表向量旋转的方向，如果向量p1p2乘以向量p1p3，如果为正，则p1p2逆时针转向p1p3，否则为顺时针
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
     * 向量p1p2(x2-x1,y2-y1)和向量p1p3(x3-x1,y3-y1)的点积
     * 
     * @param p1
     * @param p2
     * @param p3
     * @return
     */
    public static long dot_product(P p1, P p2, P p3) {
        return (p2.x - p1.x) * (p3.x - p1.x) + (p2.y - p1.y) * (p3.y - p1.y);
    }

    /**
     * 坐标点
     * 
     * @author wuyq101
     * @version 1.0
     */
    private static class P {
        int x;
        int y;

        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }
}