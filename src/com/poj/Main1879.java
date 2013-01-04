package com.poj;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 1879
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main1879 {
    // 底部队列
    private static int[] queue = new int[127];
    private static int head;
    private static int tail;
    // 1分钟 栈
    private static int[] stack_1 = new int[4];
    private static int top_1;
    // 5分钟栈
    private static int[] stack_5 = new int[11];
    private static int top_5;

    // 小时栈
    private static int[] stack_h = new int[11];
    private static int top_h;

    private static int N;

    private static int[] dlt = new int[127];
    private static int[] cycle = new int[127];

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        while (true) {
            N = cin.nextInt();
            if (N == 0)
                break;
            solve();
        }
    }

    private static void solve() {
        head = tail = 0;
        for (int i = 1; i <= N; i++) {
            queue[tail++] = i;
        }
        // 循环队列刚好满
        tail = N - 1;
        top_1 = top_5 = top_h = 0;
        day();
        dlt();
        cycle();
        int ans = cycle[0];
        for (int i = 1; i < N; i++) {
            ans = lcm(ans, cycle[i]);
        }
        System.out.printf("%d balls cycle after %d days.\n", N, ans);
    }

    // 求最大公约数
    private static int gcd(int a, int b) {
        if (a % b == 0)
            return b;
        return gcd(b, a % b);
    }

    // 最小公倍数
    private static int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    // 求每个位置上的最小周期
    private static void cycle() {
        for (int i = 0; i < N; i++) {
            int cnt = 1;
            int idx = dlt[i];
            while (idx != i) {
                cnt++;
                idx = dlt[idx];
            }
            cycle[i] = cnt;
            if (cycle[i] % 2 == 0)
                cycle[i] /= 2;
        }
    }

    // 一天之后每个位置上小球的移动情况
    private static void dlt() {
        // 先将队列拷贝到cycle,临时借用
        for (int i = 0; i < N; i++) {
            cycle[i] = queue[head];
            head = (head + 1) % N;
        }
        for (int i = 1; i <= N; i++) {
            for (int j = 0; j < N; j++) {
                if (cycle[j] == i) {
                    dlt[i - 1] = j;
                    break;
                }
            }
        }
    }

    // 模拟一天的运行情况
    private static void day() {
        for (int i = 0; i < 720; i++) {
            // 取出一个放到1分钟栈
            int d = queue[head];
            head = (head + 1) % N;
            if (top_1 < 4) {
                stack_1[top_1++] = d;
                continue;
            }
            // 其中4个回底部队列
            while (top_1 > 0) {
                tail = (tail + 1) % N;
                queue[tail] = stack_1[--top_1];
            }
            // 将第4个放入到5分钟栈
            if (top_5 < 11) {
                stack_5[top_5++] = d;
                continue;
            }
            // 11个小球回底部队列
            while (top_5 > 0) {
                tail = (tail + 1) % N;
                queue[tail] = stack_5[--top_5];
            }
            // 第12个小球去小时栈
            if (top_h < 11) {
                stack_h[top_h++] = d;
                continue;
            }
            // 11个小球回底部队列
            while (top_h > 0) {
                tail = (tail + 1) % N;
                queue[tail] = stack_h[--top_h];
            }
            // 第12个小球回队列
            tail = (tail + 1) % N;
            queue[tail] = d;
        }
    }

}
