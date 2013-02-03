package com.poj.greedy;

import java.util.Scanner;

/**
 * <pre>
 * 原题：http://poj.org/problem?id=2325
 * 题意：整数，各位数字相乘之后得到一个新的数，例如2688 --> 768=2*6*8*8, 现在给出768，求哪个数字经过各位数字相乘之后等于768并且最小。
 * 分析：先对给出的数，进行质数分解，只计算2，3,5,7这几个数的因子个数，如果有超出这个4个质数的因子，则给出的数字不可能。
 *           计算出各个质数的因子个数之后，贪心计算结果，3*3-->9, 2*2*2-->8,7,2*3-->6,5,2*2-->4,3,2, 根据从大到小来合并数字。最终输出结果。
 * </pre>
 * User: wuyq101
 * Date: 13-2-3
 * Time: 下午12:45
 */
public class Main2325 {
    private static int[] digits;
    private static int n, cnt_2, cnt_3, cnt_5, cnt_7;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext()) {
            String s = cin.next();
            if ("-1".equals(s))
                break;
            solve(s);
        }
    }

    private static void solve(String s) {
        n = s.length();
        if (n == 1) {
            System.out.println("1" + s);
            return;
        }
        cnt_2 = 0;
        cnt_3 = 0;
        cnt_5 = 0;
        cnt_7 = 0;
        digits = new int[n];
        for (int i = 0; i < n; i++) {
            digits[i] = s.charAt(n - 1 - i) - '0';
        }
        //开始做因式分解，仅对，2,3,5,7做
        while ((digits[0] & 1) == 0) {
            cnt_2++;
            divide(2);
        }
        //开始算3
        while (can_divided_by_3()) {
            cnt_3++;
            divide(3);
        }
        //开始除以5
        while (digits[0] == 0 || digits[0] == 5) {
            cnt_5++;
            divide(5);
        }
        //开始除以7
        while (true) {
            for (int i = n - 1; i > 0; i--) {
                digits[i - 1] += 10 * (digits[i] % 7);
                digits[i] /= 7;
            }
            if (digits[0] % 7 == 0) {
                digits[0] /= 7;
                cnt_7++;
            } else {
                break;
            }
        }
        //是否已近完全因式分解
        boolean flag = true;
        for (int i = 1; i < n; i++) {
            if (digits[i] != 0) {
                flag = false;
                break;
            }
        }
        if (digits[0] != 1) {
            flag = false;
        }
        if (!flag) {
            System.out.println("There is no such number.");
            return;
        }
        //根据统计的个数得出答案
        StringBuilder sb = new StringBuilder();
        calc_result(cnt_2, cnt_3, cnt_5, cnt_7, sb);
        System.out.printf(sb.append('\n').toString());
    }

    private static void calc_result(int cnt_2, int cnt_3, int cnt_5, int cnt_7, StringBuilder sb) {
        if (cnt_3 >= 2) {
            int n = cnt_3 / 2;
            calc_result(cnt_2, cnt_3 % 2, cnt_5, cnt_7, sb);
            for (int i = 0; i < n; i++)
                sb.append('9');
            return;
        }
        if (cnt_2 >= 3) {
            int n = cnt_2 / 3;
            calc_result(cnt_2 % 3, cnt_3, cnt_5, cnt_7, sb);
            for (int i = 0; i < n; i++)
                sb.append('8');
            return;
        }
        if (cnt_7 > 0) {
            calc_result(cnt_2, cnt_3, cnt_5, 0, sb);
            for (int i = 0; i < cnt_7; i++)
                sb.append('7');
            return;
        }
        if (cnt_2 > 0 && cnt_3 > 0) {
            int min = Math.min(cnt_2, cnt_3);
            calc_result(cnt_2 - min, cnt_3 - min, cnt_5, cnt_7, sb);
            for (int i = 0; i < min; i++)
                sb.append('6');
            return;
        }
        if (cnt_5 > 0) {
            calc_result(cnt_2, cnt_3, 0, cnt_7, sb);
            for (int i = 0; i < cnt_5; i++)
                sb.append('5');
            return;
        }
        if (cnt_2 >= 2) {
            int n = cnt_2 / 2;
            calc_result(cnt_2 % 2, cnt_3, cnt_5, cnt_7, sb);
            for (int i = 0; i < n; i++)
                sb.append('4');
            return;
        }
        if (cnt_3 > 0) {
            calc_result(cnt_2, 0, cnt_5, cnt_7, sb);
            for (int i = 0; i < cnt_3; i++)
                sb.append('3');
            return;
        }

        if (cnt_2 > 0) {
            calc_result(0, cnt_3, cnt_5, cnt_7, sb);
            for (int i = 0; i < cnt_2; i++)
                sb.append('2');
            return;
        }
    }

    private static void divide(int a) {
        for (int i = n - 1; i > 0; i--) {
            digits[i - 1] += 10 * (digits[i] % a);
            digits[i] /= a;
        }
        digits[0] /= a;
    }

    private static boolean can_divided_by_3() {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += digits[i];
        }
        return sum % 3 == 0;
    }
}