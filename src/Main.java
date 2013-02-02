import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * 2007, 将凸多边形根据极角排序
 * User: wuyq101
 * Date: 13-2-2
 * Time: 下午10:45
 */
public class Main {
    private static class Point {
        int x, y;
    }

    private static Point[] points = new Point[50];
    private static int n;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int n = 0;
        Point p;
        while (cin.hasNextInt()) {
            p = new Point();
            p.x = cin.nextInt();
            p.y = cin.nextInt();
            points[n++] = p;
        }
        Arrays.sort(points, 1, n, new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {
                int cp = a.x * b.y - a.y * b.x;
                if (cp == 0)
                    return 0;
                return cp > 0 ? -1 : 1;
            }
        });
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append("(").append(points[i].x).append(",").append(points[i].y).append(")\n");
        }
        System.out.print(sb.toString());
    }
}
