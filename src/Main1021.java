import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 1021 判断图形是否相等,要考虑旋转
 * 
 * @author vanguard001
 * @version 1.0
 */
public class Main1021 {
    private static int W;
    private static int H;
    private static int n;
    private static int[][] left = new int[100][100];
    private static int[][] right = new int[100][100];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int t = cin.nextInt();
        while (t-- > 0) {
            W = cin.nextInt();
            H = cin.nextInt();
            init();
            n = cin.nextInt();
            for (int i = 0; i < n; i++) {
                int x = cin.nextInt();
                int y = cin.nextInt();
                left[y][x] = 1;
            }
            for (int i = 0; i < n; i++) {
                int x = cin.nextInt();
                int y = cin.nextInt();
                right[y][x] = 1;
            }
            solve();
        }
    }

    private static Map<Integer, List<List<P>>> graph = new HashMap<Integer, List<List<P>>>();

    private static void solve() {
        // 将right的图形分类
        flood();
        // 规范化,相对坐标
        for (Integer cnt : graph.keySet()) {
            List<List<P>> list = graph.get(cnt);
            for (List<P> points : list) {
                System.out.println("排序前");
                System.out.println(points);
                norm(points);
                System.out.println("排序后");
                System.out.println(points);
            }
        }
        // 找左边的图形
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                if (left[y][x] != 1)
                    continue;
                List<P> points = new ArrayList<P>();
                dfs(y, x, points, left);
                norm(points);
                // 开始匹配这个图形
                if (!find(points)) {
                    System.out.println("NO");
                    return;
                }
            }
        }
        System.out.println("YES");
    }

    private static boolean find(List<P> points) {
        int cnt = points.size();
        List<List<P>> list = graph.get(cnt);
        if (list == null || list.size() == 0)
            return false;
        for (int i = 0; i < list.size(); i++) {
            List<P> g = list.get(i);
            if (compare(points, g)) {
                list.remove(i);
                return true;
            }
        }
        return false;
    }

    private static boolean compare(List<P> g1, List<P> g2) {
        if (g1.size() == 1 || g1.size() == 2)
            return true;
        if (inner_cmp(g1, g2))
            return true;
        // 顺时针90度,然后比较
        g1 = rotate(g1);
        if (inner_cmp(g1, g2))
            return true;
        // 90度
        g1 = rotate(g1);
        if (inner_cmp(g1, g2))
            return true;
        // 90度
        g1 = rotate(g1);
        if (inner_cmp(g1, g2))
            return true;
        return false;
    }

    private static List<P> rotate(List<P> g) {
        // System.out.println("转前：");
        // System.out.println(g);
        for (int i = 0; i < g.size(); i++) {
            P p = g.get(i);
            int x = p.x;
            int y = p.y;
            p.x = y;
            p.y = -x;
        }
        norm(g);
        // System.out.println("转后：");
        // System.out.println(g);
        return g;
    }

    private static boolean inner_cmp(List<P> g1, List<P> g2) {
        for (int i = 0; i < g1.size(); i++) {
            P p1 = g1.get(i);
            P p2 = g2.get(i);
            if (p1.x != p2.x || p1.y != p1.y) {
                return false;
            }
        }
        return true;
    }

    private static void flood() {
        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                if (right[y][x] == 1) {
                    List<P> points = new ArrayList<P>();
                    dfs(y, x, points, right);
                    List<List<P>> list = graph.get(points.size());
                    if (list == null) {
                        list = new ArrayList<List<P>>();
                        list.add(points);
                        graph.put(points.size(), list);
                    } else
                        list.add(points);
                }
            }
        }
    }

    private static void norm(List<P> list) {
        int len = list.size();
        for (int i = 0; i < len; i++) {
            int min = i;
            for (int j = i + 1; j < len; j++) {
                if (list.get(j).compareTo(list.get(min)) < 0) {
                    min = j;
                }
            }
            // swap
            swap(list, min, i);
        }
        // 排序之后,改成关于第一个点的相对位置
        P p = list.get(0);
        int x = p.x;
        int y = p.y;
        for (int i = 0; i < len; i++) {
            P pi = list.get(i);
            pi.x -= x;
            pi.y -= y;
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

    private static int[] dir_x = { 0, 1, 0, -1 };
    private static int[] dir_y = { 1, 0, -1, 0 };

    private static void dfs(int y, int x, List<P> points, int[][] map) {
        if (map[y][x] == 1) {
            P p = new P(x, y);
            points.add(p);
            map[y][x] = -1;
            for (int k = 0; k < 4; k++) {
                int dx = x + dir_x[k];
                int dy = y + dir_y[k];
                if (dx >= 0 && dx < W && dy >= 0 && dy < H) {
                    dfs(dy, dx, points, map);
                }
            }
        }
    }

    private static void init() {
        for (int i = 0; i < H; i++)
            for (int j = 0; j < W; j++)
                left[i][j] = right[i][j] = 0;
        graph.clear();
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