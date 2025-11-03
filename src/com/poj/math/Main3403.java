package com.poj.math;

import java.util.Scanner;

/**
 * 3403
 * http://poj.org/problem?id=3403
 * 
 * @author vanguard001
 * @version 1.0
 */
public class Main3403 {
    public static void main(String[] args) {
	    Scanner cin = new Scanner(System.in);
	    int i=0;
	    long [][] m = new long[4][3];
	    while(i++<4){
	    	String s = cin.nextLine();
	    	String[] parts = s.split(":");
		m[i-1][0] = Long.parseLong(parts[0]);
		m[i-1][1] = Long.parseLong(parts[1]);
		m[i-1][2] = Long.parseLong(parts[2]);
	    }
	    for (int j = 0; j < 4; j++) {
	    	for (int k = 0; k < 3; k++) {
	    		System.out.print(m[j][k]);
	    		System.out.print(" ");
	    	}
	    	System.out.println();
	    }
	    long d1 = gcd(m[0][0], m[1][0]);
	    d1 = gcd(d1, m[2][0]);
	    long d2 = gcd(m[0][1], m[1][1]);
	    d2 = gcd(d2, m[2][1]);
	    long d3 = gcd(m[0][2], m[1][2]);
	    d3 = gcd(d3, m[2][2]);
	    System.out.println(d1);
	    System.out.println(d2);
	    System.out.println(d3);
    }

    public static long gcd(long a, long b) {
	if (b == 0)
	    return a;
	return gcd(b, a % b);
    }
}
