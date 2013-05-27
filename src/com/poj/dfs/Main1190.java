package com.poj.dfs;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * <pre>
 *     http://poj.org/problem?id=1190
 *     7月17日是Mr.W的生日，ACM-THU为此要制作一个体积为Nπ的M层生日蛋糕，每层都是一个圆柱体。
 *     设从下往上数第i(1 <= i <= M)层蛋糕是半径为Ri, 高度为Hi的圆柱。当i < M时，要求Ri > Ri+1且Hi > Hi+1。
 *     由于要在蛋糕上抹奶油，为尽可能节约经费，我们希望蛋糕外表面（最下一层的下底面除外）的面积Q最小。
 *     令Q = Sπ
 *      请编程对给出的N和M，找出蛋糕的制作方案（适当的Ri和Hi的值），使S最小。
 *    （除Q外，以上所有数据皆为正整数）
 *    分析：Q = S*Pi = Pi*R1^2 + sigma{2Pi*Ri*Hi}（i=1...M）
 *              S = R1^2+sigma{2Ri*Hi}（i=1...M）
 *              V = N*Pi = sigma{Pi*Ri^2*Hi}(i=1...M)
 *              N = sigma{Ri^2*Hi}(i=1...M)
 *    给定约束：N = sigma{Ri^2*Hi}(i=1...M)
 *    求S=R1^2+sigma{2Ri*Hi}（i=1...M）的最小值。
 * </pre>
 * User: wuyq101
 * Date: 13-5-23
 * Time: 上午11:42
 */
public class Main1190 {
    private static int N, M;
    private static int[] minv = new int[21], mins = new int[21];
    private static int best;

    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(cin.readLine());
        M = Integer.parseInt(cin.readLine());
        minv[0] = mins[0] = 0;
        for (int i = 1; i <= M; i++) {
            minv[i] = minv[i - 1] + i * i * i;
            mins[i] = mins[i - 1] + 2 * i * i;
        }
        best = Integer.MAX_VALUE;
        dfs(0, 0, M, N + 1, N + 1);
        if (best == Integer.MAX_VALUE)
            best = 0;
        System.out.println(best);
    }

    private static void dfs(int cur_v, int cur_s, int now, int r, int h) {
        if (now == 0) {
            //找到一个答案
            if (cur_v == N && cur_s < best)
                best = cur_s;
            return;
        }
        if (cur_v + minv[now] > N || cur_s + mins[now] > best || 2 * (N - cur_v) / r + cur_s >= best) return;
        //枚举各个半径值
        for (int i = r - 1; i >= now; i--) {
            if (now == M)
                cur_s = i * i;
            int max_h = Math.min((N - minv[now - 1] - cur_v) / i / i, h - 1);
            //枚举各个可能的高度
            for (int j = max_h; j >= now; j--) {
                dfs(cur_v + i * i * j, cur_s + 2 * i * j, now - 1, i, j);
            }
        }
    }
}
