package com.leetcode;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Created by Administrator on 13-12-4.
 */
public class RPN {
    private static Set<String> ops = new HashSet<String>();

    public int evalRPN(String[] tokens) {
        if (tokens == null || tokens.length == 0)
            return 0;
        if (ops.isEmpty())
            init();
        Stack<Integer> stack = new Stack<Integer>();
        for (int i = 0, len = tokens.length; i < len; i++) {
            String token = tokens[i];
            if (ops.contains(token)) {
                int a = stack.pop();
                int b = stack.pop();
                int c = calc(token, b, a);
                stack.push(c);
            } else
                stack.push(Integer.valueOf(token));
        }
        return stack.pop();
    }

    private static int calc(String op, int a, int b) {
        if ("+".equals(op)) {
            return a + b;
        }
        if ("-".equals(op)) {
            return a - b;
        }
        if ("*".equals(op)) {
            return a * b;
        }
        if ("/".equals(op)) {
            return a / b;
        }
        return 0;
    }

    private void init() {
        ops.add("+");
        ops.add("-");
        ops.add("*");
        ops.add("/");
    }

    public static void main(String[] args) {
        RPN test = new RPN();
        String[] tokens = {"4", "13", "5", "/", "+"};
        System.out.println(test.evalRPN(tokens));
    }
}
