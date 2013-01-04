package com.poj;
import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1023 变态2进制
 * 
 * @author vanguard001
 * @version 1.0
 */
public class Main1023 {
    private static int k;
    private static Long N;
    private static int[] sign = new int[65];
    private static int[] answer = new int[65];

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        int t = cin.nextInt();
        while (t-- > 0) {
            k = cin.nextInt();
            String pn = cin.next();
            for (int i = 0; i < k; i++) {
                // 1代表正位,0代表负
                sign[k - 1 - i] = pn.charAt(i) == 'p' ? 1 : -1;
            }
            N = cin.nextLong();
            solve();
        }
    }

    private static void solve() {
        Arrays.fill(answer, 0);
        for (int i = 0; i < k && N != 0; i++) {
            int b = (int) (N & 1);
            if (b == 0) {
                answer[i] = 0;
                N = N >> 1;
                continue;
            }
            answer[i] = 1;
            N = (N - sign[i]) >> 1;
        }
        if (N != 0) {
            System.out.println("Impossible");
        } else {
            StringBuilder sb = new StringBuilder();
            for (int i = k - 1; i >= 0; i--) {
                sb.append(answer[i]);
            }
            System.out.println(sb.toString());
        }
    }
}