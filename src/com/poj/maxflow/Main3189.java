package com.poj.maxflow;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * http://poj.org/problem?id=3189
 * Created by wuyq on 16/5/29.
 */
public class Main3189 {
    private static int MAXN = 1000;
    private static int MAXB = 25;
    private static int N;//牛的个数
    private static int B;//牛棚的个数
    private static int[][] rank = new int[MAXN][MAXB]; //rank[i][j]的值代表第i头牛，第j个感兴趣的 barn编号
    private static int[] capacity = new int[MAXB];
    private static int best; //当前找到的最优解
    private static int s;//起点，0
    private static int t;//终点，最后一个点的下标， 顺序为 s, 1, ..., N {N头牛}, N+1, ..., N+B{B头牛}, t
    private static int[][] c;  //网络的容量
    private static int[][] flow;//当前的流量
    private static int[] len; //bfs时候，len[i]表示 下标为i的这个点，现在的长度,-1表示i现在不可达
    private static int[] path; //bfs时候，path[i]的值代表 i节点的上一个节点， 根据path，可以复原整条路劲
    private static int[] f; //bfs找增广路径时， 记录流量
    private static int[] range; //记录那些兴趣度的边已经加入到网络中了，枚举的时候，避免全部添加，类似滑动窗口的效果


    public static void main(String[] args) throws Exception {
        input();
        init();
        solve();
        output();
    }

    private static void output() {
        System.out.println(best);
    }

    /**
     * 二分查找，求最窄的范围。最小的范围是1，最大的是B
     * 每找到一个范围，更新best值。
     */
    private static void solve() {
        int min = 1, max = B;
        best = B;
        while (min <= max) {
            int mid = (min + max) / 2;
            if (match(mid)) {
                if (mid < best) {
                    best = mid;
                }
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
    }

    private static void init() {
        int L = N + B + 2;
        s = 0;
        t = L - 1;
        c = new int[L][L];
        flow = new int[L][L];
        len = new int[L];
        path = new int[L];
        f = new int[L];
        range = new int[B + 1];
        //牛棚到终点
        int bs = N + 1;
        for (int i = 1; i <= B; i++) {
            c[bs + i - 1][t] = capacity[i];
        }
        //起点到牛
        for (int i = 1; i <= N; i++) {
            c[s][i] = 1;
        }
    }

    /**
     * 在兴趣度范围为range的情况下，判断能否找到合理的分配方案
     *
     * @param range
     * @return
     */
    private static boolean match(int range) {
        for (int i = 1; i + range <= B + 1; i++) {
            if (find(i, i + range)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 在low，high兴趣度的限定下，是否能分配N头牛
     *
     * @return
     */
    private static boolean find(int low, int high) {
        int bs = N + 1;
        for (int r = 1; r <= B; r++) {
            //原来存在，但是不在范围内的，需要去掉
            if ((r < low || r >= high) && range[r] == 1) {
                range[r] = 0;
                for (int i = 0; i < N; i++) {
                    int barn = rank[i][r];
                    c[i + 1][bs + barn - 1] -= 1;
                }
            }
            //在范围内，但是原来不存在的，需要增加
            if (r >= low && r < high && range[r] == 0) {
                range[r] = 1;
                for (int i = 0; i < N; i++) {
                    int barn = rank[i][r];
                    c[i + 1][bs + barn - 1] += 1;
                }
            }
        }

        //更新路径的流量值
        Arrays.fill(f, 0);
        //将所有牛到barn直接的流量清零
        for (int i = s; i <= t; i++) {
            for (int j = s; j <= t; j++) {
                flow[i][j] = 0;
                flow[j][i] = 0;
            }
        }

        if (maxflow() >= N) {
            return true;
        }
        return false;
    }

    private static int maxflow() {
        LinkedList<Integer> q = new LinkedList<Integer>();
        int max = 0;
        //预先处理那些明显存在的增广路径
        for (int i = 1; i <= N; i++) {
            for (int j = N + 1; j < N + 1 + B; j++) {
                if (c[i][j] > 0 && c[j][t] > flow[j][t]) {
                    //找到一条路径 s --> i --> j --> t
                    max++;
                    flow[j][t] += 1;
                    flow[t][j] -= 1;

                    flow[i][j] += 1;
                    flow[j][i] -= 1;
                }
            }
        }

        if (max >= N) {
            return max;
        }
        while (true) {
            Arrays.fill(len, 0, N + B + 2, -1);
            len[s] = 0;
            f[s] = Integer.MAX_VALUE;
            q.addLast(s);
            while (!q.isEmpty()) {
                int idx = q.removeFirst();
                for (int i = s; i <= t; i++) {
                    if (len[i] == -1 && c[idx][i] > flow[idx][i]) {
                        f[i] = Math.min(f[idx], c[idx][i] - flow[idx][i]);
                        len[i] = len[idx] + 1;
                        path[i] = idx;
                        q.addLast(i);
                        if (i == t) {
                            break;
                        }
                    }
                }
            }
            if (len[t] == -1) break;
            max += f[t];
            if (max >= N) {
                return max;
            }
            //沿着路径更新网络流量
            int cur = t;
            while (cur != s) {
                int pre = path[cur];
                flow[pre][cur] += f[t];
                flow[cur][pre] -= f[t];
                cur = pre;
            }
        }
        return max;
    }

    private static void input() throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        String line = cin.readLine();
        String[] s = line.split(" ");
        N = Integer.parseInt(s[0]);
        B = Integer.parseInt(s[1]);
        for (int i = 0; i < N; i++) {
            line = cin.readLine();
            s = line.split(" ");
            for (int j = 1; j <= B; j++) {
                rank[i][j] = Integer.parseInt(s[j - 1]);
            }
        }
        line = cin.readLine();
        s = line.split(" ");
        for (int i = 1; i <= B; i++) {
            capacity[i] = Integer.parseInt(s[i - 1]);
        }
    }
}
