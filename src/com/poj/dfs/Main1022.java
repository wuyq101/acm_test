package com.poj.dfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * http://poj.org/problem?id=1022
 * User: wuyq101
 * Date: 13-3-15
 * Time: 上午8:39
 */
public class Main1022 {
    private static class Cube {
        boolean used;
        int id;
        int[] x = new int[4];
        int[][] parallel = new int[4][2];
    }

    private static int n;
    private static Cube[] cubes = new Cube[100];
    //每个维度上的最大值和最小值
    private static int[][] min = new int[4][2];

    public static void main(String[] args) throws IOException {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t = Integer.parseInt(cin.readLine()), id;
        while (t-- > 0) {
            n = Integer.parseInt(cin.readLine());
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(cin.readLine());
                if (cubes[i] == null)
                    cubes[i] = new Cube();
                else
                    cubes[i].used = false;
                cubes[i].id = Integer.parseInt(st.nextToken());
                for (int j = 0; j < 4; j++)
                    for (int k = 0; k < 2; k++)
                        cubes[i].parallel[j][k] = Integer.parseInt(st.nextToken());
            }
            solve();
        }
    }

    private static void solve() {
        boolean inconsistent = false;
        //相邻一致性检查
        for (int i = 0; i < n && !inconsistent; i++) {
            for (int j = 0; j < 4 && !inconsistent; j++) {
                for (int k = 0; k < 2 && !inconsistent; k++) {
                    if (cubes[i].parallel[j][k] == 0) continue;
                    int parallel_idx = find_by_id(cubes[i].parallel[j][k]);
                    if (parallel_idx == -1) inconsistent = true;
                    if (cubes[parallel_idx].parallel[j][1 - k] != cubes[i].id)
                        inconsistent = true;
                }
            }
        }
        if (inconsistent) {
            System.out.printf("Inconsistent\n");
            return;
        }
        //检查每个4d cube是否具有8个面
        for (int i = 0; i < 4; i++) {
            min[i][0] = 0;//取最大值
            min[i][1] = 0;//取最小值
            cubes[0].x[i] = 0;
        }
        flood_fill(0);
        for (int i = 0; i < n && !inconsistent; i++) {
            if (!cubes[i].used)
                inconsistent = true;
        }
        if (inconsistent) {
            System.out.printf("Inconsistent\n");
            return;
        }
        int min_v = 1;
        for (int i = 0; i < 4; i++)
            min_v *= min[i][0] - min[i][1] + 1;
        System.out.printf("%d\n", min_v);
    }

    private static void flood_fill(int i) {
        if (cubes[i].used) return;
        cubes[i].used = true;
        for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 2; k++) {
                if (cubes[i].parallel[j][k] == 0) continue;
                int parallel_idx = find_by_id(cubes[i].parallel[j][k]);
                if (cubes[parallel_idx].used) continue;
                //拷贝坐标
                for (int h = 0; h < 4; h++)
                    cubes[parallel_idx].x[h] = cubes[i].x[h];
                cubes[parallel_idx].x[j] += k == 0 ? 1 : -1;
                min[j][0] = cubes[parallel_idx].x[j] > min[j][0] ? cubes[parallel_idx].x[j] : min[j][0];
                min[j][1] = cubes[parallel_idx].x[j] < min[j][1] ? cubes[parallel_idx].x[j] : min[j][1];
                flood_fill(parallel_idx);
            }
        }
    }

    private static int find_by_id(int parallel_id) {
        for (int i = 0; i < n; i++)
            if (cubes[i].id == parallel_id)
                return i;
        return -1;
    }
}
