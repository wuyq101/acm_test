package com.poj.simulate;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 1432
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main1432 {
    private static String[] M = { ".-", "-...", "-.-.", "-..", ".", "..-.", "--.", "....", "..", ".---", "-.-", ".-..",
            "--", "-.", "---", ".--.", "--.-", ".-.", "...", "-", "..-", "...-", ".--", "-..-", "-.--", "--.." };
    private static int[][] m;
    private static Node root;
    private static int L;
    private static int[] dp;
    private static char[] morse;

    public static void main(String[] args) {
        init();
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        int t = cin.nextInt();
        while (t-- > 0) {
            morse = cin.next().toCharArray();
            L = morse.length;
            root = new Node();
            int n = cin.nextInt();
            for (int i = 0; i < n; i++) {
                char[] w = cin.next().toCharArray();
                insert(w);
            }
            dp();
            System.out.printf("%d\n", dp[L - 1]);
        }
    }

    private static void dp() {
        dp = new int[L + 1];
        Node r = root;
        int i = 0;
        while (i < L) {
            int idx = morse[i] == '.' ? 0 : 1;
            if (r.ch[idx] == null)
                break;
            if (r.ch[idx].word > 0)
                dp[i] = r.ch[idx].word;
            r = r.ch[idx];
            i++;
        }
        for (i = 0; i < L; i++) {
            if (dp[i] > 0)
                find(i + 1);
        }
    }

    private static void find(int i) {
        Node r = root;
        for (int j = i; j < L; j++) {
            int idx = morse[j] == '.' ? 0 : 1;
            if (r.ch[idx] == null)
                break;
            if (r.ch[idx].word > 0)
                // 找到一个匹配的单词
                dp[j] = dp[j] + dp[i - 1] * r.ch[idx].word;
            r = r.ch[idx];
        }
    }

    private static void init() {
        m = new int[26][];
        for (int i = 0; i < 26; i++) {
            m[i] = new int[M[i].length()];
            for (int j = 0; j < M[i].length(); j++) {
                m[i][j] = M[i].charAt(j) == '.' ? 0 : 1;
            }
        }
    }

    private static void insert(char[] w) {
        Node r = root;
        for (int i = 0; i < w.length; i++) {
            int idx = w[i] - 'A';
            for (int j = 0; j < m[idx].length; j++) {
                if (r.ch[m[idx][j]] == null)
                    r.ch[m[idx][j]] = new Node();
                r = r.ch[m[idx][j]];
            }
        }
        r.word++;
    }

    static class Node {
        public int word;
        public Node[] ch = new Node[2];
    }
}
