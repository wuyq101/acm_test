package com.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Created by Administrator on 13-12-23.
 */
public class Multiply {
    public String multiply(String num1, String num2) {
        map.clear();
        if (num1 == null || num1.length() == 0) return "";
        if (num2 == null || num2.length() == 0) return "";
        if (num1.length() < num2.length()) {
            String t = num1;
            num1 = num2;
            num2 = t;
        }
        String product = "0";
        for (int i = 0; i < num2.length(); i++) {
            int n = num2.charAt(i) - '0';
            if (n == 0) continue;
            String part = multiply(num1, n);
            part = padding(part, num2.length() - 1 - i);
            product = add(product, part);
        }
        return product;
    }

    private String add(String a, String b) {
        if (a.length() > b.length()) {
            String t = a;
            a = b;
            b = t;
        }
        int m = a.length();
        int n = b.length();
        int carry = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length(); i++) {
            int s = b.charAt(n - 1 - i) - '0';
            int t = 0;
            if (i < m) {
                t = a.charAt(m - 1 - i) - '0';
            }
            int sum = s + t + carry;
            if (sum <= 9) {
                carry = 0;
                sb.insert(0, sum);
            } else {
                carry = 1;
                sb.insert(0, sum - 10);
            }
        }
        if (carry > 0)
            sb.insert(0, carry);
        return sb.toString();
    }

    private String padding(String part, int n) {
        StringBuilder sb = new StringBuilder(part);
        for (int i = 0; i < n; i++)
            sb.append("0");
        return sb.toString();
    }

    private static Map<Integer, String> map = new HashMap<Integer, String>();

    private String multiply(String num1, int n) {
        if (map.containsKey(n))
            return map.get(n);
        StringBuilder sb = new StringBuilder();
        int carry = 0;
        for (int i = num1.length() - 1; i >= 0; i--) {
            int a = num1.charAt(i) - '0';
            int p = a * n + carry;
            sb.insert(0, p % 10);
            carry = p / 10;
        }
        if (carry > 0)
            sb.insert(0, carry);
        String sum = sb.toString();
        map.put(n, sum);
        return sum;
    }

    public int jump(int[] A) {
        int cnt = 0;
        int right = 0;
        int cur = 0;
        for (int i = 0; i < A.length; i++) {
            if (i > right) {
                right = cur;
                cnt++;
            }
            cur = Math.max(cur, i + A[i]);
        }
        return cnt;
    }

    public String countAndSay(int n) {
        String s = "1";
        for (int i = 1; i < n; i++) {
            s = nextSequence(s);
            System.out.printf("%d : %s\n", i + 1, s);
        }
        return s;
    }

    private String nextSequence(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            int cnt = 1;
            int j = i + 1;
            while (j < s.length() && s.charAt(j) == s.charAt(j - 1)) {
                cnt++;
                j++;
            }
            sb.append(cnt).append(s.charAt(i));
            i = j - 1;
        }
        return sb.toString();
    }

    public ArrayList<Integer> spiralOrder(int[][] matrix) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        if (matrix == null) return list;
        int m = matrix.length;
        if (m == 0) return list;
        int n = matrix[0].length;
        if (n == 0) return list;
        int min = Math.min(m, n) / 2;
        for (int layer = 0; layer <= min; layer++) {
            int top = layer, bottom = m - 1 - layer;
            int left = layer, right = n - 1 - layer;
            if (top > bottom || left > right) break;
            if (top == bottom) {
                //the last row
                for (int i = left; i <= right; i++)
                    list.add(matrix[top][i]);
                continue;
            }
            if (left == right) {
                //the last column
                for (int i = top; i <= bottom; i++)
                    list.add(matrix[i][left]);
                continue;
            }
            //top row
            for (int i = left; i < right; i++)
                list.add(matrix[top][i]);
            //right column
            for (int i = top; i < bottom; i++)
                list.add(matrix[i][right]);
            //bottom row
            for (int i = right; i > left; i--)
                list.add(matrix[bottom][i]);
            //left column
            for (int i = bottom; i > top; i--)
                list.add(matrix[i][left]);
        }
        return list;
    }


    public String simplifyPath(String path) {
        if (path == null || path.length() == 0) return path;
        //remove the tail '/'
        while (path.length() > 0 && path.charAt(path.length() - 1) == '/')
            path = path.substring(0, path.length() - 1);
        if (path.length() == 0) return "/";
        Stack<String> stack = new Stack<String>();
        for (int i = 0; i < path.length(); i++) {
            if (i + 1 < path.length() && path.charAt(i + 1) == '/') {
                continue;
            }
            //find the next part
            int j = i + 1;
            while (j < path.length() && path.charAt(j) != '/') {
                j++;
            }
            String part = path.substring(i, j);
            i = j - 1;
            if (part.equals("/..")) {
                if (!stack.isEmpty())
                    stack.pop();
                continue;
            }
            if (part.equals("/.")) {
                continue;
            }
            stack.push(part);

        }
        StringBuilder sb = new StringBuilder();
        if (stack.isEmpty()) return "/";
        while (!stack.isEmpty()) {
            sb.insert(0, stack.pop());
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        String path = "/home///////foo/";
        Multiply test = new Multiply();
        String s = test.simplifyPath(path);
        System.out.println(s);
    }
}
