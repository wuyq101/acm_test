package com.poj.dfs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <pre>
 *     http://poj.org/problem?id=1655
 *     题意：给出一个数，定义去掉一个结点之后组成的几棵树中结点数最多的树的节点数为这个结点的blance。求这棵数中balance最小的结点编号和balance数值。
 *     分析：深度优先，先统计每个结点的子树size，然后针对每个结点计算blance，分两步分，子树的大小，和父亲数的大小=(N-自己的大小），取较大值。
 *     注意构造树的时候，随便定义两个数哪个是父结点，哪个是子结点，关键是避免一个结点出现多个父结点。
 * </pre>
 * User: wuyq101
 * Date: 13-5-31
 * Time: 上午11:18
 */
public class Main1655 {
    private static int N, min_num, min_balance;
    private static Node[] nodes = new Node[20001];

    private static class Node {
        int num, cnt;
        Node parent;
        List<Node> children;
        boolean visited = false;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(cin.readLine());
        while (t-- > 0) {
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
                }
            }
            dfs(nodes[1]);
            count();
            System.out.printf("%d %d\n", min_num, min_balance);
        }
    }

    private static void insertNode(Node parent, Node child) {
        if (parent.children == null)
            parent.children = new ArrayList<Node>();
        child.parent = parent;
        parent.children.add(child);
    }

    private static void init() {
        min_balance = N + 1;
        min_num = N + 1;
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
        for (int i = 1; i <= N; i++) {
            int b = getBalance(nodes[i]);
            if (b < min_balance || (b == min_balance && i < min_num)) {
                min_balance = b;
                min_num = i;
            }
        }
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
