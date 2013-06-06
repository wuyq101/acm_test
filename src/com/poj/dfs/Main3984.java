package com.poj.dfs;
import java.util.Scanner;

public class Main3984 {
    private static int[][] maze = new int[5][5];
    private static int[][] map = new int[5][5];
    private static int UNREACHABLE = 100;

    /**
     * @param args
     */
    public static void main(String[] args) {
        // 读取迷宫数据
        Scanner cin = new Scanner(System.in);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                maze[i][j] = cin.nextInt();
            }
        }
        solve(4, 4, 0);
        print(0, 0);
    }

    public static void solve(int i, int j, int step) {
        if (maze[i][j] == 1) {
            map[i][j] = UNREACHABLE;
            return;
        }
        if (map[i][j] == 0 || step + 1 < map[i][j]) {
            map[i][j] = step + 1;
            if (i + 1 < 5) {
                solve(i + 1, j, map[i][j]);
            }
            if (i - 1 >= 0) {
                solve(i - 1, j, map[i][j]);
            }
            if (j + 1 < 5) {
                solve(i, j + 1, map[i][j]);
            }
            if (j - 1 >= 0) {
                solve(i, j - 1, map[i][j]);
            }
        }
    }

    public static void print(int i, int j) {
        if (i == 4 && j == 4) {
            System.out.printf("(%d, %d)", i, j);
            return;
        }
        System.out.printf("(%d, %d)\n", i, j);
        map[i][j] = UNREACHABLE;
        int a = UNREACHABLE, b = UNREACHABLE, c = UNREACHABLE, d = UNREACHABLE;
        if (i + 1 < 5) {
            a = map[i + 1][j];
        }
        if (i - 1 >= 0) {
            b = map[i - 1][j];
        }
        if (j + 1 < 5) {
            c = map[i][j + 1];
        }
        if (j - 1 >= 0) {
            d = map[i][j - 1];
        }
        int min, min_i, min_j;
        if (a < b) {
            min_i = i + 1;
            min_j = j;
            min = a;
        } else {
            min_i = i - 1;
            min_j = j;
            min = b;
        }
        if (c < min) {
            min_i = i;
            min_j = j + 1;
            min = c;
        }
        if (d < min) {
            min_i = i;
            min_j = j - 1;
            min = d;
        }
        print(min_i, min_j);
    }

}
