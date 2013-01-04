package com.poj;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 2503 trie树
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main2503 {
    private static Node root = new Node();

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        boolean isWord = false;
        while (cin.hasNextLine()) {
            char[] line = cin.nextLine().toCharArray();
            if (!isWord) {
                int i = 0;
                for (i = 0; i < line.length; i++) {
                    if (line[i] == ' ') {
                        insert(line, i);
                        break;
                    }
                }
                if (i == line.length)
                    isWord = true;
            } else {
                find(line);
            }
        }
    }

    private static void find(char[] line) {
        Node r = root;
        boolean found = true;
        for (int j = 0; j < line.length; j++) {
            if (r.children == null) {
                found = false;
                break;
            }
            int idx = line[j] - 'a';
            if (r.children[idx] == null) {
                found = false;
                break;
            }
            if (r.children[idx].word != null) {
                System.out.println(r.children[idx].word);
                return;
            }
            r = r.children[idx];
        }
        if (!found) {
            System.out.println("eh");
        }
    }

    private static void insert(char[] line, int i) {
        Node r = root;
        for (int j = i + 1; j < line.length; j++) {
            if (r.children == null)
                r.children = new Node[26];
            int idx = line[j] - 'a';
            if (r.children[idx] == null)
                r.children[idx] = new Node();
            r = r.children[idx];
        }
        r.word = String.valueOf(line, 0, i);
    }

    static class Node {
        public String word;
        public Node[] children;
    }

}
