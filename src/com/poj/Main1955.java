package com.poj;
import java.util.Scanner;

/**
 * 1955 模拟魔方
 * 
 * <pre>
 * left '0', front '1', right '2', back '3', top '4', bottom '5'
 * </pre>
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main1955 {
    private static char[][][] cube = new char[6][3][3];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int num = cin.nextInt();
        int idx = 0;
        while (num-- > 0) {
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
            int t = cin.nextInt();
            while (t-- > 0) {
                int s = cin.nextInt();
                int d = cin.nextInt();
                turn(s, d);
            }
            System.out.printf("Scenario #%d:\n", ++idx);
            print();
        }
    }

    private static void print() {
        StringBuilder sb = new StringBuilder();
        // top
        for (int i = 0; i < 3; i++) {
            sb.append("     ");
            for (int j = 0; j < 3; j++) {
                sb.append(' ').append(cube[4][i][j]);
            }
            sb.append('\n');
        }
        // left, front, right, back
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 12; j++) {
                sb.append(cube[j / 3][i][j % 3]).append(' ');
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append('\n');
        }
        // bottom
        for (int i = 0; i < 3; i++) {
            sb.append("     ");
            for (int j = 0; j < 3; j++) {
                sb.append(' ').append(cube[5][i][j]);
            }
            sb.append('\n');
        }
        sb.append('\n');
        System.out.printf(sb.toString());
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
