package com.poj;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 1204
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main1204 {
    private static int L;
    private static int C;
    private static int W;
    private static char[][] puzzle;
    private static char[][] words;
    // 最大的单词长度
    private static int max;
    private static int[] len;

    // 8个方向
    private static int[] dir_r = { -1, -1, 0, 1, 1, 1, 0, -1 };
    private static int[] dir_c = { 0, 1, 1, 1, 0, -1, -1, -1 };

    private static Node root = new Node();

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        L = cin.nextInt();
        C = cin.nextInt();
        W = cin.nextInt();
        puzzle = new char[L][C];
        for (int i = 0; i < L; i++) {
            char[] str = cin.next().toCharArray();
            for (int j = 0; j < C; j++) {
                puzzle[i][j] = str[j];
            }
        }
        words = new char[W][];
        max = 0;
        len = new int[1001];
        for (int i = 0; i < W; i++) {
            char[] str = cin.next().toCharArray();
            words[i] = str;
            if (str.length > max)
                max = str.length;
            len[str.length] = 1;
        }
        build_trie();
        find();
        print();
    }

    private static void print() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < W; i++) {
            char[] w = words[i];
            Node rt = root;
            int idx = 0;
            for (int j = 0; j < w.length; j++) {
                idx = w[j] - 'A';
                rt = rt.children[idx];
            }
            Info info = rt.info;
            // System.out.printf("%d %d %c\n", info.r, info.c, info.d);
            sb.append(info.r).append(' ').append(info.c).append(' ').append(info.d).append('\n');
        }
        System.out.print(sb.toString());
    }

    private static void build_trie() {
        root.children = new Node[26];
        for (int i = 0; i < W; i++) {
            Node rt = root;
            char[] w = words[i];
            int idx = 0;
            for (int j = 0; j < w.length; j++) {
                idx = w[j] - 'A';
                if (rt.children == null)
                    rt.children = new Node[26];
                if (rt.children[idx] == null)
                    rt.children[idx] = new Node();
                rt = rt.children[idx];
            }
            rt.info = new Info();
        }
    }

    private static void find() {
        int left = words.length;
        // 每一个位置
        for (int i = 0; i < L && left > 0; i++) {
            for (int j = 0; j < C && left > 0; j++) {
                // 每一个方向
                for (int k = 0; k < 8 && left > 0; k++) {
                    int r = i;
                    int c = j;
                    int l = 1;
                    int idx = 0;
                    Node rt = root;
                    while (r >= 0 && r < L && c >= 0 && c < C && l <= max) {
                        idx = puzzle[r][c] - 'A';
                        if (rt == null)
                            break;
                        if (rt.children == null)
                            break;
                        if (rt.children[idx] == null)
                            break;
                        rt = rt.children[idx];
                        if (rt.info != null && rt.info.r == -1) {
                            rt.info.r = i;
                            rt.info.c = j;
                            rt.info.d = (char) ('A' + k);
                            left -= 1;
                        }
                        // 下一格
                        r += dir_r[k];
                        c += dir_c[k];
                        l += 1;
                    }
                }
            }
        }
    }

    private static class Node {
        public Info info;
        public Node[] children;
    }

    private static class Info {
        public int r = -1;
        public int c;
        public char d;
    }

}
