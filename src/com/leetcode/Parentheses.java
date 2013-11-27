package com.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Created by Administrator on 13-11-26.
 */
public class Parentheses {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<Character>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
                continue;
            } else {
                if (stack.isEmpty())
                    return false;
                char top = stack.peek();
                if ((top == '(' && ch != ')') || (top == '[' && ch != ']') || (top == '{' && ch != '}'))
                    return false;
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    public ArrayList<String> generateParenthesis(int n) {
        Set<String> set = generate(n);
        ArrayList<String> list = new ArrayList<String>();
        list.addAll(set);
        return list;
    }

    private static Map<Integer, Set<String>> map = new HashMap<Integer, Set<String>>();

    private Set<String> generate(int n) {
        if (map.containsKey(n))
            return map.get(n);
        if (n == 1) {
            Set<String> set = new HashSet<String>();
            set.add("()");
            map.put(1, set);
            return set;
        }
        Set<String> set = generate(n - 1);
        Set<String> s = new HashSet<String>();
        for (String str : set) {
            for (int i = 0; i < str.length(); i++) {
                s.add(str.substring(0, i) + "()" + str.substring(i));
            }
        }
        map.put(n, s);
        return s;
    }

    public static void main(String[] args) {
        String str = "]";
        Parentheses t = new Parentheses();
        System.out.println(t.generateParenthesis(1));
        System.out.println(t.generateParenthesis(2));
        System.out.println(t.generateParenthesis(3));
        System.out.println(t.generateParenthesis(4));
    }
}
