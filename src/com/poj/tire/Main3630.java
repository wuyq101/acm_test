package com.poj.tire;
import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * 3630 trie树
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main3630 {
    private static int n;
    private static String[] phones;
    private static Node root;

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        int t = cin.nextInt();
        while (t-- > 0) {
            n = cin.nextInt();
            phones = new String[n];
            for (int i = 0; i < n; i++) {
                phones[i] = cin.next();
            }
            //按照长度排序
            Arrays.sort(phones, new Comparator<String>() {
                @Override
                public int compare(String s1, String s2) {
                    if(s1.length() == s2.length())
                        return 0;
                    return s1.length() > s2.length() ? 1 : -1;
                }
            });
            boolean flag = true;
            root = new Node();
            for (int i = 0; i < n; i++) {
                Node rt = root;
                char[] str = phones[i].toCharArray();
                if (!flag)
                    continue;
                // insert
                for (int j = 0; j < str.length; j++) {
                    int idx = str[j] - '0';
                    if (rt.children == null)
                        rt.children = new Node[10];
                    if (rt.children[idx] == null)
                        rt.children[idx] = new Node();
                    rt = rt.children[idx];
                    if (rt.phone) {
                        flag = false;
                        break;
                    }
                }
                if (flag)
                    rt.phone = true;
            }
            // print
            System.out.println(flag ? "YES" : "NO");
        }
    }

    static class Node {
        public Node[] children;
        public boolean phone;
    }
}
