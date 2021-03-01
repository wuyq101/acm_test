package com.poj.simulate;

import java.util.Scanner;

/**
 * http://poj.org/problem?id=1027
 * Created by wuyq on 2021/2/27.
 */
public class Main1027 {
    private static int R = 10;
    private static int C = 15;
    private static char[][] game = new char[10][15];
    private static char[][] copy = new char[10][15];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int t = cin.nextInt();
        for (int k = 1; k <= t; k++) {
            System.out.printf("Game %d:\n\n", k);

            for (int i = R - 1; i >= 0; i--) {
                String line = cin.next();
//                System.out.println(line);
                for (int j = 0; j < C; j++) {
                    game[i][j] = line.charAt(j);
                }
            }
            solve();
        }
    }

    private static int left = 0;

    private static void solve() {
        int move = 0;
        int score = 0;
        left = R * C;
        while (true) {
            makeCopy();
            maxCluster = 0;
            maxI = -1;
            maxJ = -1;
            // find a move
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    char color = copy[i][j];
                    if (color == 0) {
                        continue;
                    }
                    curCluster = 0;
                    curI = i;
                    curJ = j;
                    dfs(i, j, color);
                    if (curCluster > maxCluster) {
                        maxCluster = curCluster;
                        maxI = curI;
                        maxJ = curJ;
                    }
                    if (curCluster == maxCluster) {
                        if (curJ < maxJ || curJ == maxJ && curI < maxI) {
                            maxI = curI;
                            maxJ = curJ;
                        }
                    }
                }
            }
            if (maxCluster < 2) {
                break;
            }
            // remove, output score
            char color = game[maxI][maxJ];
            System.out.printf("Move %d at (%d,%d): removed %d balls of color %c, got %d points. \n",
                    ++move,
                    maxI + 1, maxJ + 1,
                    maxCluster,
                    color,
                    (maxCluster - 2) * (maxCluster - 2));
            score += (maxCluster - 2) * (maxCluster - 2);
            left -= maxCluster;
            remove(maxI, maxJ, color);
//            System.out.println("remove");
//            print();
            // shift
            shiftDown();
//            System.out.println("shift down");
//            print();
            shiftLeft();
//            System.out.println("shift left");
//            print();

        }
        //output final score
        if (left == 0) {
            score += 1000;
        }
        System.out.printf("Final score: %d, with %d balls remaining. \n\n", score, left);
    }

    private static void print() {
        System.out.println("**************");
        StringBuilder sb = new StringBuilder();
        for (int i = R - 1; i >= 0; i--) {
            for (int j = 0; j < C; j++) {
                if (game[i][j] == 0) {
                    sb.append(' ');
                } else {
                    sb.append(game[i][j]);
                }
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
        System.out.println("**************");
    }

    private static void shiftLeft() {
        int totalCol = C;
        for (int c = 0; c < totalCol; c++) {
            boolean empty = true;
            for (int r = 0; r < R; r++) {
                if (game[r][c] != 0) {
                    empty = false;
                    break;
                }
            }
            if (!empty) {
                continue;
            }
            //c之后的每一列都往左边移动一格
            for (int i = 0; i < R; i++) {
                for (int j = c; j < C - 1; j++) {
                    game[i][j] = game[i][j + 1];
                }
                game[i][C - 1] = 0;
            }
            c--;
            totalCol--;
        }
    }

    private static void shiftDown() {
        //针对每一列
        for (int c = 0; c < C; c++) {
            int idx = 0;
            for (int r = 0; r < R; r++) {
                if (game[r][c] != 0) {
                    game[idx++][c] = game[r][c];
                }
            }
            for (int r = idx; r < R; r++) {
                game[r][c] = 0;
            }
        }
    }

    private static int maxCluster = 0;
    private static int maxI = 0;
    private static int maxJ = 0;
    private static int curCluster = 0;
    private static int curI = 0;
    private static int curJ = 0;

    private static void dfs(int i, int j, char color) {
        copy[i][j] = 0;
        curCluster++;
        if (j < curJ || (j == curJ && i < curI)) {
            curI = i;
            curJ = j;
        }

        for (int k = 0; k < dir.length; k++) {
            int r = i + dir[k][0];
            int c = j + dir[k][1];
            if (r >= 0 && r < R && c >= 0 && c < C && copy[r][c] == color) {
                dfs(r, c, color);
            }
        }
    }

    private static void remove(int i, int j, char color) {
        game[i][j] = 0;
        for (int k = 0; k < dir.length; k++) {
            int r = i + dir[k][0];
            int c = j + dir[k][1];
            if (r >= 0 && r < R && c >= 0 && c < C && game[r][c] == color) {
                remove(r, c, color);
            }
        }
    }

    private static int[][] dir = new int[][]{
            {-1, 0},
            {+1, 0},
            {0, +1},
            {0, -1}
    };


    private static void makeCopy() {
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                copy[i][j] = game[i][j];
            }
        }
    }

}