package com.poj;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * poj 1113 凸包
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main1113 {
    private static int N;
    private static int L;
    private static P[] vertices;
    private static List<P> stack = new ArrayList<P>();

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        N = cin.nextInt();
        L = cin.nextInt();
        vertices = new P[N];
        for (int i = 0; i < N; i++) {
            vertices[i] = new P();
            vertices[i].x = cin.nextInt();
            vertices[i].y = cin.nextInt();
        }
        solve();
    }

    private static void solve() {
        // 首先选取y坐标最小的点，如果y坐标相同，则选其中x坐标最小的，将它交换到vertices[0]
        swap_first();
        // 根据当前点与vertices[0]以及x轴的夹角排序,如果共线，距离近的优先
        sort();
        graham_scan();
        calc_result();
    }

    private static void calc_result() {
        double c = 2 * Math.PI * L;
        for (int i = 0, len = stack.size(); i < len; i++) {
            P p1 = stack.get(i);
            P p2 = i == len - 1 ? stack.get(0) : stack.get(i + 1);
            double d = Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
            c += d;
        }
        System.out.println(Math.round(c));
    }

    /**
     * <pre>
     * 按照排好的序 依次加入新点得到新的边
     * 如果和上一条边成左转关系就压栈继续
     * 如果右转就弹栈直到和栈顶两点的边成左转关系 压栈继续
     * </pre>
     */
    private static void graham_scan() {
        stack.add(vertices[0]);
        stack.add(vertices[1]);
        for (int i = 2; i < N; i++) {
            int m = stack.size();
            P p0 = stack.get(m - 2);
            P p1 = stack.get(m - 1);
            P p2 = vertices[i];
            int cp = cross_product(p0, p1, p2);
            if (cp >= 0) {
                stack.add(vertices[i]);
                continue;
            } else {
                stack.remove(m - 1);
                i--;
                continue;
            }
        }
    }

    /**
     * 根据当前点与vertices[0]以及x轴的夹角排序,如果共线，距离近的优先
     */
    private static void sort() {
        Arrays.sort(vertices, 1, N, new Comparator<P>() {
            // 比较p1,p2与p0的夹角
            @Override
            public int compare(P p1, P p2) {
                int cp = cross_product(vertices[0], p1, p2);
                if (cp == 0) {
                    int d1 = (p1.x - vertices[0].x) * (p1.x - vertices[0].x) + (p1.y - vertices[0].y)
                            * (p1.y - vertices[0].y);
                    int d2 = (p2.x - vertices[0].x) * (p2.x - vertices[0].x) + (p2.y - vertices[0].y)
                            * (p2.y - vertices[0].y);
                    if (d1 == d2)
                        return 0;
                    return d1 > d2 ? 1 : -1;
                }
                return cp > 0 ? -1 : 1;
            }
        });
    }

    /**
     * 首先选取y坐标最小的点，如果y坐标相同，则选其中x坐标最小的，将它交换到vertices[0]
     */
    private static void swap_first() {
        for (int i = 1; i < N; i++) {
            if (vertices[0].y > vertices[i].y || (vertices[0].y == vertices[i].y && vertices[0].x > vertices[i].x)) {
                swap(vertices[0], vertices[i]);
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

        public String toString() {
            return "(" + x + "," + y + ")";
        }
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

}
