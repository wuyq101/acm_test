package com.poj.geometry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * <pre>
 * 原题: http://poj.org/problem?id=1873
 * 题意：n棵树(2<=n<=15),每棵树的坐标x，y。价值v，长度l。砍掉其中的一些做成篱笆围住其他树。求满足条件时应该砍掉哪些树使得砍掉
 *      的树总价值最小。如果有多种方案砍掉的树总价值一样的话，选砍掉棵数最小的方案。
 * 分析：枚举砍掉的树，剩下的树求凸包，如果砍掉树的长度>=凸包周长即可，
 * </pre>
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main1873 {
    private static class P {
        int x;
        int y;
        int value;
        int len;
        boolean selected = false;

        public P() {
            // empty
        }

        public P(P p) {
            this.x = p.x;
            this.y = p.y;
            this.len = p.len;
            this.value = p.value;
            this.selected = p.selected;
        }

        public String toString() {
            return x + "," + y + " value=" + value + " len=" + len + " selected=" + selected;
        }
    }

    private static int n;
    private static P[] trees = new P[15];
    // 拷贝过来计算凸包的时候使用
    private static P[] points = new P[15];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        n = cin.nextInt();
        while (n != 0) {
            for (int i = 0; i < n; i++) {
                if (trees[i] == null)
                    trees[i] = new P();
                trees[i].x = cin.nextInt();
                trees[i].y = cin.nextInt();
                trees[i].value = cin.nextInt();
                trees[i].len = cin.nextInt();
            }
            solve();
            n = cin.nextInt();
        }
    }

    // 当前答案
    private static int cur_value = 0;
    private static int cur_len = 0;
    private static int cur_count = 0;

    // 最佳答案
    private static int best_value = 0;
    private static int best_count = 0;
    private static List<Integer> best_list = new ArrayList<Integer>();
    private static double extra = 0.0;

    private static void solve() {
        cur_len = 0;
        cur_value = 0;
        cur_count = 0;
        best_value = 200000;
        best_count = 20;
        dfs(0);
        // 所有都枚举了,打印答案
        System.out.printf("Forest %d\n", k++);
        StringBuilder sb = new StringBuilder("Cut these trees: ");
        for (int i = 0, len = best_list.size(); i < len; i++) {
            sb.append(best_list.get(i)).append(" ");
        }
        System.out.println(sb.substring(0, sb.length() - 1));
        System.out.printf("Extra wood: %.2f\n\n", extra);
    }

    private static int k = 1;

    private static void dfs(int i) {
        if (i == n) {
            return;
        }
        // 选中i
        trees[i].selected = true;
        cur_len += trees[i].len;
        cur_value += trees[i].value;
        cur_count += 1;
        if (cur_value < best_value || (cur_value == best_value && cur_count < best_count)) {
            // 计算剩余树的凸包长度
            double convex_length = convex_length();
            if (cur_len >= convex_length) {
                // 找到一个答案,比较答案
                if (cur_value < best_value || (cur_value == best_value && cur_count < best_count)) {
                    best_value = cur_value;
                    best_count = cur_count;
                    best_list.clear();
                    for (int j = 0; j < n; j++) {
                        if (trees[j].selected)
                            best_list.add(j + 1);
                    }
                    extra = cur_len - convex_length;
                }
            }
        }
        dfs(i + 1);
        // 不选中i
        trees[i].selected = false;
        cur_len -= trees[i].len;
        cur_value -= trees[i].value;
        cur_count -= 1;
        dfs(i + 1);
    }

    private static int N;

    private static double convex_length() {
        N = 0;
        for (int i = 0; i < n; i++) {
            if (!trees[i].selected) {
                points[N++] = new P(trees[i]);
            }
        }
        if (N == 1)
            return 0;
        // 返回线段长度
        if (N == 2)
            return Math.sqrt(distance(points[0], points[1])) * 2;
        // 先求凸包，然后计算长度
        swap_first();
        sort();
        graham_scan();
        int size = stack.size();
        double length = 0.0;
        for (int i = 0; i < size - 1; i++) {
            length += Math.sqrt(distance(stack.get(i), stack.get(i + 1)));
        }
        length += Math.sqrt(distance(stack.get(0), stack.get(size - 1)));
        return length;
    }

    private static List<P> stack = new ArrayList<P>();

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
    public static int cross_product(P p1, P p2, P p3) {
        return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
    }

    private static void swap_first() {
        for (int i = 1; i < N; i++) {
            if (points[0].y > points[i].y || (points[0].y == points[i].y && points[0].x > points[i].x)) {
                swap(points[0], points[i]);
            }
        }
    }

    private static void swap(P a, P b) {
        int temp = a.x;
        a.x = b.x;
        b.x = temp;
        temp = a.y;
        a.y = b.y;
        b.y = temp;
        temp = a.len;
        a.len = b.len;
        b.len = temp;
        temp = a.value;
        a.value = b.value;
        b.value = temp;
    }

    private static long distance(P a, P b) {
        return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
    }
}
