package com.poj.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by wuyq on 15/10/4.
 */
public class Main2234 {
    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        String line = cin.readLine();
        while (line != null && line.length() > 0) {
            String[] s = line.split(" ");
            int m = Integer.parseInt(s[0]);
            int r = 0;
            for (int i = 1; i <= m; i++) {
                r ^= Integer.parseInt(s[i]);
            }
            System.out.println(r != 0 ? "Yes" : "No");
            line = cin.readLine();
        }
    }
}
