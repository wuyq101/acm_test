package com.poj;
import java.util.Scanner;

/**
 * 3983
 * 
 * @author wyq
 * @version 1.0
 */
public class Main3983 {
    // 运算符号
    private static char[] op = { '+', '-', '*', '/' };

    private static double cal(double a, int operator, double b) {
        switch (op[operator]) {
        case '+':
            return a + b;
        case '-':
            return a - b;
        case '*':
            return a * b;
        case '/':
            return a / b;
        }
        return 0.0;
    }

    /**
     * (a b) (c d) ((a b) c) d) (a (b c)) d a((b c) d) a (b (c d))
     */
    private static boolean isValid(int i, int j, int k, int a, int b, int c, int d) {
        // (a b) (c d)
        if (cal(cal(a, i, b), j, cal(c, k, d)) - 24 == 0) {
            System.out.printf("(%d%c%d)%c(%d%c%d)", a, op[i], b, op[j], c, op[k], d);
            return true;
        }
        // ((a b) c) d
        if (cal(cal(cal(a, i, b), j, c), k, d) - 24 == 0) {
            System.out.printf("((%d%c%d)%c%d)%c%d", a, op[i], b, op[j], c, op[k], d);
            return true;
        }
        // (a (b c)) d
        if (cal(cal(a, i, cal(b, j, c)), k, d) - 24 == 0) {
            System.out.printf("(%d%c(%d%c%d))%c%d", a, op[i], b, op[j], c, op[k], d);
            return true;
        }
        // a ((b c) d)
        if (cal(a, i, cal(cal(b, j, c), k, d)) - 24 == 0) {
            System.out.printf("%d%c((%d%c%d)%c%d)", a, op[i], b, op[j], c, op[k], d);
            return true;
        }
        // a (b (c d))
        if (cal(a, i, cal(b, j, cal(c, k, d))) - 24 == 0) {
            System.out.printf("%d%c(%d%c(%d%c%d))", a, op[i], b, op[j], c, op[k], d);
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int a = cin.nextInt();
        int b = cin.nextInt();
        int c = cin.nextInt();
        int d = cin.nextInt();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    if (isValid(i, j, k, a, b, c, d)) {
                        return;
                    }
                }
            }
        }
    }

}
