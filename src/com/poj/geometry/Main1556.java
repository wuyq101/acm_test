package com.poj.geometry;
import java.util.Scanner;

/**
 * <pre>
 * 原题：http://poj.org/problem?id=1556
 * 题意：房间中有墙，墙上有两门，门开着不同的地方，给出坐标，求从房间左侧的某点到房间右侧的某点的最短路劲。
 * 分析：先求每个门的两个点之间的距离，如果被其他墙挡住，记为不可达，这样构成了一个图，然后就图的最短路劲。计算两点之间的线段是
 *      否被其他墙给挡了，使用线段是否相交来判断（叉积）。
 * </pre>
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main1556 {
    private static int n;
    private static P[] points = new P[18 * 4 + 2];
    private static Segment[][] walls = new Segment[18][3];
    private static double[][] graph = new double[18 * 4 + 2][18 * 4 + 2];
    private static double[] dist = new double[18 * 4 + 2];
    private static double MAX = 100000.0;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        n = cin.nextInt();
        double x = 0.0;
        double[] y = new double[4];
        while (n != -1) {
            int idx = 0;
            points[idx++] = new P(0, 5);
            for (int i = 0; i < n; i++) {
                x = cin.nextDouble();
                for (int j = 0; j < 4; j++) {
                    y[j] = cin.nextDouble();
                }
                points[idx++] = new P(x, y[0]);
                points[idx++] = new P(x, y[1]);
                points[idx++] = new P(x, y[2]);
                points[idx++] = new P(x, y[3]);
                walls[i][0] = new Segment(new P(x, 0), new P(x, y[0]));
                walls[i][1] = new Segment(new P(x, y[1]), new P(x, y[2]));
                walls[i][2] = new Segment(new P(x, y[3]), new P(x, 10));
            }
            points[idx++] = new P(10, 5);
            solve();
            n = cin.nextInt();
        }
    }

    private static void solve() {
        build_graph();
        dijkstra();
    }

    private static void dijkstra() {
        int N = n * 4 + 2;
        // 初始值
        for (int i = 0; i < N; i++) {
            dist[i] = graph[0][i];
        }
        for (int j = 0; j < N; j++) {
            for (int i = 0; i < N; i++) {
                if (dist[j] > dist[i] + graph[i][j]) {
                    dist[j] = dist[i] + graph[i][j];
                }
            }
        }
        System.out.printf("%.2f\n", dist[N - 1]);
    }

    /**
     * 构图
     */
    private static void build_graph() {
        int N = n * 4 + 2;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                // 计算点i和点j之间的距离
                graph[i][j] = dist(i, j);
                graph[j][i] = graph[i][j];
            }
        }
        for (int i = 0; i < N; i++) {
            graph[i][i] = MAX;
        }
    }

    /**
     * 计算点i和点j之间的距离
     * 
     * @param a
     * @param b
     * @return
     */
    private static double dist(int a, int b) {
        // 简单点i和点j之间的所有墙，是否和线段ab相交
        Segment path = new Segment(points[a], points[b]);
        double min_x = points[a].x < points[b].x ? points[a].x : points[b].x;
        double max_x = points[a].x > points[b].x ? points[a].x : points[b].x;
        if (min_x == max_x) {
            double ya = points[a].y;
            double yb = points[b].y;
            for (int i = 0; i < n; i++) {
                double x = walls[i][0].a.x;
                if (x == min_x) {
                    if ((ya == walls[i][0].b.y && yb == walls[i][1].a.y)
                            || (ya == walls[i][1].b.y && yb == walls[i][2].a.y)) {
                        return yb - ya;
                    }
                    return MAX;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            double x = walls[i][0].a.x;
            if (min_x < x && x < max_x) {
                for (int j = 0; j < 3; j++) {
                    if (segment_cross(walls[i][j], path))
                        return MAX;
                }
            }
        }
        return distance(points[a], points[b]);
    }

    /**
     * 两点之间的距离
     * 
     * @param a
     * @param b
     * @return
     */
    private static double distance(P a, P b) {
        double x = a.x - b.x;
        double y = a.y - b.y;
        return Math.sqrt(x * x + y * y);
    }

    /**
     * 判断两条线段是否相交
     * 
     * @param segment
     * @param path
     * @return
     */
    private static boolean segment_cross(Segment A, Segment B) {
        P a = A.a, b = A.b, c = B.a, d = B.b;
        // a和b否在直线cd的两侧-->向量cd乘以ca 向量cd和向量cb 叉积异号
        int sig_cd_ca = sig(cross_product(c, d, a));
        int sig_cd_cb = sig(cross_product(c, d, b));
        // 都在同一侧，认为不相交
        if (sig_cd_ca * sig_cd_cb > 0) {
            return false;
        }
        // 判断a是否在cd上
        if (sig_cd_ca == 0 && is_on_segment(c, d, a)) {
            return true;
        }
        // 判断b是否再cd上
        if (sig_cd_cb == 0 && is_on_segment(c, d, b)) {
            return true;
        }
        // 判断c和d是否再直接ab的两侧 ---> 向量ab乘以向量ac 向量ab乘以向量ad 叉积异号
        int sig_ab_ac = sig(cross_product(a, b, c));
        int sig_ab_ad = sig(cross_product(a, b, d));
        if (sig_ab_ac * sig_ab_ad > 0) {
            return false;
        }
        // 判断c是否在ab上
        if (sig_ab_ac == 0 && is_on_segment(a, b, c)) {
            return true;
        }
        // 判断d是否在ab上
        if (sig_ab_ad == 0 && is_on_segment(a, b, d)) {
            return true;
        }
        // 规范相交
        if (sig_cd_ca * sig_cd_cb < 0 && sig_ab_ac * sig_ab_ad < 0) {
            return true;
        }
        return false;
    }

    private static int sig(double value) {
        if (value == 0)
            return 0;
        return value > 0 ? 1 : -1;
    }

    /**
     * 判断点c是否在线段ab上，前提三点共线
     * 
     * @param a
     * @param b
     * @param c
     * @return
     */
    private static boolean is_on_segment(P a, P b, P c) {
        return dot_product(c, a, b) < 0;
    }

    /**
     * 向量ab和向量ac的点积
     * 
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static double dot_product(P a, P b, P c) {
        return (b.x - a.x) * (c.x - a.x) + (b.y - a.y) * (c.y - b.y);
    }

    /**
     * 向量ab乘以向量ac的叉积
     * 
     * @param a
     * @param b
     * @param c
     * @return
     */
    private static double cross_product(P a, P b, P c) {
        return (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y);
    }

    private static class P {
        double x;
        double y;

        public P(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    private static class Segment {
        P a;
        P b;

        public Segment(P a, P b) {
            this.a = a;
            this.b = b;
        }

        public String toString() {
            return a + "--" + b;
        }
    }

}
