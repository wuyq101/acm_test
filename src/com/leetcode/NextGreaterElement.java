package com.leetcode;

import java.util.Stack;

/**
 * Created by Administrator on 13-12-16.
 */
public class NextGreaterElement {
    public void printNGE(int[] a) {
        Stack<Integer> stack = new Stack<Integer>();
        int[] result = new int[a.length];
        Stack<Integer> idx = new Stack<Integer>();
        stack.add(a[0]);
        idx.add(0);
        for (int i = 1; i < a.length; i++) {
            int next = a[i];
            while (!stack.isEmpty() && stack.peek() < next) {
                int element = stack.pop();
                int j = idx.pop();
                result[j] = next;
//                System.out.printf("%d %d\n", element, next);
            }
            stack.push(next);
            idx.push(i);
        }
        while (!idx.isEmpty()) {
            result[idx.pop()] = -1;
//            System.out.printf("%d %d\n", stack.pop(), -1);
        }
        for (int i = 0; i < result.length; i++) {
            System.out.printf("%d %d ---> %d\n", i, a[i], result[i]);
        }
    }

    public static void main(String[] args) {
        NextGreaterElement t = new NextGreaterElement();
        int[] a = {11, 13, 21, 3, 4, 5, 32, 12, 3443, 54, 223};
        t.printNGE(a);
    }
}
