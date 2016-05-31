package com.poj.bfs;

import java.util.*;

/**
 * http://poj.org/problem?id=3191
 * Created by wuyq on 16/5/31.
 */
public class Main3191 {
    private static class Node {
        HashSet<Long> set;
        long n;
    }

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int n = cin.nextInt();
        System.out.println(calc(n));
    }

    private static String calc(int n) {
        if (n == 0) {
            return "0";
        }
        LinkedList<Node> q = new LinkedList<Node>();
        Node node = new Node();
        node.set = new HashSet<Long>();
        node.n = n;
        q.add(node);
        while (!q.isEmpty()) {
            node = q.removeFirst();

            long p = Math.abs(node.n);
            long i = 0, base = 1;
            while (true) {
                if (base <= p && p <= base * 2) {
                    break;
                }
                i++;
                base *= 2;
            }
            for (long j = i; j <= i + 1; j++) {
                int sign = i % 2 == 0 ? 1 : -1;
                Node temp = new Node();
                temp.set = (HashSet<Long>) node.set.clone();
                temp.n = node.n - base * sign;
                temp.set.add(i);
                if (temp.n == 0) {
                    return set2String(temp.set);
                }
                q.addLast(temp);
            }

            if (!node.set.contains(i + 1)) {
                int sign = (i + 1) % 2 == 0 ? 1 : -1;
                Node temp = new Node();
                temp.set = (HashSet<Long>) node.set.clone();
                temp.n = node.n - base * 2 * sign;
                temp.set.add(i + 1);
                if (temp.n == 0) {
                    return set2String(temp.set);
                }
                q.addLast(temp);
            }
        }
        return "";
    }

    private static String set2String(HashSet<Long> set) {
        long max = 0;
        for (long i : set) {
            if (i > max) {
                max = i;
            }
        }
        char[] ch = new char[(int) (max + 1)];
        Arrays.fill(ch, '0');
        for (long i : set) {
            ch[((int) i)] = '1';
        }
        StringBuilder sb = new StringBuilder();
        for (int i = (int) max; i >= 0; i--) {
            sb.append(ch[i]);
        }
        return sb.toString();
    }
}