package com.poj.sort;

import java.util.*;

/**
 * User: wuyq101
 * Date: 13-2-17
 * Time: 下午3:31
 */
public class Main1694 {
    private static class Node {
        //到达该结点需要的最少个数
        int cnt;
        List<Integer> children;
    }

    private static int N;
    private static Node[] nodes = new Node[200];
    private static List<Integer> children_cnt = new ArrayList<Integer>();

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int M = cin.nextInt();
        while (M-- > 0) {
            input(cin);
            solve();
        }
    }

    private static void solve() {
        count(1);
        System.out.println(nodes[1].cnt);
    }

    private static void count(int label) {
        Node node = nodes[label];
        //是一个叶子结点
        if (node.children == null || node.children.isEmpty()) {
            node.cnt = 1;
            return;
        }
        //考虑各个子结点的情况
        for (int child : node.children) {
            count(child);
        }
        children_cnt.clear();
        for (int child : node.children) {
            children_cnt.add(nodes[child].cnt);
        }
        Collections.sort(children_cnt, new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return -a.compareTo(b);
            }
        });
        int max = 0;
        for (int i = 0, len = children_cnt.size(); i < len; i++) {
            if (max < i + children_cnt.get(i)) {
                max = i + children_cnt.get(i);
            }
        }
        node.cnt = max;
    }

    private static void input(Scanner cin) {
        N = cin.nextInt();
        Node node = null;
        for (int i = 0; i < N; i++) {
            int label = cin.nextInt();
            node = nodes[label];
            if (node == null) {
                node = new Node();
                nodes[label] = node;
            } else {
                node.cnt = 0;
                if (node.children != null)
                    node.children.clear();
            }
            int cnt = cin.nextInt();
            if (cnt == 0)
                continue;
            if (node.children == null)
                node.children = new ArrayList<Integer>();
            for (int j = 0; j < cnt; j++) {
                node.children.add(cin.nextInt());
            }
        }
    }
}
