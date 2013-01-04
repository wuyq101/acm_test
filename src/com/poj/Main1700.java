package com.poj;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1700
 * 
 * @author wyq@palmdeal.com
 * @version 1.0
 */
public class Main1700 {
    private static int N;
    private static int[] a = new int[1000];
    private static int time;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int T = cin.nextInt();
        while (T-- > 0) {
            N = cin.nextInt();
            for (int i = 0; i < N; i++) {
                a[i] = cin.nextInt();
            }
            solve();
            System.out.println(time);
        }
    }

    private static void solve() {
        time = 0;
        Arrays.sort(a, 0, N);
        int i = 0;
        for (i = N - 1; i > 2; i -= 2) {
            //让最慢的两个人分开走
            int s = a[0] + a[0] + a[i] + a[i - 1];
            //让最慢的两个人一起走
            int t = a[1] + a[1] + a[0] + a[i];
            time += s < t ? s : t;
        }
        if (i == 2) {
            time += a[0] + a[1] + a[2];
        } else if (i == 1) {
            time += a[1];
        } else {
            time += a[0];
        }
    }
}
