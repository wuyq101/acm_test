package com.poj.hash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * User: wuyq101
 * Date: 13-3-13
 * Time: 下午7:23
 */
public class Main2002 {
    private static class Point {
        int x, y;
    }

    private static class Node {
        int y;
        Node next;
    }

    private static int n;
    private static Point[] points = new Point[1000];
    private static Node[] nodes = new Node[40001];
    private static Node[] nodes_1 = new Node[40001];

    public static void main(String[] args) throws IOException {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        while (true) {
            st = new StringTokenizer(cin.readLine());
            n = Integer.parseInt(st.nextToken());
            if (n == 0) break;
            init();
            for (int i = 0; i < n; i++) {
                st = new StringTokenizer(cin.readLine());
                if (points[i] == null)
                    points[i] = new Point();
                points[i].x = Integer.parseInt(st.nextToken());
                points[i].y = Integer.parseInt(st.nextToken());
                add(points[i].x, points[i].y);
            }
            solve();
        }
    }

    private static void init() {
        for (int i = 0; i < 40001; i++) {
            nodes[i] = null;
            nodes_1[i] = null;
        }
    }

    private static void add(int x, int y) {
        x += 20000;
        y += 20000;
        if ((x & 1) == 0) {
            if (nodes[x] == null) {
                nodes[x] = new Node();
                nodes[x].y = y;
            } else {
                Node node = nodes[x];
                while (node.next != null)
                    node = node.next;
                node.next = new Node();
                node.next.y = y;
            }
        } else {
            if (nodes_1[y] == null) {
                nodes_1[y] = new Node();
                nodes_1[y].y = x;
            } else {
                Node node = nodes_1[y];
                while (node.next != null)
                    node = node.next;
                node.next = new Node();
                node.next.y = x;
            }
        }
    }

    private static void solve() {
        Arrays.sort(points, 0, n, new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {
                if (a.x != b.x)
                    return a.x < b.x ? -1 : 1;
                if (a.y != b.y)
                    return a.y < b.y ? -1 : 1;
                return 0;
            }
        });
        int x3, y3, x4, y4, cnt = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                x3 = points[i].x - points[j].y + points[i].y;
                y3 = points[i].y + points[j].x - points[i].x;
                x4 = points[j].x - points[j].y + points[i].y;
                y4 = points[j].y + points[j].x - points[i].x;
                if (find(x3, y3) && find(x4, y4))
                    cnt++;
            }
        }
        System.out.printf("%d\n", cnt / 2);
    }

    private static boolean find(int x, int y) {
        x += 20000;
        y += 20000;
        if ((x & 1) == 0) {
            Node node = nodes[x];
            while (node != null) {
                if (node.y == y)
                    return true;
                node = node.next;
            }
        } else {
            Node node = nodes_1[y];
            while (node != null) {
                if (node.y == x)
                    return true;
                node = node.next;
            }
        }
        return false;
    }
}
