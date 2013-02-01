import java.util.*;

/**
 * <pre>
 * 原题:http://poj.org/problem?id=1228
 * 题意：给出多边形一些边上的点，求这些点能否唯一确定原来多边形的形状
 * 分析：对给出的点求凸包，如果凸包上每条边至少包含原多边形3个点，可以确定原来多边形形状
 *           所有点都共线的话，不能确定
 *           至少要有6个点才能确定，所以n<=5是不可能确定的。
 * </pre>
 * User: wuyq101
 * Date: 13-1-31
 * Time: 下午7:02
 */
public class Main {
    private static class Point {
        int x, y;
    }

    private static int n;
    private static Point[] points = new Point[1000];
    private static List<Point> stack = new ArrayList<Point>();

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int t = cin.nextInt();
        while (t-- > 0) {
            n = cin.nextInt();
            for (int i = 0; i < n; i++) {
                if (points[i] == null) {
                    points[i] = new Point();
                }
                points[i].x = cin.nextInt();
                points[i].y = cin.nextInt();
            }
            solve();
        }
    }

    private static void solve() {
        if (n <= 5) {
            System.out.printf("NO\n");
            return;
        }
        //求凸包
        graham_scan(points, n);
        System.out.printf(calc_result() ? "YES\n" : "NO\n");
    }

    /**
     * 检查是否每条边上都有>=3个顶点
     */
    private static boolean calc_result() {
        int len = stack.size();
        int edge_cnt = 0;
        for (int i = 0; i < len; i++) {
            Point a = stack.get(i);
            Point b = i == len - 1 ? stack.get(0) : stack.get(i + 1);
            //一条新的边
            int num = 2;
            edge_cnt++;
            for (int j = 0; j < n; j++) {
                if (points[j] != a && points[j] != b && cross_product(a, b, points[j]) == 0) {
                    num++;
                    break;
                }
            }
            if (num < 3) {
                return false;
            }
        }
        //判断所有点是否再同一个边上
        return true;
    }

    private static void graham_scan(Point[] points, int n) {
        // 首先选取y坐标最小的点，如果y坐标相同，则选其中x坐标最小的，将它交换到vertices[0]
        swap_first(points, n);
        // 根据当前点与vertices[0]以及x轴的夹角排序,如果共线，距离近的优先
        sort(points, n);
        stack.clear();
        stack.add(points[0]);
        stack.add(points[1]);
        for (int i = 2; i < n; i++) {
            int m = stack.size();
            Point p0 = stack.get(m - 2);
            Point p1 = stack.get(m - 1);
            Point p2 = points[i];
            int cp = cross_product(p0, p1, p2);
            if (cp >= 0) {
                stack.add(points[i]);
            } else {
                stack.remove(m - 1);
                i--;
            }
        }
    }

    /**
     * 根据当前点与vertices[0]以及x轴的夹角排序,如果共线，距离近的优先
     */
    private static void sort(final Point[] points, int n) {
        Arrays.sort(points, 1, n, new Comparator<Point>() {
            // 比较p1,p2与p0的夹角
            @Override
            public int compare(Point p1, Point p2) {
                int cp = cross_product(points[0], p1, p2);
                if (cp == 0) {
                    int d1 = distance(points[0], p1);
                    int d2 = distance(points[0], p2);
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
    private static void swap_first(Point[] points, int n) {
        for (int i = 1; i < n; i++) {
            if (points[0].y > points[i].y || (points[0].y == points[i].y && points[0].x > points[i].x)) {
                swap(points[0], points[i]);
            }
        }
    }

    private static void swap(Point a, Point b) {
        int temp = a.y;
        a.y = b.y;
        b.y = temp;
        temp = a.x;
        a.x = b.x;
        b.x = temp;
    }

    /**
     * <pre>
     * 两个向量A（x2-x1,y2-y1）,B (x3-x1,y3-y1)的叉积cp。
     * 1. cp的一半是一个三角型的有向面积
     * 2. cp的符号代表向量旋转的方向，如果向量A乘以向量B，如果为正，则A逆时针转向B，否则为顺时针
     *    如果为0，则共线
     * </pre>
     */
    private static int cross_product(Point p1, Point p2, Point p3) {
        return (p2.x - p1.x) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.x - p1.x);
    }

    private static int distance(Point a, Point b) {
        return (a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y);
    }
}
