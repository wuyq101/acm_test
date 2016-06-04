package com.poj.simulate;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * http://poj.org/problem?id=3183
 * Created by wuyq on 16/6/1.
 */
public class Main3183 {
    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(cin.readLine());
        int cur = Integer.parseInt(cin.readLine());
        for (int i = 2; i <= n; ) {
            int next = Integer.parseInt(cin.readLine());
            if (cur < next) {
                cur = next;
                i++;
            } else {
                System.out.println(i - 1);
                i++;
                while (next < cur) {
                    cur = next;
                    if (i <= n) {
                        next = Integer.parseInt(cin.readLine());
                        i++;
                    } else {
                        return;
                    }
                }
                cur = next;
            }
        }
        System.out.println(n);
    }
}
