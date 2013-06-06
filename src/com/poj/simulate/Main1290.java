package com.poj.simulate;
import java.util.Scanner;

/**
 * 1290 模拟 魔方
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main1290 {
    private static char[][][] cube = new char[6][3][3];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int t = cin.nextInt();
        while (t-- > 0) {
            // top
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    cube[4][i][j] = cin.next().charAt(0);
            // left, front, right, back
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 12; j++)
                    cube[j / 3][i][j % 3] = cin.next().charAt(0);
            // bottom
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    cube[5][i][j] = cin.next().charAt(0);
            while (true) {
                String tn = cin.next();
                if ("0".equals(tn)) {
                    verify();
                    break;
                }
                int d = tn.startsWith("+") ? 1 : -1;
                int s = tn.charAt(1) - '1';
                turn(s, d);
            }
        }
    }

    private static void verify() {
        for (int s = 0; s < 6; s++) {
            char ch = cube[s][0][0];
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    if (cube[s][i][j] != ch) {
                        System.out.println("No, you are wrong!");
                        return;
                    }
        }
        System.out.println("Yes, grandpa!");
    }

    private static void turn(int s, int d) {
        // front
        // 复制到临时面
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                tmp[i][j] = cube[s][i][j];
        // 顺时针
        if (d == 1) {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    cube[s][i][j] = tmp[2 - j][i];
        } else {
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    cube[s][i][j] = tmp[j][2 - i];
        }
        // 拷贝
        for (int k = 0; k < 12; k++) {
            int side = sides[s][order[k / 3]];
            int i = col[s][k] / 3;
            int j = col[s][k] % 3;
            c[k] = cube[side][i][j];
        }
        // 旋转
        for (int k = 0; k < 12; k++) {
            int side = sides[s][order[k / 3]];
            int i = col[s][k] / 3;
            int j = col[s][k] % 3;
            int idx = d == 1 ? (((k - 3) % 12 + 12) % 12) : (((k + 3) % 12 + 12) % 12);
            cube[side][i][j] = c[idx];
        }

    }

    private static char[][] tmp = new char[3][3];

    private static int[][] sides = { { 3, 0, 1, 2, 4, 5 },// 0
            { 0, 1, 2, 3, 4, 5 }, // 1
            { 1, 2, 3, 0, 4, 5 },// 2
            { 2, 3, 0, 1, 4, 5 },// 3
            { 0, 4, 2, 5, 3, 1 },// 4
            { 0, 5, 2, 4, 1, 3 } // 5
    };

    private static int[] order = { 0, 4, 2, 5 };

    private static char[] c = new char[12];

    // left, top, right,bottom
    private static int[][] col = { { 8, 5, 2, 0, 3, 6, 0, 3, 6, 0, 3, 6 },// 0
            { 8, 5, 2, 6, 7, 8, 0, 3, 6, 2, 1, 0 },// 1
            { 8, 5, 2, 8, 5, 2, 0, 3, 6, 8, 5, 2 },// 2
            { 8, 5, 2, 2, 1, 0, 0, 3, 6, 6, 7, 8 },// 3
            { 2, 1, 0, 2, 1, 0, 2, 1, 0, 2, 1, 0 },// 4
            { 6, 7, 8, 6, 7, 8, 6, 7, 8, 6, 7, 8 },// 5
    };
}