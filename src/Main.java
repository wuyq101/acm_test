import java.util.Scanner;

/**
 * <pre>
 * 原题：http://poj.org/problem?id=1271
 * 题意：有一多边形，沿着某一条边往内部平行移动h的距离，切一刀，最多切k次，求最多能切多少面积。
 * 分析：暴力枚举沿着哪些边切，然后计算切了之后剩余的多边形面积，原来面积减去剩余面积即为切除的面积，取最大值。
 *       使用半平面交来求边平移之后构成的新多边形。
 * User: wuyq101
 * Date: 13-1-12
 * Time: 下午9:38
 * </pre>
 */
public class Main {
    private static int n, k, h;
    private static Polygon bread;
    private static double area;
    private static final double eps = 1e-8;
    private static final int N = 21;
    //每条边的平行点，中间结果，仅计算一次
    private static Point[][] p_p = new Point[N][2];


    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (true) {
            n = cin.nextInt();
            k = cin.nextInt();
            h = cin.nextInt();
            if (n == 0 && k == 0 && h == 0)
                break;
            bread = new Polygon();
            bread.n = n;
            for (int i = 0; i < n; i++) {
                bread.p[i] = new Point(cin.nextInt(), cin.nextInt());
            }
            bread.p[n] = bread.p[0];
            solve();
        }
    }

    private static void solve() {
        if (k == 0 || h == 0) {
            System.out.println("0.00");
            return;
        }
        for (int i = 0; i < n; i++) {
            p_p[i][0] = null;
            p_p[i][1] = null;
        }
        Polygon polygon = new Polygon(bread);
        area = Double.MAX_VALUE;
        dfs(polygon, 0, 0);
        System.out.printf("%.2f\n", getArea(bread) - area);
    }

    private static void dfs(Polygon polygon, int count, int j) {
        if (count == k || k > n && count == n) {
            //已经使用了k个动作，或者每条边都已经被枚举，开始计算面积
            double cur = getArea(polygon);
            area = Math.min(cur, area);
            return;
        }
        for (int i = j; i < n; i++) {
            //将i，i+1平移到p1，p2上
            translate(i);
            Polygon temp = new Polygon(polygon);
            temp = cut_polygon(temp, p_p[i][0],p_p[i][1]);
            dfs(temp, count + 1, i + 1);
        }
    }

    private static void translate(int i){
        if(p_p[i][0]!=null)
            return;
        p_p[i][0] = new Point();
        p_p[i][1] = new Point();
        translate(bread.p[i], bread.p[i + 1],p_p[i][0],p_p[i][1]);
    }

    /**
     * 使用经过点a和点b的直线去切割多边形
     *
     * @param polygon
     * @param a
     * @param b
     */
    private static Polygon cut_polygon(Polygon polygon, Point a, Point b) {
        Polygon result = new Polygon();
        for (int i = 0; i < polygon.n; i++) {
            double cp1 = cross_product(a, b, polygon.p[i]);
            double cp2 = cross_product(a, b, polygon.p[i + 1]);
            //a，b，p[i]在ab线的上方,p[i]点继续保留
            if (cp1 >= 0) {
                result.p[result.n++] = new Point(polygon.p[i].x, polygon.p[i].y);
            }
            //边i和直线ab相交
            if (cp1 * cp2 < 0) {
                Point p = intersect(getLine(a, b), getLine(polygon.p[i], polygon.p[i + 1]));
                result.p[result.n++] = p;
            }
        }
        result.p[result.n] = result.p[0];
        return result;
    }

    /**
     * 返回两条直线的交点
     *
     * @param line1
     * @param line2
     * @return
     */
    private static Point intersect(Line line1, Line line2) {
        Point p = new Point();
        double d = line1.a * line2.b - line2.a * line1.b;
        p.x = (line2.c * line1.b - line1.c * line2.b) / d;
        p.y = (line1.c * line2.a - line2.c * line1.a) / d;
        return p;
    }

    private static Line getLine(Point a, Point b) {
        Line line = new Line();
        line.a = a.y - b.y;
        line.b = b.x - a.x;
        line.c = a.x * b.y - b.x * a.y;
        return line;
    }

    /**
     * 向量ab 向量ac的叉积
     *
     * @param a
     * @param b
     * @param c
     * @return
     */
    public static double cross_product(Point a, Point b, Point c) {
        return (b.x - a.x) * (c.y - a.y) - (c.x - a.x) * (b.y - a.y);
    }

    private static void translate(Point a, Point b, Point c, Point d) {
        //垂直于向量ab的向量v
        Point v = new Point(a.y - b.y, b.x - a.x);
        double t = h / Math.sqrt(v.x * v.x + v.y * v.y);
        c.x = a.x + t * v.x;
        c.y = a.y + t * v.y;
        d.x = b.x + t * v.x;
        d.y = b.y + t * v.y;
    }

    /**
     * 计算多边形的面积
     */
    private static double getArea(Polygon polygon) {
        if (polygon.n < 3)
            return 0;
        Point p = new Point(0, 0);
        double area = 0;
        for (int i = 0; i < polygon.n; i++) {
            area += cross_product(p, polygon.p[i], polygon.p[i + 1]);
        }
        return area / 2;
    }

    private static class Point {
        double x, y;

        public Point() {
        }

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }

    /**
     * ax+by+c=0
     */
    private static class Line {
        double a, b, c;
    }

    private static class Polygon {
        int n;
        Point[] p = new Point[N];

        public Polygon() {
        }

        public Polygon(Polygon polygon) {
            this.n = polygon.n;
            for (int i = 0; i < n; i++) {
                p[i] = new Point(polygon.p[i].x, polygon.p[i].y);
            }
            p[n] = p[0];
        }
    }
}
