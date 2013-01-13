import java.util.Scanner;

/**
 * <pre>
 * User: wuyq101
 * Date: 13-1-12
 * Time: 下午9:38
 * </pre>
 */
public class Main {
    private static int n;
    private static int k;
    private static int h;
    private static Polygon bread = new Polygon();
    private static double area;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        n = cin.nextInt();
        k = cin.nextInt();
        h = cin.nextInt();
        while (n != 0 && k != 0 && h != 0) {
            bread.n = n;
            for (int i = 0; i < n; i++) {
                if (bread.p[i] == null) {
                    bread.p[i] = new Point();
                }
                bread.p[i].x = cin.nextDouble();
                bread.p[i].y = cin.nextDouble();
            }
            solve();
        }
    }

    private static void solve() {
        if (k == 0 || h == 0) {
            System.out.printf("%.2f\n", 0.0);
            return;
        }
        k = k>=n ? n : k;
        Polygon polygon = bread;
        area  = Double.MAX_VALUE;
        dfs(polygon, 0, 0);
    }

    private static void dfs(Polygon polygon, int count, int j){
        if(count==k){
            //已经使用了k个动作，开始计算面积
            double temp = getArea(polygon);
            area = Math.min(temp, area);
            return;
        }
        for(int i=j; i<n; i++){
            Point p1 = new Point();
            Point p2 = new Point();
            translate(polygon.p[i],polygon.p[i+1],p1,p2);
        }
    }

    private static void translate(Point a, Point b, Point c, Point d) {

    }

    /**
     * 计算多边形的面积
     */
    private static double getArea(Polygon polygon){
        return 0;
    }

    private static class Point {
        double x, y;
    }

    private static class Line {
        double a, b, c;
    }

    private static class Polygon {
        int n;
        Point[] p = new Point[20];
    }
}
