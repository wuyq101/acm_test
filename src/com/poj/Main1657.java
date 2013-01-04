package com.poj;
import java.util.Scanner;

/**
 * 1657
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main1657 {
    private static int x1;
    private static int y1;
    private static int x2;
    private static int y2;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int t = cin.nextInt();
        String src = null;
        String des = null;
        while (t-- > 0) {
            src = cin.next();
            x1 = 8 - (src.charAt(0) - 'a');
            y1 = src.charAt(1) - '0';
            des = cin.next();
            x2 = 8 - (des.charAt(0) - 'a');
            y2 = des.charAt(1) - '0';
            solve();
        }
    }

    private static void solve() {
        if (x1 == x2 && y1 == y2) {
            System.out.println("0 0 0 0");
            return;
        }
        // 王,横、直、斜都可以走，但每步限走一格
        int a = Math.abs(x1 - x2) > Math.abs(y1 - y2) ? Math.abs(x1 - x2) : Math.abs(y1 - y2);
        // 后,横、直、斜都可以走，每步格数不受限制
        int b = 2;
        if (x1 == x2 || y1 == y2 || Math.abs(x1 - x2) == Math.abs(y1 - y2)) {
            b = 1;
        }
        // 车,横、竖均可以走，不能斜走，格数不限
        int c = 2;
        if (x1 == x2 || y1 == y2) {
            c = 1;
        }
        // 象,只能斜走，格数不限,黑格只能走到黑格
        // 判断x1，y1是否和x2，y2同颜色
        // 白色判断条件，如果x是奇数，y也是奇数，x是偶数，y也是偶数
        if ((x1 % 2 == y1 % 2) == (x2 % 2 == y2 % 2)) {
            int d = 2;
            if (Math.abs(x1 - x2) == Math.abs(y1 - y2))
                d = 1;
            System.out.printf("%d %d %d %d\n", a, b, c, d);
        } else {
            System.out.printf("%d %d %d Inf\n", a, b, c);
        }
    }

}
