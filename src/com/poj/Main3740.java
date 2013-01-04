package com.poj;
import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 3740 从一个M*N的0,1矩阵中找出几行,使得这几行组成的矩阵,每列仅有一个1.
 * 
 * NOTE 超时，使用同样的算法c++，AC
 * 
 * @author vanguard001
 * @version 1.0
 */
public class Main3740 {
    private static int M;
    private static int N;
    // 2的幂次
    private static int[] bit = new int[16];

    // col[n]记录第n列1的分布情况
    private static int[] col = new int[300];

    private static boolean found;

    private static StringBuilder sb = new StringBuilder();
    private static final String NO = "It is impossible\n";
    private static final String YES = "Yes, I found it\n";

    public static void main(String[] args) {
        for (int i = 0; i < 16; i++) {
            bit[i] = 1 << i;
        }
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        while (cin.hasNext()) {
            // 初始化
            Arrays.fill(col, 0);
            // 读入数据
            M = cin.nextInt();
            N = cin.nextInt();
            for (int m = 0; m < M; m++) {
                for (int n = 0; n < N; n++) {
                    if (cin.nextInt() == 1) {
                        col[n] |= bit[m];
                    }
                }
            }
            solve();
            if (sb.length() > 1000) {
                System.out.println(sb.toString());
                sb.delete(0, sb.length());
            }
        }
        if (sb.length() > 0) {
            System.out.println(sb.toString());
        }
    }

    private static void solve() {
        found = false;
        // 预处理检查m行的冲突行
        for (int n = 0; n < N; n++) {
            // 检查是否某列中没有1,如果没有，则返回
            if (col[n] == 0) {
                // System.out.println("It is impossible");
                sb.append(NO);
                return;
            }
        }
        dfs(0, 0, 0);
        sb.append(found ? YES : NO);
        // System.out.println(found ? "Yes, I found it" : "It is impossible");
    }

    // 查找第n列，行数使用情况记录在used中，conflict记录与当前选中行冲突的那些行
    private static void dfs(int n, int used, int abort) {
        // 已经找到
        if (found)
            return;

        if (n == N) {
            // 已经找到最后一列，搜索结束
            found = true;
            return;
        }

        // 如果第n列的1都分布在conflict中
        if (col[n] == (col[n] & abort))
            return;

        int m1 = 0;
        int count = 0;
        for (int m = 0; m < M; m++) {
            if ((col[n] & used & bit[m]) != 0) {
                count++;
                m1 = m;
            }
        }
        if (count == 1) {
            dfs(n + 1, used, abort | col[n] ^ bit[m1]);
            if (found)
                return;
        } else if (count == 0) {
            for (int m = 0; m < M && !found; m++) {
                if ((abort & bit[m]) == 0 && (col[n] & bit[m]) != 0 && (used & bit[m]) == 0)
                    // 第m行已经被排除
                    // 第m行第n列没有1
                    // 第m行已经被使用
                    dfs(n + 1, used | bit[m], abort | col[n] ^ bit[m]);
                if (found)
                    return;
            }
        }
    }
}
