package com.poj.dp;
import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1038, 动态规划DP 状态压缩 dfs深度优先搜索
 * 
 * @author wuyq101
 */
public class Main1038 {
    // 1<=M<=10
    private static int M;
    // 1<=N<=150
    private static int N;
    // 坏点的个数
    private static int K;

    private static int best;
    // 59049==3^10
    private static int[][] dp = new int[2][59049];

    // 一共x行，每行y列，和题目图中的坐标变换一下，使得每行最多M列，每列3中情况，最多3^10=59049中状态。
    private static boolean[][] map = new boolean[153][13];

    // 3^0, 3^1, ..., 3^10
    private static int[] pow = { 1, 3, 9, 27, 81, 243, 729, 2187, 6561, 19683, 59049 };
    // p, q中的三个状态代表的含义
    // 0 该行与上一行都是空
    // 1 该行是空，上一行被占
    // 2 该行被占
    // 用p(m)表示第m行，第i列和第i-1列的状态，那么整个的放置状态就是p(0)*3^0+p(1)*3^1......p(M-1)*3^(M-1)
    private static int[] p = new int[11];

    // 由于下移了一列，第i-1列我们暂不考虑，之前的0和1都变成新状态的0，原来的2状态变成了新状态的1，
    // 但是需要考虑的是第i+1列中是否会存在不能放的区域，假如上图的某个问号中某处不能放置东西，那么不论这个状态是0，还是1，都将变成2，
    // 将新状态存于q数组中
    private static int[] q = new int[11];

    private static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        int T = cin.nextInt();
        int x, y;
        while (T-- > 0) {
            N = cin.nextInt();
            M = cin.nextInt();
            init();
            K = cin.nextInt();
            for (int i = 0; i < K; i++) {
                x = cin.nextInt();
                y = cin.nextInt();
                map[x][y] = false;
            }
            //预先处理，过滤所有不能用的点，减少dfs的状态
            //filter();
            dp();
        }
        System.out.println(sb.toString());
    }

    private static void dp() {
        int max_state = pow[M];
        // dp初始化-1，代表不可达或者尚未到达的状态
        // Arrays.fill(dp[0], -1);
        Arrays.fill(dp[1], -1);
        best = 0;
        // 第一行的初始状态
        for (int m = 1; m <= M; m++) {
            p[m] = map[1][m] ? 1 : 2;
        }
        int state = toInt(p);
        dp[1][state] = 0;
        // 从第二行开始搜索
        for (int n = 2; n <= N; n++) {
            // 对每一行求解
            Arrays.fill(dp[n % 2], -1);
            // 从上一行有效的状态开始
            for (state = 0; state < max_state; state++) {
                if (dp[(n - 1) % 2][state] != -1) {
                    // 计算p,上一行的状态
                    toArray(state, p);
                    // 计算q,当前行的状态
                    int cur_state = move(n);
                    dfs(n, 1, cur_state, 0);
                }
            }
        }
        // System.out.println(best);
        sb.append(best).append('\n');
    }

    // 处理第n行，第m列，当前的状态是state,(指x-1行的状态)， 当前已经摆放了count个
    private static void dfs(int n, int m, int state, int count) {
        if (m > M - 1) {
            // 搜索结束
            int last = toInt(p);
            dp[n % 2][state] = Math.max(dp[n % 2][state], dp[(n - 1) % 2][last] + count);
            if (n == N) {
                best = Math.max(best, dp[n % 2][state]);
            }
            return;
        }

        // m占3格的放
        if (m <= M - 2 && q[m] == 0 && q[m + 1] == 0 && q[m + 2] == 0) {
            q[m] = q[m + 1] = q[m + 2] = 2;
            int now = toInt(q);
            dfs(n, m + 3, now, count + 1);
            q[m] = q[m + 1] = q[m + 2] = 0;
        }

        // m占2格的放
        if (m <= M - 1 && q[m] == 0 && q[m + 1] == 0 && p[m] == 0 && p[m + 1] == 0) {
            q[m] = q[m + 1] = 2;
            int now = toInt(q);
            dfs(n, m + 2, now, count + 1);
            q[m] = q[m + 1] = 0;
        }
        // 不放
        dfs(n, m + 1, state, count);
    }

    // 返回新的状态
    private static int move(int n) {
        // 原来0,1的状态都变为0， 原来2的状态变为1
        // 但是如果map[n][m]不能使用的话，q[m]=2
        int state = 0;
        for (int m = 1; m <= M; m++) {
            if (map[n][m]) {
                q[m] = p[m] - 1;
                q[m] = q[m] < 0 ? 0 : q[m];
                state = state + q[m] * pow[m - 1];
            } else {
                q[m] = 2;
                state = state + q[m] * pow[m - 1];
            }
        }
        return state;
    }

    private static void toArray(int state, int[] a) {
        for (int m = 1; m <= M; m++) {
            a[m] = state % 3;
            state = state / 3;
        }
    }

    private static int toInt(int[] a) {
        int state = 0;
        for (int m = 1; m <= M; m++) {
            state = state + a[m] * pow[m - 1];
        }
        return state;
    }

    private static void init() {
        // 将(1,1)--->(N,M)的区域设置为true
        for (int x = 1; x <= N; x++) {
            Arrays.fill(map[x], 1, M + 1, true);
        }
    }

    // 预处理，假设要在点(x,y)上chip，围绕(x,y),一共有12种切法，如果这12种都不能切割，则该点废了。
//    private static void filter() {
//        for (int i = 1; i <= N; i++) {
//            for (int j = 1; j <= M; j++) {
//                if (!map[i][j]) {
//                    continue;
//                }
//                if (isWaste(i, j)) {
//                    map[i][j] = false;
//                }
//            }
//        }
//    }

    // 判断围绕(x,y)点的12中切法是否有一种或者多种可行，如果没有一种可行，该点作废。
//    private static boolean isWaste(int x, int y) {
//        // 竖着放
//        for (int i = -2; i <= 0; i++) {
//            for (int j = -1; j <= 0; j++) {
//                if (x + i >= 1 && y + j >= 1 && canPut(x + i, y + j, 2, 3)) {
//                    return false;
//                }
//            }
//        }
//        // 横着放
//        for (int i = -1; i <= 0; i++) {
//            for (int j = -2; j <= 0; j++) {
//                if (y + i >= 1 && x + j >= 1 && canPut(y + i, x + j, 3, 2)) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

//    private static boolean canPut(int x, int y, int len, int height) {
//        // 检查边界是否超出
//        if (x + height - 1 > N) {
//            return false;
//        }
//        if (y + len - 1 > M) {
//            return false;
//        }
//        // 检查每一个点是否是坏点
//        for (int i = x; i < x + height; i++) {
//            for (int j = y; j < y + len; j++) {
//                if (!map[i][j]) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }

}