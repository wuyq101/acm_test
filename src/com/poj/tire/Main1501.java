package com.poj.tire;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * http://poj.org/problem?id=1501
 * 题意：在字母矩阵找找单词游戏，找的方向有8个，水平、垂直、两个对角线，外加每种两个方向。求给定的词是否在字母矩阵中，并求开始和结束的坐标。
 * 分析：100*100的矩阵，单词不超过100个，每个长度不超过100。先将这些单词放在一起构建tire树，然后遍历矩阵中每个位置，在每个位置上按照8个方向
 *          查找，如果找到了，就在tire树的结点上标记找到一个单词。最后打印答案。
 * User: wuyq101
 * Date: 13-6-14
 * Time: 上午8:50
 */
public class Main1501 {
    private static int len;
    private static char[][] map = new char[101][101];
    private static List<String> words = new ArrayList<String>();
    private static List<String> answer = new ArrayList<String>();
    private static Node root;
    private static int[][] dir = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}, {-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        len = Integer.parseInt(cin.readLine());
        String line;
        for (int i = 1; i <= len; i++) {
            line = cin.readLine();
            for (int j = 1; j <= len; j++) {
                char ch = line.charAt(j - 1);
                map[i][j] = ch;
            }
        }
        words.clear();
        answer.clear();
        while (true) {
            line = cin.readLine();
            if ("0".equals(line)) break;
            words.add(line);
            answer.add("Not found");
        }
        solve();
    }

    private static void solve() {
        build_trie();
        find();
        print();
    }

    private static void print() {
        for (int i = 0; i < answer.size(); i++) {
            System.out.println(answer.get(i));
        }
    }

    private static void find() {
        for (int i = 1; i <= len; i++) {
            for (int j = 1; j <= len; j++) {
                for (int k = 0; k < 8; k++) {
                    String word = "";
                    int start_r = i, start_c = j;
                    Node rt = root;
                    int r = start_r, c = start_c;
                    while (r >= 1 && r <= len && c >= 1 && c <= len) {
                        int idx = map[r][c] - 'A';
                        if (rt.children == null) break;
                        if (rt.children[idx] == null) break;
                        rt = rt.children[idx];
                        word += map[r][c];
                        if (rt.is_word) {
                            addAnswer(word, start_r, start_c, r, c);
                            rt.is_word = false;
                        }
                        r += dir[k][0];
                        c += dir[k][1];
                    }
                }
            }
        }
    }

    private static void addAnswer(String word, int start_r, int start_c, int end_r, int end_c) {
        for (int i = 0; i < words.size(); i++) {
            if (words.get(i).equals(word)) {
                answer.set(i, start_r + "," + start_c + " " + end_r + "," + end_c);
            }
        }
    }

    private static void build_trie() {
        root = new Node();
        Node rt = root;
        int idx = 0;
        for (int j = 0; j < words.size(); j++) {
            String word = words.get(j);
            rt = root;
            idx = 0;
            for (int i = 0; i < word.length(); i++) {
                idx = word.charAt(i) - 'A';
                if (rt.children == null)
                    rt.children = new Node[26];
                if (rt.children[idx] == null)
                    rt.children[idx] = new Node();
                rt = rt.children[idx];
            }
            rt.is_word = true;
        }
    }

    private static class Node {
        boolean is_word;
        Node[] children;
    }
}
