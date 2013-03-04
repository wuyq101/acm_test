package com.poj.sort;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <pre>
 *     原题：http://poj.org/problem?id=1877
 *     题意:给出一块矩形，按照10*10划分为m*n个格子，给出每个格子的海拔高度，
 *     同时给出该矩形的降水量H（立方），求降水的水位高度，同时求被覆盖区域的百分比。
 *     分析：将各个格子排序，先从第一个格子开始灌水，如果灌到和第二个格子一样高的时候
 *     这样的水位已经小于H的话，那就不用考虑后面的格子了，直接算出答案。如果灌到和第二个格子一样高时，还有多余的水，那么将当前答案设置为
 *     第二个格子的海拔高度，然后水量减去现在1个（第一个）的蓄水量。再考虑第三个格子，如果从第二个到第三个格子的高度差*两个（第一个和第二个）
 *     能够将剩余的水都容纳的话，也直接算答案，重复以上过程直到最后一个格子。
 *     另外需要注意的是，如果H为0的话，那水位高度是0（如果最低的格子高度大于零），是最低格子的高度（如果它小于0）。
 * </pre>
 * User: wuyq101
 * Date: 13-3-3
 * Time: 下午9:12
 */
public class Main1877 {
    private static int n, m, case_no;
    //总的高度
    private static double H, ans;
    private static int[] h = new int[900];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (true) {
            m = cin.nextInt();
            n = cin.nextInt();
            if (n == 0 && m == 0) break;
            int k = m * n;
            for (int i = 0; i < k; i++)
                h[i] = cin.nextInt();
            H = cin.nextInt();
            solve();
        }
    }

    private static void solve() {

        H = H / 100.0;
        int k = m * n, cnt = 0;
        Arrays.sort(h, 0, k);
        if (H == 0) {
            System.out.printf("Region %d\n" +
                    "Water level is %.2f meters.\n" +
                    "0.00 percent of the region is under water.", ++case_no, h[0] < 0 ? h[0] * 1.0 : 0.00);
            return;
        }
        ans = h[0];
        for (int i = 1; i < k; i++) {
            if (H / i + ans <= h[i]) {
                ans = ans + H / i;
                cnt = i;
                H = 0;
                break;
            } else {
                H -= (h[i] - ans) * i;
                ans = h[i];
            }
        }
        if (H > 0) {
            ans = ans + H / k;
            cnt = k;
        }
        System.out.printf("Region %d\n" +
                "Water level is %.2f meters.\n" +
                "%.2f percent of the region is under water.", ++case_no, ans, cnt * 100.0 / k);
    }
}
