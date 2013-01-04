package com.poj;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 1363 基本栈 操作
 * @author wyq@palmdeal.com
 * @version 1.0
 */
public class Main1363 {
    private static int N;
    private static int[] n = new int[1001];
    private static int[] stack = new int[1001];
    private static int top = 0;

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        while (true) {
            N = cin.nextInt();
            if (N == 0)
                break;
            while (true) {
                n[1] = cin.nextInt();
                if (n[1] == 0)
                    break;
                else {
                    for (int i = 2; i <= N; i++)
                        n[i] = cin.nextInt();
                    solve();
                }
            }
            System.out.println();
        }
    }

    // 模拟栈操作
    private static void solve() {
        top = 0;
        int idx = 1;
        boolean flag = true;
        for (int i = 1; i <= N && flag; i++) {
            // 如果n[i]==栈顶
            if (top >= 1 && stack[top - 1] == n[i]) {
                top--;
                continue;
            }
            // n[i]==将要驶入的
            if (n[i] == idx) {
                idx++;
                continue;
            }
            // 先将不相等的入栈
            while (idx != n[i] && idx <= N) {
                stack[top++] = idx++;
            }
            // 找不到n[i]
            if (idx > N) {
                flag = false;
                break;
            } else {
                // 找到n[i]
                idx++;
            }
        }
        System.out.println(flag ? "Yes" : "No");
    }

}
