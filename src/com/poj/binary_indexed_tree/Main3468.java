package com.poj.binary_indexed_tree; /** http://poj.org/problem?id=3468 */

import java.util.Scanner;

public class Main3468 {
  private static int n, q;
  private static long[] A;

  public static void main(String[] args) {
    Scanner cin = new Scanner(System.in);
    n = cin.nextInt();
    q = cin.nextInt();
    A = new long[n + 1];
    for (int i = 0; i < n; i++) {
      int value = cin.nextInt();
      add(i + 1, value);
    }
    for (int i = 0; i < q; i++) {
      String op = cin.next();
      if ("Q".equals(op)) {
        int a = cin.nextInt();
        int b = cin.nextInt();
        query(a, b);
      } else {
        int a = cin.nextInt();
        int b = cin.nextInt();
        int c = cin.nextInt();
        rangeAdd(a, b, c);
      }
    }
  }

  private static void rangeAdd(int a, int b, int c) {
    for (int i = a; i <= b; i++) {
      add(i, c);
    }
  }

  private static void query(int a, int b) {
    System.out.println(sum(b) - sum(a - 1));
  }

  private static long sum(int i) {
    long s = 0;
    while (i > 0) {
      s += A[i];
      i -= i & (-i);
    }
    return s;
  }

  private static void add(int i, int value) {
    while (i <= n) {
      A[i] += value;
      i += i & (-i);
    }
  }
}
