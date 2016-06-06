package com.poj.bfs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.StringTokenizer;

/**
 * http://poj.org/problem?id=3182
 * 找到树丛最上最左的点(x,y)，然后划一条线从(x,0) --> (x,y)，
 * bfs搜索从起点出发的最短路径的时候，中间经过的路线不能越过这条线
 * 然后沿着线上的点，挑选两边的结果，使得相加的和最小。
 * Created by wuyq on 16/6/4.
 */
public class Main3182 {
    static int R, C;
    static char[][] map;
    static int sr, sc; //起点
    static int x = -1, y = -1; //树丛最上，最左的那个点
    static int[][] d;
    static int[] dr = {0, 1, 1, 1, 0, -1, -1, -1};
    static int[] dc = {1, 1, 0, -1, -1, -1, 0, 1};

    public static void main(String[] args) throws Exception {
        input();
        solve();
        output();
    }

    private static void solve() {
        d = new int[R][C];
        LinkedList<Integer> listx = new LinkedList<Integer>();
        LinkedList<Integer> listy = new LinkedList<Integer>();
        LinkedList<Integer> steps = new LinkedList<Integer>();
        listx.addLast(sr);
        listy.addLast(sc);
        steps.addLast(1);
        d[sr][sc] = 1;
        while (!listx.isEmpty()) {
            int r = listx.removeFirst();
            int c = listy.removeFirst();
            int step = steps.removeFirst();
            for (int i = 0; i < dc.length; i++) {
                int r1 = r + dr[i];
                int c1 = c + dc[i];
                if (r1 >= 0 && r1 < R && c1 >= 0 && c1 < C && d[r1][c1] == 0 && map[r1][c1] == '.') {
                    //检查 （r,c） --> （r1, c2)是否穿越了线(x,0) --> (x,y);
                    //往下穿越不让走
                    if (c < y && r == x && r1 == x + 1) {
                        continue;
                    }
                    //往上穿越的，也不让走
                    if (c1 < y && r1 == x && r == x + 1) {
                        continue;
                    }
                    d[r1][c1] = step + 1;
                    listx.addLast(r1);
                    listy.addLast(c1);
                    steps.addLast(step + 1);
                }
            }
        }
    }

    private static void output() {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < y; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i + j >= 0 && i + j < C && map[x + 1][i + j] == '.') {
                    min = Math.min(min, d[x][i] + d[x + 1][i + j] - 1);
                }
            }
        }
        System.out.println(min);
    }

    private static void input() throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(cin.readLine());
        R = Integer.parseInt(tokenizer.nextToken());
        C = Integer.parseInt(tokenizer.nextToken());
        map = new char[R][C];
        for (int i = 0; i < R; i++) {
            String s = cin.readLine();
            for (int j = 0; j < C; j++) {
                map[i][j] = s.charAt(j);
                if (map[i][j] == '*') {
                    sr = i;
                    sc = j;
                    map[i][j] = '.';
                }
                if (map[i][j] == 'X' && x == -1) {
                    x = i;
                    y = j;
                }
            }
        }
    }
}
