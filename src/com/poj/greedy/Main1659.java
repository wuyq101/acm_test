package com.poj.greedy;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * <pre>
 *     http://poj.org/problem?id=1659
 *     参考：http://en.wikipedia.org/wiki/Degree_(graph_theory)  Havel-Hakimi定理
 * </pre>
 * User: wuyq101
 * Date: 13-6-14
 * Time: 下午5:44
 */
public class Main1659 {
    private static int N, sum;
    private static Node[] x = new Node[11];
    private static int[][] G = new int[11][11];

    private static class Node implements Comparable<Node> {
        int idx;
        int degree;

        @Override
        public int compareTo(Node other) {
            if (degree == other.degree) return 0;
            return degree < other.degree ? 1 : -1;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(cin.readLine());
        while (t-- > 0) {
            N = Integer.parseInt(cin.readLine());
            sum = 0;
            StringTokenizer st = new StringTokenizer(cin.readLine());
            for (int i = 0; i < N; i++) {
                if (x[i] == null)
                    x[i] = new Node();
                x[i].idx = i;
                x[i].degree = Integer.parseInt(st.nextToken());
                sum += x[i].degree;
            }
            solve();
            System.out.println();
        }
    }

    private static void solve() {
        if (sum % 2 == 1) {
            System.out.println("NO");
            return;
        }
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                G[i][j] = 0;
        boolean flag = true;
        for (int i = 0; i < N; i++) {
            Arrays.sort(x, i, N);
            Node node = x[i];
            int d = node.degree;
            //将i与与之后的degree个顶点相连
            int next, idx = node.idx, next_idx;
            for (int j = 1; j <= d; j++) {
                next = i + j;
                if (next >= N) {
                    flag = false;
                    break;
                }
                x[next].degree--;
                next_idx = x[next].idx;
                if (x[next].degree < 0) {
                    flag = false;
                    break;
                }
                G[idx][next_idx] = 1;
                G[next_idx][idx] = 1;
            }
        }
        if (!flag) {
            System.out.println("NO");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("YES\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(G[i][j]).append(' ');
            }
            sb.append('\n');
        }
        System.out.printf(sb.toString());
    }
}
