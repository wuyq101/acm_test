package com.poj;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1026 置换群
 * 
 * @author wyq@palmdeal.com
 * @version 1.0
 */
public class Main1026 {
    private static int n;
    private static int[] p = new int[201];
    private static int[][] c = new int[201][201];
    private static int[] cycle = new int[201];

    private static int k;
    private static char[] m = new char[201];
    private static char[] cipher = new char[201];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        n = cin.nextInt();
        while (n != 0) {
            for (int i = 1; i <= n; i++)
                p[i] = cin.nextInt();
            cycle();
            cin.nextLine();
            while (true) {
                String line = cin.nextLine();
                int idx = line.indexOf(' ');
                if (idx <= 0)
                    break;
                k = Integer.valueOf(line.substring(0, idx));
                if (k == 0)
                    break;
                for (int i = 1; i <= n; i++) {
                    m[i] = ' ';
                    cipher[i] = ' ';
                }
                line.getChars(idx + 1, line.length(), m, 1);
                cipher();
            }
            System.out.println();
            n = cin.nextInt();
        }
    }

    // 求每个位置的循环
    private static void cycle() {
        for (int i = 1; i <= n; i++) {
            c[0][i] = i;
            cycle[i] = 0;
        }
        // 初始化
        for (int i = 2; i <= n; i++) {
            Arrays.fill(c[i], 0);
        }
        int cnt = 0;
        for (int i = 0; i < n && cnt < n; i++) {
            for (int j = 1; j <= n; j++) {
                c[i + 1][p[j]] = c[i][j];
                if (cycle[p[j]] == 0 && c[i + 1][p[j]] == p[j]) {
                    cnt++;
                    cycle[p[j]] = i + 1;
                }
            }
        }
    }

    private static void cipher() {
        for (int i = 1; i <= n; i++) {
            int t = k % cycle[i];
            cipher[i] = m[c[t][i]];
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++)
            sb.append(cipher[i]);
        System.out.println(sb.toString());
    }
}
