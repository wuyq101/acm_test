package com.leetcode;

import java.util.Stack;

/**
 * Created by Administrator on 13-12-15.
 */
public class Expression {
    //2*3+5
    public double calc(String express) {
        Stack<String> post = convertToPost(express);
        return calc(post);
    }

    private double calc(Stack<String> post) {
        Stack<Double> num = new Stack<Double>();
        while (!post.isEmpty()) {
            String s = post.pop();
            if (s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")) {
                Double a = num.pop();
                Double b = num.pop();
                Double c = op(b, a, s);
                num.push(c);
                continue;
            }
            num.push(Double.valueOf(s));
        }
        return num.pop();
    }

    private Double op(Double a, Double b, String s) {
        if ("+".equals(s))
            return a + b;
        if ("-".equals(s))
            return a - b;
        if ("*".equals(s))
            return a * b;
        if ("/".equals(s))
            return a / b;
        return null;
    }

    private Stack<String> convertToPost(String express) {
        Stack<String> op = new Stack<String>();
        Stack<String> post = new Stack<String>();
        op.push("(");
        for (int i = 0; i < express.length(); i++) {
            char ch = express.charAt(i);
            if (ch == '(') {
                op.push("(");
                continue;
            }
            if (ch == ')') {
                while (!"(".equals(op.peek()))
                    post.push(op.pop());
                op.pop();
                continue;
            }
            if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                String pre = op.peek();
                while (!pre.equals("(") && cmp(pre, ch)) {
                    post.push(op.pop());
                    pre = op.peek();
                }
                op.push(String.valueOf(ch));
                continue;
            }
            //digital
            int j = i;
            while (j + 1 < express.length() && Character.isDigit(express.charAt(j + 1)))
                j = j + 1;
            String num = express.substring(i, j + 1);
            post.push(num);
            i = j;
        }
        String pre = op.peek();
        while (!pre.equals("(")) {
            post.push(op.pop());
            pre = op.peek();
        }
        op.pop();
        while (!post.isEmpty()) {
            op.push(post.pop());
        }
        return op;
    }

    private boolean cmp(String pre, char ch) {
        if (ch == '+' || ch == '-') {
            return true;
        }
        if (ch == '*' || ch == '/') {
            if (pre.equals("+") || pre.equals("-"))
                return false;
            return true;
        }
        return true;
    }

    public static void main(String[] args) {
        String test = "2*3+4*4-1+(9/(3+6)-3)*2";
        Expression t = new Expression();
        System.out.println(t.calc(test));
    }
}
