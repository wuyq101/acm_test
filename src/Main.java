import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 原题：http://poj.org/problem?id=1654
 * 题意：求多边形面积
 * User: wuyq101
 * Date: 13-1-24
 * Time: 下午9:32
 */
public class Main {
    private static class Point {
        int x, y;
    }

    private static final int MAX = 1000001;
    private static Point[] polygon = new Point[MAX];
    private static int[][] direction = {{0, 0}, {-1, -1}, {0, -1}, {1, -1}, {-1, 0}, {0, 0}, {1, 0}, {-1, 1}, {0, 1}, {1, 1}};

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int t = cin.nextInt();
        while (t-- > 0) {
            String s = cin.next();
            solve(s);
        }
    }

    private static void solve(String s) {
        int n = 1;
        if (polygon[0] == null) {
            polygon[0] = new Point();
            polygon[0].x = 0;
            polygon[0].y = 0;
        }
        int x = 0, y = 0, idx;
        char pre = s.charAt(0), ch;
        for (int i = 1, len = s.length() - 1; i < len; i++) {
            ch = s.charAt(i);
            if (ch != pre) {
                if (polygon[n] == null) {
                    polygon[n] = new Point();
                }
                polygon[n].x = x;
                polygon[n].y = y;
                n++;
            }
            idx = ch - '0';
            x += direction[idx][0];
            y += direction[idx][1];
        }
    }
}
