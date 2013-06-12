package com.poj.dfs;

import java.util.Arrays;
import java.util.Scanner;

/**
 * <pre>
 * http://poj.org/problem?id=2362
 * 题意： 给定M根木棍，问是否可以围绕成正方形。
 * 分析：深度优先搜索（dfs）。
 *          剪枝条件：
 *          1. 总和是否可被4整除；
 *          2. 最长木棍是否超过最后的平均边长。
 *          3. 如果已经拼装了3条边，可找到一个方案，退出
 *          4. 如果使用某条木棍找不到可行解，则相同长度的木棍也找不到
 *          5. 如果从某条木棍出发，找不到可行解，直接退出
 * </pre>
 * User: wuyq101
 * Date: 13-6-7
 * Time: 下午5:11
 */
public class Main2362 {
    private static int M, sum, side, side_cnt;
    private static int[] s = new int[20], used = new int[20];
    private static boolean found;


    public static void main(String[] args) throws Exception {
        Scanner cin = new Scanner(System.in);

        int t = cin.nextInt();
        while (t-- > 0) {
            found = false;
            sum = 0;
            side_cnt = 0;
            M = cin.nextInt();
            for (int i = 0; i < M; i++) {
                int d = cin.nextInt();
                sum += d;
                s[i] = d;
                used[i] = 0;
            }
            solve();
            System.out.println(found ? "yes" : "no");
        }
    }

    private static void solve() {
        if (sum % 4 != 0) {
            found = false;
            return;
        }
        side = sum / 4;
        Arrays.sort(s, 0, M);
        for (int i = 0, j = M - 1; i < j; i++, j--) {
            int t = s[i];
            s[i] = s[j];
            s[j] = t;
        }
        if (s[0] > side) {
            found = false;
            return;
        }
        //开始搜素
        dfs(0, 0, 0);
    }

    private static void dfs(int start, int cur, int root) {
        if (found) return;
        if (cur == side) {
            side_cnt++;
            if (side_cnt == 3) {
                found = true;
                return;
            }
            int i = 0;
            while (used[i] == 1) i++;
            dfs(i, 0, i);
            return;
        }
        for (int i = start; i < M; i++) {
            if (used[i] == 0 && s[i] + cur <= side) {
                used[i] = 1;
                dfs(i + 1, s[i] + cur, root);
                if (found) return;
                if (cur + s[i] == side) side_cnt--;
                used[i] = 0;
                while (i + 1 < M && s[i + 1] == s[i]) i++;
            }
            if (used[root] == 0) return;
        }
    }
}
