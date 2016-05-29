package com.poj.dfs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * http://poj.org/problem?id=3188
 * Created by wuyq on 16/4/16.
 */
public class Main3188 {
    private static int B;//一共B个按键
    private static int L;//最多L个字母
    private static int D; //D个单词
    private static String[] words = new String[1000];
    private static HashMap<Integer, HashSet<String>> dict = new HashMap<Integer, HashSet<String>>();
    private static Node root;
    private static int[] preChar2num = new int[26];
    private static int[] char2num = new int[26]; //字母到数字的映射关系
    private static int count; //总的不重复的个数
    private static int best; //当前最优解
    private static int[] bestChar2num = new int[26];

    private static class Node {
        int count;
        Node[] children;
    }

    public static void main(String[] args) throws Exception {
        input();
        solve();
        output();
    }

    private static void output() {
        StringBuilder sb = new StringBuilder();
        sb.append(best).append('\n');
        for (int i = 0; i < L; i++) {
            sb.append((char) (i + 'A'));
            if (i + 1 < L && bestChar2num[i] != bestChar2num[i + 1]) {
                sb.append('\n');
            }
        }
        System.out.println(sb.toString());
    }

    private static void solve() {
        if (B == 1) {
            //只有一个按键，特殊处理一下
            Arrays.fill(char2num, 0, L, 0);
            init();
            best = count;
            System.arraycopy(char2num, 0, bestChar2num, 0, L);
        } else {
            for (int i = 0; i < L; i++) {
                char2num[i] = i;
                preChar2num[i] = i;
            }
            best = -1;
            init();
            dfs(1, L);
        }
    }

    /**
     * @param s 从那个位置开始合并
     * @param k 当前一共有多少个按键
     */
    private static void dfs(int s, int k) {
        if (k == B) {
            Set<String> set = new HashSet<String>();
            for (int i = 0; i < L; i++) {
                if (char2num[i] != preChar2num[i]) {
                    if (dict.containsKey(i)) {
                        set.addAll(dict.get(i));
                    }
                }
            }
            for (String w : set) {
                count += delete(w, preChar2num);
            }
            for (String w : set) {
                count += insert(w, char2num);
            }
            System.arraycopy(char2num, 0, preChar2num, 0, L);
            if (count > best) {
                best = count;
                System.arraycopy(char2num, 0, bestChar2num, 0, L);
            }
            return;
        }
        for (int i = s; i < L; i++) {
            char2num[i] = char2num[i - 1];
            dfs(i + 1, k - 1);
            char2num[i] = i;
        }
    }

    private static void init() {
        root = new Node();
        count = 0;
        for (int i = 0; i < D; i++) {
            count += insert(words[i], char2num);
        }
    }

    /**
     * 根据字母到数字的映射关系，将一个单词保存到trie tree，返回总数的变化，增加或者减少
     *
     * @param word
     * @return
     */
    private static int insert(String word, int[] pos) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            int ch = word.charAt(i) - 'A';
            if (cur.children == null) {
                cur.children = new Node[26];
            }
            int idx = pos[ch];
            if (cur.children[idx] == null) {
                cur.children[idx] = new Node();
            }
            cur = cur.children[idx];
        }
        cur.count += 1;
        if (cur.count == 1) {
            return 1; //新增一个单词
        }
        if (cur.count == 2) {
            return -1; //原来的那个单词，和现在这个单词重复了，也将从总数中减掉
        }
        return 0;
    }

    /**
     * 将一个单词根据按键顺序，从trie tree中删除
     *
     * @param word
     * @return
     */
    private static int delete(String word, int[] pos) {
        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            int ch = word.charAt(i) - 'A';
            int idx = pos[ch];
            cur = cur.children[idx];
        }
        cur.count -= 1;
        if (cur.count == 0) {
            return -1;
        }
        if (cur.count == 1) {
            return 1;
        }
        return 0;
    }

    private static void input() throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        String line = cin.readLine();
        String[] nums = line.split(" ");
        B = Integer.parseInt(nums[0]);
        L = Integer.parseInt(nums[1]);
        D = Integer.parseInt(cin.readLine());
        for (int i = 0; i < D; i++) {
            words[i] = cin.readLine();
            for (int j = 0; j < words[i].length(); j++) {
                int ch = words[i].charAt(j) - 'A';
                if (!dict.containsKey(ch)) {
                    dict.put(ch, new HashSet<String>());
                }
                dict.get(ch).add(words[i]);
            }
        }
    }
}