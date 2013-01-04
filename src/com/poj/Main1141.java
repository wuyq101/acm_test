package com.poj;
import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1141 动态规划
 * 
 * @author vanguard001
 * @version 1.0
 */
public class Main1141 {

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        while (cin.hasNextLine()) {
            String line = cin.nextLine();
            //System.out.println("输入的内容长度"+line.length());
            solve(line);
        }
    }

    private static char[] ch = new char[100];
    private static int len = 0;
    private static int[][] dp = new int[101][101];
    private static int[][] path = new int[101][101];

    private static void solve(String line) {
        len = 0;
        for (int i = 0; i < line.length(); i++) {
            ch[len++] = line.charAt(i);
        }
        for (int i = 0; i < len; i++) {
            Arrays.fill(dp[i], -1);
        }
        dp(0, len - 1);
        System.out.println(print(0, len - 1));
    }

    private static String print(int i, int j) {
        if (i > j) {
            return "";
        }
        if (path[i][j] == -1) {
            // 刚才是去头去尾，不用处理
            if (ch[i] == '(')
                return "(" + print(i + 1, j - 1) + ")";
            else
                return "[" + print(i + 1, j - 1) + "]";
        }
        if (i == j) {
            // 就一个字符，补全
            if (ch[i] == '(' || ch[i] == ')') {
                return "()";
            }
            if (ch[i] == '[' || ch[i] == ']') {
                return "[]";
            }
        }
        int k = path[i][j];
        // 刚才是分成这样来做的 (i,k)+(k+1,j)
        return print(i, k) + print(k + 1, j);
    }

    private static int dp(int i, int j) {
        // 搜索结束
        if (i > j) {
            return 0;
        }
        // 已经搜索过的
        if (dp[i][j] >= 0) {
            return dp[i][j];
        }
        // 搜索到最后一个相同的字符
        if (i == j) {
            dp[i][j] = 1;
            // 表示补全，补1个字符--->1
            path[i][j] = i;
            return 1;
        }
        int min = Integer.MAX_VALUE;
        if ((ch[i] == '(' && ch[j] == ')') || (ch[i] == '[' && ch[j] == ']')) {
            if (dp(i + 1, j - 1) < min) {
                min = dp(i + 1, j - 1);
                // -1 代表去掉一个头和尾巴
                path[i][j] = -1;
            }
        }
        for (int k = i; k + 1 <= j; k++) {
            if (dp(i, k) + dp(k + 1, j) < min) {
                min = dp(i, k) + dp(k + 1, j);
                // k, 表示在k的位置上拆分成(i,k), (k+1, j)
                path[i][j] = k;
            }
        }
        dp[i][j] = min;
        return min;
    }

}
