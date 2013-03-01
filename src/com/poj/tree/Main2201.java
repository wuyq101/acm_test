package com.poj.tree;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * <pre>
 *     原题 ：http://poj.org/problem?id=2201
 *     题意：笛卡尔树
 *     分析：笛卡尔树的结构和treap一模一样。从key的角度看，是一棵二叉搜索书，从value的角度看，是一个堆，所以叫树堆（Tree+Heap=Treap）。
 *               笛卡尔树是给定key，value对，构造来解决应用问题，treap是为了让key更加平衡又随机增加了value权重，主要是为key搜索的作用。没本质区别。
 *               构造笛卡尔树的时候，先将结点安照key排序。
 *               然后从最小的第一个结点开始，往树中插入结点。因为新插入的结点key值大于树中的任何一个结点key值，所以，新插入结点的位置一定在树的最
 *               右端。插入之后如果value值小于其父结点的value值，则将该结点取代父结点p，并将父结点设置为该结点的左子结点，该结点的原来左子结点
 *               设置为p的右子结点。重复该过程，直到当前结点的value大于其父结点的value值为止。
 *               从上面插入的过程中，我们可以知道，最后一个插入的结点一直保持在最右端。所以新插入结点的时候，可以直接挂在上一个插入结点的右结点上。
 * </pre>
 * User: wuyq101
 * Date: 13-2-28
 * Time: 下午1:49
 */
public class Main2201 {
    private static class Node {
        int key, value, id;
        Node left, right, parent;
    }

    private static int n;
    private static Node[] nodes = new Node[50000];
    private static int[] P = new int[50001], L = new int[50001], R = new int[50001];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        n = cin.nextInt();
        for (int i = 0; i < n; i++) {
            if (nodes[i] == null)
                nodes[i] = new Node();
            nodes[i].id = i + 1;
            nodes[i].key = cin.nextInt();
            nodes[i].value = cin.nextInt();
        }
        solve();
    }

    private static void solve() {
        build_cartesian_tree();
        print();
        StringBuilder sb = new StringBuilder();
        sb.append("YES\n");
        for (int i = 1; i <= n; i++) {
            sb.append(P[i]).append(' ').append(L[i]).append(' ').append(R[i]).append('\n');
        }
        System.out.printf(sb.toString());
    }

    private static void print() {
        for (int i = 0; i < n; i++) {
            Node node = nodes[i];
            Node p = node.parent, left = node.left, right = node.right;
            int id = node.id;
            P[id] = p == null ? 0 : p.id;
            L[id] = left == null ? 0 : left.id;
            R[id] = right == null ? 0 : right.id;
        }
    }

    private static void build_cartesian_tree() {
        //根据key排序
        Arrays.sort(nodes, 0, n, new Comparator<Node>() {
            @Override
            public int compare(Node a, Node b) {
                return a.key < b.key ? -1 : 1;
            }
        });
        right_most = nodes[0];
        for (int i = 1; i < n; i++) {
            insert(nodes[i]);
            right_most = nodes[i];
        }
    }

    private static Node right_most;

    private static void insert(Node node) {
        right_most.right = node;
        node.parent = right_most;
        while (node.parent != null && node.parent.value > node.value) {
            Node p = node.parent;
            if (p.parent != null) {
                p.parent.right = node;
                node.parent = p.parent;
            } else {
                node.parent = null;
            }
            Node xl = node.left;
            if (xl != null) {
                xl.parent = p;
                p.right = xl;
            } else
                p.right = null;
            node.left = p;
            p.parent = node;
        }
    }
}
