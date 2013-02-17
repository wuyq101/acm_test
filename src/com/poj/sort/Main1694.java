package com.poj.sort;

import java.util.*;

/**
 * <pre>
 *     原题：http://poj.org/problem?id=1694
 *     题意：有一棵树，根据规则放石子，规则如下，
 *               1. 每个叶子上可以直接放1个石子
 *               2. 某个节点p的每个直接子节点上都有一个石子的时候，可以将这些子节点上的石子回收（可重复利用），然后放一个石子在该节点上，
 *               求放到根节点所需的最少石子个数。
 *      分析：摆放叶子节点需要1个节点
 *               摆放某个非叶子节点所需要的最少石子个数和它的各个子节点所需的石子相关。
 *               假设各个子节点所需要的最少石子个数分别为c1,c2,c3,...,cn. 降序排序，c1>=c2>=c3>c3...
 *               假设我们先摆好子节点1的子节点,需要使用c1个石子，回收，使用1个放在子节点1上。如果c1>c2--->c1-1>=c2,剩余的石子摆放子节点2够了。
 *               如果c1==c2,那么c1-1<c2,摆放c2的子节点还不够，需要增加1个。这样某个子节点需要ci个石子，那么那些大于ci的节点上必须放1个，一共是i个（i从0开始）。
 *               所以排序之后，求max{i+ci}，就是该节点所需要的最少石子个数。递归可求得根节点所需要的石子个数。
 * </pre>
 * User: wuyq101
 * Date: 13-2-17
 * Time: 下午3:31
 */
public class Main1694 {
    private static class Node {
        //到达该节点需要的最少个数
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
        //是一个叶子节点
        if (node.children == null || node.children.isEmpty()) {
            node.cnt = 1;
            return;
        }
        //考虑各个子节点的情况
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
