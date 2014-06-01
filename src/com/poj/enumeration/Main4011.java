package com.poj.enumeration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * Created by wuyq on 14-6-1.
 */
public class Main4011 {
    public static void main(String[] args) throws IOException {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(cin.readLine());
        int ata = Integer.parseInt(st.nextToken());
        System.out.println(solve(ata));
    }

    private static int solve(int ata) {
        if (ata >= 199)
            return 0;
        return 199 - ata;
    }
}
