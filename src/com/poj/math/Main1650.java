package com.poj.math;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * <pre>
 * http://poj.org/problem?id=1650
 * 题意：给定一个浮点数A，求写成分数的形式N/D，使得值最接近，其中N和D都在给定范围L内，1 <= N, D <= L。
 * 分析：要求误差|A - N / D| 最小，在给定分母D的情况下，如果误差最小的话，N可以直接计算，N是与A*D最接近的整数，通过ceil函数
 *           取得N'=ceil(A*D), 比较 N'-A*D和A*D-N'+1的大小比较，可以确定N是去N’还是取N'-1。确定N之后，计算误差，与当前最小误
 *           差比较，更新目前的最优解。因为这个误差值和分母直接没有明显的线性关心，所以分母不能使用二分等快速确定，只能一个一个
 *           枚举，最终确定答案。
 *   注意：因为N和D的取值范围是[1,L]，注意边界值的处理。另外误差值大小比较时，为避免2/5==4/10这种可以约分的分数出现，可以
 *            使用这样来比较大小：e+1e-10<error。
 *  </pre>
 * User: wuyq101
 * Date: 13-6-14
 * Time: 下午3:18
 */
public class Main1650 {

    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        double A = Double.parseDouble(cin.readLine());
        int L = Integer.parseInt(cin.readLine());
        solve(A, L);
    }

    private static void solve(double A, int L) {
        double error = L;
        int n = 0, d = 0;
        for (int i = 1; i <= L; i++) {
            double ad = A * i;
            int N = (int) Math.ceil(ad);
            if (ad - N + 1 < N - ad)
                N = N - 1;
            if (N > L) N = L;
            if (N < 1) N = 1;
            double e = Math.abs((ad - N) / i);
            if (e + 1e-10 < error) {
                error = e;
                n = N;
                d = i;
            }
        }
        System.out.printf("%d %d\n", n, d);
    }
}
