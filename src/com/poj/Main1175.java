package com.poj;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 1175 判断图形是否相等
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main1175 {
    private static int[][] sky = new int[100][100];
    private static char[][] ans = new char[100][100];
    private static int W;
    private static int H;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        W = cin.nextInt();
        H = cin.nextInt();
        for (int y = 0; y < H; y++) {
            String str = cin.next();
            for (int x = 0; x < W; x++) {
                sky[y][x] = str.charAt(x) - '0';
                ans[y][x] = '0';
            }
        }
        solve();
    }

    private static Map<Integer, List<List<P>>> graph = new HashMap<Integer, List<List<P>>>();

    private static void solve() {
        // 扫描归类
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                if (sky[y][x] == 1) {
                    List<P> cluster = new ArrayList<P>();
                    dfs(x, y, cluster);
                    sort(cluster);
                    List<List<P>> list = graph.get(cluster.size());
                    if (list == null) {
                        list = new ArrayList<List<P>>();
                        graph.put(cluster.size(), list);
                    }
                    list.add(cluster);
                }
            }
        }
        char ch = 'a';
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                if (sky[y][x] == -1) {
                    List<P> cluster = new ArrayList<P>();
                    sfd(x, y, cluster);
                    // 开始找这个cluster相同的其他图形
                    sort(cluster);
                    List<List<P>> list = graph.get(cluster.size());
                    for (List<P> cluster1 : list) {
                        if (isSame(cluster1, cluster)) {
                            color(ch, cluster1);
                        }
                    }
                    ch = (char) (ch + 1);
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                sb.append(ans[y][x]);
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
    }

    private static boolean isSame(List<P> cluster1, List<P> cluster2) {
        // 拷贝一份
        List<P> temp = new ArrayList<P>();
        for (P p : cluster1) {
            temp.add(new P(p.x, p.y));
        }
        cluster1 = temp;
        if (inn_cmp(cluster1, cluster2))
            return true;
        for (int k = 1; k <= 3; k++) {
            // 旋转90度
            rotate(cluster1);
            if (inn_cmp(cluster1, cluster2))
                return true;
        }
        // 镜像
        reflect(cluster1);
        if (inn_cmp(cluster1, cluster2))
            return true;
        for (int k = 1; k <= 3; k++) {
            // 旋转90度
            rotate(cluster1);
            if (inn_cmp(cluster1, cluster2))
                return true;
        }
        return false;
    }

    private static void reflect(List<P> g) {
        for (int i = 0; i < g.size(); i++) {
            P p = g.get(i);
            p.y = -p.y;
        }
        sort(g);
    }

    // 选择90度
    private static void rotate(List<P> g) {
        for (int i = 0; i < g.size(); i++) {
            P p = g.get(i);
            int x = p.x;
            int y = p.y;
            p.x = y;
            p.y = -x;
        }
        sort(g);
    }

    private static void sort(List<P> cluster) {
        int len = cluster.size();
        for (int i = 0; i < len; i++) {
            int min = i;
            for (int j = i + 1; j < len; j++) {
                if (cluster.get(j).compareTo(cluster.get(min)) < 0) {
                    min = j;
                }
            }
            // swap
            swap(cluster, min, i);
        }
    }

    private static void swap(List<P> list, int i, int j) {
        P pmin = list.get(j);
        P pi = list.get(i);
        int x = pmin.x;
        int y = pmin.y;
        pmin.x = pi.x;
        pmin.y = pi.y;
        pi.x = x;
        pi.y = y;
    }

    private static boolean inn_cmp(List<P> cluster1, List<P> cluster2) {
        int dx = cluster1.get(0).x - cluster2.get(0).x;
        int dy = cluster1.get(0).y - cluster2.get(0).y;
        for (int i = 1; i < cluster1.size(); i++) {
            P p1 = cluster1.get(i);
            P p2 = cluster2.get(i);
            if (p1.x - p2.x != dx || p1.y - p2.y != dy)
                return false;
        }
        return true;
    }

    private static void color(char ch, List<P> cluster) {
        for (P p : cluster) {
            ans[p.y][p.x] = ch;
            sky[p.y][p.x] = 1;
        }
    }

    private static void sfd(int x, int y, List<P> cluster) {
        if (sky[y][x] != -1)
            return;
        cluster.add(new P(x, y));
        sky[y][x] = 1;
        for (int k = 0; k < 8; k++) {
            int dx = x + dir_x[k];
            int dy = y + dir_y[k];
            if (dx >= 0 && dx < W && dy >= 0 && dy < H) {
                sfd(dx, dy, cluster);
            }
        }
    }

    private static int[] dir_x = { 0, 1, 1, 1, 0, -1, -1, -1 };
    private static int[] dir_y = { 1, 1, 0, -1, -1, -1, 0, 1 };

    private static void dfs(int x, int y, List<P> cluster) {
        if (sky[y][x] != 1)
            return;
        cluster.add(new P(x, y));
        sky[y][x] = -1;
        for (int k = 0; k < 8; k++) {
            int dx = x + dir_x[k];
            int dy = y + dir_y[k];
            if (dx >= 0 && dx < W && dy >= 0 && dy < H) {
                dfs(dx, dy, cluster);
            }
        }
    }

    private static class P implements Comparable<P> {
        int x;
        int y;

        public P(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(P p1) {
            if (x == p1.x) {
                if (y == p1.y)
                    return 0;
                return y < p1.y ? -1 : 1;
            }
            return x < p1.x ? -1 : 1;
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

}
