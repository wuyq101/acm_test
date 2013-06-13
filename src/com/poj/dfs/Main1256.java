package com.poj.dfs;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * <pre>
 *     http://poj.org/problem?id=1256
 *     题意：根据规定的字母顺序，输出一个字符串的全排列。
 *     分析：深度优先遍历，先将字符串排序，然后枚举。
 *               注意每枚举到一个答案的时候，与前一个答案比较是否相同。
 *               另外，枚举的时候避免重复字母的处理，如果在第n层，已经枚举了某个字母，相同层就不需要枚举剩余的相同的字母。
 * </pre>
 * User: wuyq101
 * Date: 13-6-13
 * Time: 上午11:35
 */
public class Main1256 {
    private static char[] word;
    private static String last;


    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(cin.readLine());
        while (t-- > 0) {
            word = cin.readLine().toCharArray();
            generate();
        }
    }

    private static void generate() {
        //先排序
        int len = word.length;
        last = null;
        for (int i = 0; i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                if (cmp(word[i], word[j]) > 0) {
                    char t = word[i];
                    word[i] = word[j];
                    word[j] = t;
                }
            }
        }
        dfs("", word);
    }

    private static void dfs(String pre, char[] w) {
        if (w.length == 1) {
            pre = pre + w[0];
            if (last == null || !pre.equals(last)) {
                last = pre;
                System.out.println(pre);
                return;
            }
        }
        for (int i = 0; i < w.length; i++) {
            boolean skip = false;
            for (int k = i - 1; k >= 0; k--) {
                if (w[i] == w[k]) {
                    skip = true;
                    break;
                }
            }
            if (skip) continue;
            char[] next = new char[w.length - 1];
            copy(w, next, i);
            dfs(pre + w[i], next);
        }
    }

    private static void copy(char[] w, char[] next, int idx) {
        for (int i = 0, j = 0; i < w.length; i++)
            if (i != idx)
                next[j++] = w[i];
    }


    private static int cmp(char a, char b) {
        if (a == b) return 0;
        if (Character.isUpperCase(a)) {
            if (Character.isUpperCase(b)) {
                //ab都是大写
                return a < b ? -1 : 1;
            }
            //a大写，b是小写
            return Character.toLowerCase(a) <= b ? -1 : 1;
        } else {
            if (Character.isUpperCase(b)) {
                //a 小写， b大写
                char bb = Character.toLowerCase(b);
                if (bb == a) return 1;
                return a < bb ? -1 : 1;
            } else {
                //a 小写， b小写
                return a < b ? -1 : 1;
            }
        }
    }
}
