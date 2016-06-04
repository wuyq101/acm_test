package com.poj.graph;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * http://poj.org/problem?id=3180
 * 题目意思很模糊，表示看不懂，但是网上都是是求图的强联通分量。
 * tarjan算法： 具体见http://www.nocow.cn/index.php/%E5%BC%BA%E8%BF%9E%E9%80%9A%E5%88%86%E9%87%8F
 * Created by wuyq on 16/6/3.
 */
public class Main3180 {
    static int N, M;
    static int group;
    static int[] low, dfn;
    static int idx;
    static Stack<Integer> stack;
    static int[] in; //代表是否在stack中
    static List<Integer>[] edges;


    public static void main(String[] args) throws Exception {
        input();
        solve();
    }

    private static void solve() {
        low = new int[N + 1];
        dfn = new int[N + 1];
        in = new int[N + 1];
        stack = new Stack<Integer>();
        idx = 0;
        group = 0;
        for (int i = 1; i <= N; i++) {
            if (dfn[i] == 0) {
                tarjan(i);
            }
        }
        System.out.println(group);
    }

    private static void tarjan(int u) {
        dfn[u] = low[u] = ++idx;
        stack.push(u);
        in[u] = 1;
        if (edges[u] != null) {
            for (int i = 0; i < edges[u].size(); i++) {
                int v = edges[u].get(i);
                if (dfn[v] == 0) {
                    tarjan(v);
                    low[u] = Math.min(low[u], low[v]);
                } else if (in[v] == 1) {
                    low[u] = Math.min(low[u], dfn[v]);
                }
            }
        }
        if (dfn[u] == low[u]) {
            int count = 0;
            while (true) {
                int v = stack.pop();
                in[v] = 0;
                count++;
                if (v == u) break;
            }
            if (count >= 2) group++;
        }
    }

    private static void input() throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokenizer = new StringTokenizer(cin.readLine());
        N = Integer.parseInt(tokenizer.nextToken());
        M = Integer.parseInt(tokenizer.nextToken());
        edges = new ArrayList[N + 1];
        for (int i = 0; i < M; i++) {
            tokenizer = new StringTokenizer(cin.readLine());
            int s = Integer.parseInt(tokenizer.nextToken());
            int t = Integer.parseInt(tokenizer.nextToken());
            if (edges[s] == null) {
                edges[s] = new ArrayList<Integer>();
            }
            edges[s].add(t);
        }
    }
}
