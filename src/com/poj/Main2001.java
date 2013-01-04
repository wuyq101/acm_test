package com.poj;
import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
/**
 * 2001 trie树
 * @author wyq@palmdeal.com
 * @version 1.0
 */
public class Main2001 {
    private static Node root;
    private static Word[] words = new Word[1000];
    private static int n;

    public static void main(String[] args) {
        root = new Node();
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        while (cin.hasNext()) {
            Word word = new Word();
            word.w = cin.next().toCharArray();
            // if (word.w.equals("==="))
            // break;
            word.index = n++;
            words[word.index] = word;
            insert(word.w);
        }
        shortest_prefix();
    }

    private static void insert(char[] str) {
        Node r = root;
        int index = 0;
        for (int i = 0; i < str.length; i++) {
            index = str[i] - 'a';
            if (r.children == null)
                r.children = new Node[26];
            if (r.children[index] == null) {
                r.children[index] = new Node();
                r.children[index].cnt = 1;
            } else
                r.children[index].cnt++;
            r = r.children[index];
        }
    }

    private static void shortest_prefix() {
        Arrays.sort(words, 0, n, new Comparator<Word>() {
            @Override
            public int compare(Word w1, Word w2) {
                int len1 = w1.w.length;
                int len2 = w2.w.length;
                if (len1 == len2)
                    return 0;
                return len1 > len2 ? -1 : 1;
            }
        });
        for (int i = 0; i < n; i++) {
            Node r = root;
            char[] w = words[i].w;
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < w.length; j++) {
                char ch = w[j];
                int index = ch - 'a';
                sb.append(ch);
                if (r.children[index].cnt == 1) {
                    break;
                }
                r = r.children[index];
            }
            words[i].prefix = sb.toString();
        }
        Arrays.sort(words, 0, n, new Comparator<Word>() {
            @Override
            public int compare(Word w1, Word w2) {
                if (w1.index == w2.index)
                    return 0;
                return w1.index > w2.index ? 1 : -1;
            }
        });
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            sb.append(words[i].w).append(' ').append(words[i].prefix).append('\n');
        }
        System.out.print(sb.toString());
    }

    private static class Node {
        // 经过该节点的单词个数,如果cnt等1，那么从root到这个节点的str就是最短前缀了
        public int cnt;
        public Node[] children;
    }

    private static class Word {
        public char[] w;
        public int index;
        public String prefix;
    }
}
