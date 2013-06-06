package com.poj.dp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.Math.max;

/**
 * <pre>
 *     http://poj.org/problem?id=2184
 *     题意：n头牛，每头牛有两个属性值，smart值s，fun值f，求选出其中的m头牛，使得s和f的总和最大，并保证各自的s和以及f和都大于0.
 *     分析：
 *     最直观的想法是搜索，每头牛都有两种情况，选中或者没有选中，那么搜索空间就是2^100，这个计算量太大，排除。
 *     转化问题，求s和为某个固定值时候最大的f和值，然后遍历这些所有的s和以及对应的f和值，求出总和总和最大的那个。
 *     那么这样就是一个0-1背包问题，可以把s值理解为费用，f值理解为价值
 *     dp[c]代表s和为c时候，f和能取到的最大值。
 *     状态转移方程： dp[c]=max{dp[c], dp[c-s[i]+f[i]}
 *     注意：因为s有小于0的情况，s的范围在[-1000,1000]之间，最多有100头牛，所以和的范围在[-100000,100000]之间，为了避免负数作为下标，
 *     将s和都加上100000，整体的范围就变为[0,200000]。
 *     当s[i]>0的时候，dp[c]的计算顺序从大往小计算，因为在计算第i头牛的最优f和时，dp[c-s[i]]是前i-1头牛的最优f和，而这个值在dp[c]的左侧，所以我们
 *     选择从右往左计算，这样就可以利用前i-1的最优解，不会覆盖。同理当s[i]<0的时候，dp[c]的计算顺序从小往大计算，因为dp[c-s[i]]这个时候
 *     在dp[c]的右侧了
 * </pre>
 * User: wuyq101
 * Date: 13-5-31
 * Time: 下午5:29
 */
public class Main2184 {
    private static int n, MAX = 200000, ZERO = 100000;
    private static int[] s = new int[100], f = new int[100];
    //dp[i]为s和为i时候f和的最大值
    private static int[] dp = new int[MAX + 1];

    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(cin.readLine());
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(cin.readLine());
            s[i] = Integer.parseInt(st.nextToken());
            f[i] = Integer.parseInt(st.nextToken());
        }
        for (int i = 0; i <= MAX; i++) {
            dp[i] = -MAX;
        }
        dp[ZERO] = 0;
        for (int i = 0; i < n; i++) {
            if (s[i] < 0) {
                for (int c = 0; c - s[i] <= MAX; c++) {
                    dp[c] = max(dp[c], dp[c - s[i]] + f[i]);
                }
            } else {
                for (int c = MAX; c >= s[i]; c--) {
                    dp[c] = max(dp[c], dp[c - s[i]] + f[i]);
                }
            }
        }
        int max = 0;
        for (int c = ZERO; c <= MAX; c++) {
            if (dp[c] > 0 && dp[c] + c - ZERO > max) {
                max = dp[c] + c - ZERO;
            }
        }
        System.out.println(max);
    }
}
