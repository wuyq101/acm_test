package com.poj.dfs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * <pre>
 *     http://poj.org/problem?id=2303
 *     题意：俄罗斯套娃，给出每个套娃的高度、半径、厚度，将套娃们分成两组，每组各自能套在一起。
 *     分析：先排序,然后深度优先搜索
 * </pre>
 * Created by Administrator on 13-8-24.
 */
public class Main2303 {
    private static Doll[] dolls = new Doll[201];
    private static int[] category = new int[201];
    private static int n, N;

    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        while (true) {
            st = new StringTokenizer(cin.readLine());
            n = Integer.parseInt(st.nextToken());
            N = 2 * n;
            if (n == 0) break;
            for (int i = 1; i <= N; i++)
                read(new StringTokenizer(cin.readLine()), i);
            solve();
            System.out.println();
        }
    }

    private static void read(StringTokenizer st, int i) {
        if (dolls[i] == null) dolls[i] = new Doll();
        dolls[i].h = Integer.parseInt(st.nextToken());
        dolls[i].d = Integer.parseInt(st.nextToken());
        dolls[i].w = Integer.parseInt(st.nextToken());
    }


    private static void solve() {
        Arrays.sort(dolls, 1, N + 1);
        initFirstFakeDoll();
        Arrays.fill(category, 0);
        //put the first doll to category 1
        category[1] = 1;
        dfs(2, 1, 0, 1, 0);
        print(1);
        System.out.println("-");
        print(0);
    }

    private static void print(int type) {
        for (int i = 1; i <= N; i++)
            if (category[i] == type)
                System.out.printf("%d %d %d \n", dolls[i].h, dolls[i].d, dolls[i].w);
    }

    private static void initFirstFakeDoll() {
        if (dolls[0] == null) {
            dolls[0] = new Doll();
            dolls[0].h = Integer.MAX_VALUE;
            dolls[0].d = Integer.MAX_VALUE;
            dolls[0].w = 1;
        }
    }

    /**
     * @param idx     当前枚举的idx
     * @param last_1  第一组中的最后一个
     * @param last_2  第二组中的最后一个
     * @param count_1 第一组的个数
     * @param count_2 第二组的个数
     */
    private static boolean dfs(int idx, int last_1, int last_2, int count_1, int count_2) {
        if (count_1 > n || count_2 > n) return false;
        if (idx == N + 1) return true;
        int wall = dolls[last_1].w + dolls[last_1].w;
        if (dolls[last_1].h - wall >= dolls[idx].h && dolls[last_1].d - wall >= dolls[idx].d) {
            category[idx] = 1;
            if (dfs(idx + 1, idx, last_2, count_1 + 1, count_2)) return true;
            category[idx] = 0;
        }
        wall = dolls[last_2].w + dolls[last_2].w;
        if (dolls[last_2].h - wall >= dolls[idx].h && dolls[last_2].d - wall >= dolls[idx].d)
            if (dfs(idx + 1, last_1, idx, count_1, count_2 + 1)) return true;
        return false;
    }

    private static class Doll implements Comparable<Doll> {
        public int h, d, w;

        @Override
        public int compareTo(Doll other) {
            if (h == other.h) {
                if (d == other.d) return 0;
                return d > other.d ? -1 : 1;
            }
            return h > other.h ? -1 : 1;
        }
    }
}
