package com.poj.simulate;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 2013
 */
public class Main2013 {
    private static int N;
    private static String[] s = new String[15];

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        N = cin.nextInt();
        int k = 1;
        while (N != 0) {
            for (int i = 0; i < N; i++) {
                s[i] = cin.next();
            }
            System.out.printf("SET %d\n", k++);
            solve();
            N = cin.nextInt();
        }
    }

    private static void solve() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i += 2) {
            sb.append(s[i]).append("\n");
        }
        int j = N % 2 == 0 ? N - 1 : N - 2;
        for (int i = j; i >= 1; i -= 2) {
            sb.append(s[i]).append("\n");
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb.toString());
    }

}
