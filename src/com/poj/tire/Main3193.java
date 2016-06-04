package com.poj.tire;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by wuyq on 16/5/31.
 */
public class Main3193 {
    private static class Node {
        Node[] children;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        String[] s = cin.readLine().split(" ");
        int m = Integer.parseInt(s[0]);
        int n = Integer.parseInt(s[1]);
        Node root = new Node();
        for (int i = 0; i < m; i++) {
            String line = cin.readLine();
            insert(root, line);
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            String line = cin.readLine();
            if (find(root, line)) {
                count++;
            }
        }
        System.out.println(count);
    }

    private static boolean find(Node root, String s) {
        for (int i = 0; i < s.length(); i++) {
            int idx = idx(s.charAt(i));
            if (root.children == null || root.children[idx] == null) {
                return false;
            }
            root = root.children[idx];
        }
        return root != null;
    }

    private static void insert(Node root, String s) {
        for (int i = 0; i < s.length(); i++) {
            int idx = idx(s.charAt(i));
            if (root.children == null) {
                root.children = new Node[56];
            }
            if (root.children[idx] == null) {
                root.children[idx] = new Node();
            }
            root = root.children[idx];
        }
    }

    /**
     * 0---25: a--z
     * 26--52: A--Z
     * 后面四个: a period (.), comma (,), question mark (?) or space
     *
     * @param c
     * @return
     */
    private static int idx(char c) {
        if (c >= 'a' && c <= 'z') {
            return c - 'a';
        }
        if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 26;
        }
        if (c == '.') {
            return 52;
        }
        if (c == ',') {
            return 53;
        }
        if (c == '?') {
            return 54;
        }
        if (c == ' ') {
            return 55;
        }
        return 0;
    }
}
