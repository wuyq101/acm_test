package com.poj.dijkstra;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * <pre>
 *     http://poj.org/problem?id=2312
 *     题意：在矩形图中找从s到t的最短距离，中间有街道墙（不可通过），砖墙（可用炮弹攻击掉），
 *     河流（无法通过得绕路），空地（可直接通过）。4个行走方向，4个炮弹方向。
 *     分析：设空地每个点的权重为1，河流与墙权重为无穷大，砖头为2（到达+炮弹）。这样就变成了一张连通的图，
 *     在图中求最短路径，可以使用dijstra来求。
 *
 * </pre>
 * User: wuyq101
 * Date: 13-6-6
 * Time: 下午1:26
 */
public class Main2312 {
    private static int M, N, MAX = 300;
    private static int start_x, start_y, end_x, end_y;
    private static int[][] dist = new int[MAX][MAX];
    private static int[][] map = new int[MAX][MAX];
    private static int[] v = new int[27];
    private static int[][] dir = {{-1, 0}, {0, -1}, {0, 1}, {1, 0}};

    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        v['Y' - 'A'] = 0;
        v['T' - 'A'] = 1;
        v['S' - 'A'] = 99999;
        v['B' - 'A'] = 2;
        v['R' - 'A'] = 99999;
        v['E' - 'A'] = 1;
        while (true) {
            StringTokenizer st = new StringTokenizer(cin.readLine());
            M = Integer.parseInt(st.nextToken());
            N = Integer.parseInt(st.nextToken());
            if (M == 0 && N == 0) break;
            for (int i = 0; i < M; i++) {
                String line = cin.readLine();
                for (int j = 0; j < N; j++) {
                    dist[i][j] = 99999;
                    char ch = line.charAt(j);
                    if (ch == 'Y') {
                        start_x = i;
                        start_y = j;
                        dist[i][j] = 0;
                    }
                    if (ch == 'T') {
                        end_x = i;
                        end_y = j;
                    }
                    map[i][j] = v[ch - 'A'];
                }
            }
            dijkstra();
            if (dist[end_x][end_y] >= 99999)
                System.out.println(-1);
            else
                System.out.println(dist[end_x][end_y]);
        }
    }

    private static void dijkstra() {
        Set<Integer> unvisited = new HashSet<Integer>();
        Set<Integer> visited = new HashSet<Integer>();
        unvisited.add(start_x * N + start_y);
        while (!unvisited.isEmpty()) {
            int min = 99999;
            int min_x = -1;
            int min_y = -1;
            for (int next : unvisited) {
                int x = next / N;
                int y = next % N;
                if (dist[x][y] < min) {
                    min = dist[x][y];
                    min_x = x;
                    min_y = y;
                }
            }
            visited.add(min_x * N + min_y);
            unvisited.remove(min_x * N + min_y);
            for (int i = 0; i < 4; i++) {
                int m = min_x + dir[i][0];
                int n = min_y + dir[i][1];
                if (m >= 0 && m < M && n >= 0 && n < N) {
                    if (dist[m][n] > dist[min_x][min_y] + map[m][n]) {
                        dist[m][n] = dist[min_x][min_y] + map[m][n];
                        if (!visited.contains(m * N + n))
                            unvisited.add(m * N + n);
                    }
                }
            }
        }
    }
}
