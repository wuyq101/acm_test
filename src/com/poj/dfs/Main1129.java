package com.poj.dfs;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * <pre>
 * http://poj.org/problem?id=1129
 * 题意：平面上有一些无线电站，组成图状，互不相交。为了使得相互传输不干扰，必须采用不同的频道。求最少的频道数。
 * 分析：根据四色定理，最多只要4种颜色。四色定理说明：任何一张地图只用四种颜色就能使具有共同边界的国家着上不同的颜色。
 * 扑学版本的四色问题阐述可以转化为更为抽象的图论版本。这里的转化指的是一种对偶的概念。
 * 即将一个地图转化为图论中的一个无向平面图。具体来说，是将地图中的每一个国家用其内部的一个点代表，作为一个顶点。
 * 如果两个国家相邻，就在两个顶点之间连一条线。这样得到的图必然是一个平面图（不会有两条边相交），而与每个国家选取的代表点无关。
 * 四色定理可以叙述为：必然可以用四种颜色给平面图的顶点染色，使得相邻的顶点颜色不同。
 * 在本题中，构图之后，将两个repeater之间的边看成顶点，每个repeater看成边，就构成了一张新的图，然后使得这张图相邻顶点颜色不同就行。
 *
 * 实现：用1-4，暴力带入验证，如果可以，就返回。复杂度 4^N.
 * </pre>
 * User: wuyq101
 * Date: 13-5-22
 * Time: 下午5:49
 * To change this template use File | Settings | File Templates.
 */
public class Main1129 {
    private static int[][] map = new int[26][26];
    private static int[] colors = new int[26];
    private static int N;

    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            init();
            N = Integer.parseInt(cin.readLine());
            if (N == 0) break;
            for (int i = 0; i < N; i++) {
                String s = cin.readLine();
                //起点
                int start = s.charAt(0) - 'A';
                for (int j = 2; j < s.length(); j++) {
                    int end = s.charAt(j) - 'A';
                    map[start][end] = 1;
                    map[end][start] = 1;
                }
            }
            for (int i = 1; i <= 4; i++) {
                if (verify(i)) {
                    if (i == 1)
                        System.out.printf("1 channel needed.\n");
                    else
                        System.out.printf("%d channels needed.\n", i);
                }
            }
        }
    }

    private static boolean verify(int k) {
        return verify(0,k);
    }

    private static boolean verify(int idx, int k) {
        if (colors[idx] == 0) {
            for (int c = 1; c <= k; c++) {
                if (can_use(idx, c)) {
                    colors[idx] = c;
                    if (idx == N - 1)
                        return true;
                    if (verify(idx + 1, k))
                        return true;
                    colors[idx] = 0;
                }
            }
        }
        return false;
    }

    private static boolean can_use(int idx, int c) {
        for (int i = 0; i < N; i++) {
            if (map[idx][i] == 1 && colors[i] == c)
                return false;
        }
        return true;
    }

    private static void init() {
        for (int i = 0; i < 26; i++)
            for (int j = 0; j < 26; j++)
                map[i][j] = 0;
        for (int i = 0; i < 26; i++)
            colors[i] = 0;
    }
}
