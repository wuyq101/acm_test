package com.poj.dfs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <pre>
 *     http://poj.org/problem?id=3107
 *     参考poj 2378
 * </pre>
 * User: wuyq101
 * Date: 13-5-31
 * Time: 下午4:30
 */
public class Main3107 {
    private static int N;
    private static Node[] nodes = new Node[50001];

    private static class Node {
        int num, cnt;
        Node parent;
        List<Node> children;
        boolean visited = false;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(cin.readLine());
        init();
        for (int i = 0; i < N - 1; i++) {
            StringTokenizer st = new StringTokenizer(cin.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if (nodes[b].parent == null) {
                insertNode(nodes[a], nodes[b]);
                continue;
            }
            if (nodes[a].parent == null) {
                insertNode(nodes[b], nodes[a]);
                continue;
            }
            //两个结点都已经有父亲结点
            reverse(nodes[a]);
            reverse(nodes[b]);
            insertNode(nodes[b], nodes[a]);
        }
        dfs(nodes[1]);
        count();
    }

    private static void reverse(Node node) {
        Node cur = node;
        Node p = cur.parent;
        node.parent = null;
        while (p != null) {
            Node temp_p = p.parent;
            p.children.remove(cur);
            p.parent = cur;
            if (cur.children == null)
                cur.children = new ArrayList<Node>();
            cur.children.add(p);
            cur = p;
            p = temp_p;
        }
    }

    private static void insertNode(Node parent, Node child) {
        if (parent.children == null)
            parent.children = new ArrayList<Node>();
        child.parent = parent;
        parent.children.add(child);
    }

    private static void init() {
        for (int i = 1; i <= N; i++) {
            if (nodes[i] == null)
                nodes[i] = new Node();
            nodes[i].num = i;
            nodes[i].cnt = 1;
            nodes[i].visited = false;
            if (nodes[i].children != null)
                nodes[i].children.clear();
            nodes[i].parent = null;
        }
    }

    private static void dfs(Node node) {
        if (node.visited)
            return;
        node.visited = true;
        if (node.children != null) {
            for (int i = 0; i < node.children.size(); i++) {
                Node child = node.children.get(i);
                dfs(child);
                node.cnt += child.cnt;
            }
        }
        if (node.parent != null)
            dfs(node.parent);
    }

    private static void count() {
        int min = N + 1;
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i <= N; i++) {
            int b = getBalance(nodes[i]);
            if (b < min) {
                list.clear();
                list.add(i);
                min = b;
            } else if (b == min) {
                list.add(i);
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i)).append(' ');
        }
        System.out.println(sb.deleteCharAt(sb.length() - 1).toString());
    }

    private static int getBalance(Node node) {
        int b = 0;
        if (node.children != null) {
            for (Node child : node.children) {
                if (child.cnt > b) {
                    b = child.cnt;
                }
            }
        }
        if (node.parent != null) {
            int bp = N - node.cnt;
            if (bp > b)
                b = bp;
        }
        return b;
    }
}
