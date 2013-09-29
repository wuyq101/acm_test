package com.poj.enumeration;

import java.util.Scanner;

/**
 * <pre>
 *     http://poj.org/problem?id=1166
 * </pre>
 */
public class Main1166 {
    private static int[] a = new int[10];
    private static int[] b = new int[10];
    private static int[] c = new int[10];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        for (int i = 1; i <= 9; i++)
            a[i] = cin.nextInt();
        solve();
    }

    private static void solve() {
        int i;
        StringBuilder sb = new StringBuilder();
        for (b[1] = 0; b[1] <= 3; b[1]++)
            for (b[2] = 0; b[2] <= 3; b[2]++)
                for (b[3] = 0; b[3] <= 3; b[3]++)
                    for (b[4] = 0; b[4] <= 3; b[4]++)
                        for (b[5] = 0; b[5] <= 3; b[5]++)
                            for (b[6] = 0; b[6] <= 3; b[6]++)
                                for (b[7] = 0; b[7] <= 3; b[7]++)
                                    for (b[8] = 0; b[8] <= 3; b[8]++)
                                        for (b[9] = 0; b[9] <= 3; b[9]++) {
                                            c[1] = (a[1] + b[1] + b[2] + b[4]) % 4;
                                            c[2] = (a[2] + b[1] + b[2] + b[3] + b[5]) % 4;
                                            c[3] = (a[3] + b[2] + b[3] + b[6]) % 4;
                                            c[4] = (a[4] + b[1] + b[4] + b[5] + b[7]) % 4;
                                            c[5] = (a[5] + b[1] + b[3] + b[5] + b[7] + b[9]) % 4;
                                            c[6] = (a[6] + b[3] + b[5] + b[6] + b[9]) % 4;
                                            c[7] = (a[7] + b[4] + b[7] + b[8]) % 4;
                                            c[8] = (a[8] + b[5] + b[7] + b[8] + b[9]) % 4;
                                            c[9] = (a[9] + b[6] + b[8] + b[9]) % 4;
                                            if (c[1] + c[2] + c[3] + c[4] + c[5] + c[6] + c[7] + c[8] + c[9] == 0) {
                                                for (i = 0; i < b[1]; i++) sb.append("1 ");
                                                for (i = 0; i < b[2]; i++) sb.append("2 ");
                                                for (i = 0; i < b[3]; i++) sb.append("3 ");
                                                for (i = 0; i < b[4]; i++) sb.append("4 ");
                                                for (i = 0; i < b[5]; i++) sb.append("5 ");
                                                for (i = 0; i < b[6]; i++) sb.append("6 ");
                                                for (i = 0; i < b[7]; i++) sb.append("7 ");
                                                for (i = 0; i < b[8]; i++) sb.append("8 ");
                                                for (i = 0; i < b[9]; i++) sb.append("9 ");
                                                System.out.println(sb.toString());
                                                return;
                                            }
                                        }
    }
}
