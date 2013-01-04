package com.poj;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * pku 3083
 * 
 * @author vanguard001
 * @version 1.0
 */
public class Main3083 {
    // 宽度
    private static int W;
    // 高度
    private static int H;
    // 迷宫
    private static char[][] maze;
    private static int[][] map;
    private static int start;
    private static int end;

    // 方向右下左上
    private static int[][] dir = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
    private static int[] path_dir = new int[40 * 40];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int num = cin.nextInt();
        for (int index = 0; index < num; index++) {
            // 读取迷宫
            readMaze(cin);
            // 向左走
            dfs_left(start, 1);
            // 向右走
            dfs_right(start, 1);
            // 最短走
            bfs();
        }
    }

    private static void readMaze(Scanner cin) {
        W = cin.nextInt();
        H = cin.nextInt();
        maze = new char[H][W];
        map = new int[H][W];
        for (int i = 0; i < H; i++) {
            String line = cin.next();
            for (int j = 0; j < W; j++) {
                maze[i][j] = line.charAt(j);
                if (maze[i][j] == 'S') {
                    start = i * W + j;
                }
                if (maze[i][j] == 'E') {
                    end = i * W + j;
                }
            }
        }
    }

    private static void init() {
        path.clear();
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (maze[i][j] == '#') {
                    map[i][j] = 9999;
                } else {
                    map[i][j] = 0;
                }
            }
        }
        path.add(start);
        path_dir[start] = 0;
    }

    // 深度优先
    private static int dfs_left(int cur, int count) {
        int row = cur / W;
        int col = cur % W;
        int i = (path_dir[cur] + 3) % 4;
        for (int k = 0; k < 4; k++, i = (i + 1) % 4) {
            int nrow = row + dir[i][0];
            int ncol = col + dir[i][1];
            int next = nrow * W + ncol;
            // 找到终点
            if (end == next) {
                System.out.printf("%d ", count + 1);
                return count + 1;
            }
            if (nrow >= 0 && nrow < H && ncol >= 0 && ncol < W && maze[nrow][ncol] == '.') {
                path_dir[next] = i;
                return dfs_left(next, count + 1);
            }
        }
        return 0;
    }

    private static int dfs_right(int cur, int count) {
        int row = cur / W;
        int col = cur % W;
        int i = (path_dir[cur] + 1) % 4;
        for (int k = 0; k < 4; k++, i = (i + 3) % 4) {
            int nrow = row + dir[i][0];
            int ncol = col + dir[i][1];
            int next = nrow * W + ncol;
            // 找到终点
            if (end == next) {
                System.out.printf("%d ", count + 1);
                return count + 1;
            }
            if (nrow >= 0 && nrow < H && ncol >= 0 && ncol < W && maze[nrow][ncol] == '.') {
                path_dir[next] = i;
                return dfs_right(next, count + 1);
            }
        }
        return 0;
    }

    private static List<Integer> path = new ArrayList<Integer>();

    // 广度优先搜索,最短路劲
    private static void bfs() {
        init();
        int[] offsetR = { 0, 1, 0, -1 };
        int[] offsetC = { 1, 0, -1, 0 };
        // 开始点设置为1
        int start = path.get(0);
        map[start / W][start % W] = 1;
        while (!path.isEmpty()) {
            int index = path.remove(0);
            int row = index / W;
            int col = index % W;
            // System.out.println(row+","+col);
            if (maze[row][col] == 'E') {
                System.out.printf("%d \n", map[row][col]);
                return;
            }
            for (int i = 0; i < 4; i++) {
                int r = row + offsetR[i];
                int c = col + offsetC[i];
                if (r >= 0 && r < H && c >= 0 && c < W && maze[r][c] != '#' && map[r][c] == 0) {
                    path.add(r * W + c);
                    map[r][c] = map[row][col] + 1;
                }
            }

        }
    }

}
