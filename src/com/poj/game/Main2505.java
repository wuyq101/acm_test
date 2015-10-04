package com.poj.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * [2,9] true 必胜           2         9
 * [9+1,9*2] false 必败      10        18
 * [9*2+1,9*2*9] true       19        162
 * [18*9+1,9*2*9*2] false   163       324
 * [18*18+1,18*18*9] true   325       2916
 * [18*18*9+1, 18*18*9*2] false 2917   5832
 * ...
 * N
 * Created by wuyq on 15/10/4.
 */
public class Main2505 {
    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        String line = cin.readLine();
        while (line != null && line.length() > 0) {
            double n = Double.parseDouble(line);
            while (n > 18) {
                n /= 18.0;
            }
            if (n <= 9) {
                System.out.println("Stan wins.");
            } else {
                System.out.println("Ollie wins.");
            }
            line = cin.readLine();
        }
    }
}
