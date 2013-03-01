package com.poj.kruskal;

import java.util.*;

/**
 * <pre>
 *     原题：http://poj.org/problem?id=2377
 *     题意：求最大生成树的权重
 * </pre>
 * User: wuyq101
 * Date: 13-3-1
 * Time: 下午5:06
 */
public class Main2377 {
    private static class Edge {
        int u, v, c;
    }

    private static int n, m;
    private static Edge[] edges = new Edge[20000];
    private static List<Set<Integer>> graph = new ArrayList<Set<Integer>>();

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        n = cin.nextInt();
        m = cin.nextInt();
        for (int i = 0; i < m; i++) {
            if (edges[i] == null)
                edges[i] = new Edge();
            edges[i].u = cin.nextInt();
            edges[i].v = cin.nextInt();
            edges[i].c = cin.nextInt();
        }
        kruskal();
    }


    private static void kruskal() {
        //将所有的边根据cost从大到小排序
        Arrays.sort(edges, 0, m, new Comparator<Edge>() {
            @Override
            public int compare(Edge a, Edge b) {
                return a.c > b.c ? -1 : 1;
            }
        }
        );
        int cost = 0;
        make_set();
        for (int i = 0; i < m; i++) {
            int u = edges[i].u, v = edges[i].v;
            //检查uv是否已经连通，如果已经连通，不使用这条边，如果不连通，则增加这条边；
            Set<Integer> set1 = find_set(u), set2 = find_set(v);
            if (find_set(u) != find_set(v)) {
                set1.addAll(set2);
                graph.remove(set2);
                cost += edges[i].c;
            }
        }
        //全部都已经连通
        System.out.println(graph.size() == 1 ? cost : -1);
    }

    private static void make_set() {
        for (int i = 1; i <= n; i++) {
            Set<Integer> set = new HashSet<Integer>();
            set.add(i);
            graph.add(set);
        }
    }

    private static Set<Integer> find_set(int u) {
        for (Set<Integer> set : graph) {
            if (set.contains(u))
                return set;
        }
        return null;
    }
}
