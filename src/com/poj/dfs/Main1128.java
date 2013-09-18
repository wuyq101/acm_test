package com.poj.dfs;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * <pre>
 *     原题：http://poj.org/problem?id=1128
 *     题意：一组相框组合在一起，给出最后的组合形状，求相框重叠的顺序。（确保每个相框都有露出来）
 *     分析：
 * </pre>
 * Created by Administrator on 13-9-5.
 */
public class Main1128 {
    private static int h, w, count;
    private static int[][] block = new int[30][30];
    private static int[] letters = new int[26];
    //left,top,right,bottom
    private static int[][] pos = new int[26][4];
    private static int left = 0, top = 1, right = 2, bottom = 3;
    //map[i][j]=1, means i is at the bottom of j.
    //map[i][j]=0, i,j not connected.
    //map[i][j]=-1, means i is at the top of j.
    private static int[][] map = new int[26][26];
    private static StringBuilder answer;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext()) {
            h = cin.nextInt();
            w = cin.nextInt();
            init();
            for (int i = 0; i < h; i++) {
                String line = cin.next();
                for (int j = 0; j < w; j++) {
                    char ch = line.charAt(j);
                    block[i][j] = ch == '.' ? -1 : ch - 'A';
                }
            }
            solve();
        }
    }

    private static void init() {
        for (int i = 0; i < h; i++)
            Arrays.fill(block[i], -1);
        Arrays.fill(letters, 0);
        count = 0;
        for (int i = 0; i < 26; i++)
            Arrays.fill(map[i], 0);
        answer = new StringBuilder();
    }

    private static void solve() {
        //检查每个字母的位置,如果边框上有其他字母，则其它字母一定在该字母之上
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++) {
                int ch = block[i][j];
                if (ch < 0) continue;
                if (letters[ch] == 1) continue;
                letters[ch] = 1;
                count++;
                position(ch);
                first_check(ch);
            }
        //计算字母两两之间的关系
        for (int i = 0; i < 26; i++)
            for (int j = 0; j < 26; j++) {
                if (letters[i] == 1 && letters[j] == 1 && i != j && map[i][j] == 0) {
                    map[i][j] = stack_check(i, j);
                    map[j][i] = -map[i][j];
                }
            }
        //根据两两之间的关系，打印输出结果
        print("");
        System.out.print(answer.toString());
    }

    private static void print(String pre) {
        if (pre.length() == count) {
            answer.append(pre).append('\n');
            return;
        }
        for (int i = 0; i < 26; i++) {
            if (letters[i] == 0 || !isTop(i, pre)) continue;
            letters[i] = 0;
            print(pre + ((char) (i + 'A')));
            letters[i] = 1;
        }
    }

    private static boolean isTop(int ch, String pre) {
        for (int i = 0, len = pre.length(); i < len; i++) {
            if (map[ch][pre.charAt(i) - 'A'] == -1)
                return false;
        }
        return true;
    }

    private static void first_check(int ch) {
        int l = pos[ch][left], r = pos[ch][right], t = pos[ch][top], b = pos[ch][bottom];
        for (int j = l; j <= r; j++) {
            if (block[t][j] != -1 && block[t][j] != ch) {
                map[block[t][j]][ch] = 1;
                map[ch][block[t][j]] = -1;
            }
            if (block[b][j] != -1 && block[b][j] != ch) {
                map[block[b][j]][ch] = 1;
                map[ch][block[b][j]] = -1;
            }
        }
        for (int i = t; i <= b; i++) {
            if (block[i][l] != -1 && block[i][l] != ch) {
                map[block[i][l]][ch] = 1;
                map[ch][block[i][l]] = -1;
            }
            if (block[i][r] != -1 && block[i][r] != ch) {
                map[block[i][r]][ch] = 1;
                map[ch][block[i][r]] = -1;
            }
        }
    }

    private static int stack_check(int a, int b) {
        Set<Integer> set = new HashSet<Integer>();
        Set<Integer> visited = new HashSet<Integer>();
        set.add(a);
        while (set.size() > 0) {
            int next = set.iterator().next();
            if (next == b)
                return 1;
            set.remove(next);
            visited.add(next);
            if (a != next && map[a][next] == 0) {
                map[a][next] = 1;
                map[next][a] = -1;
            }
            for (int i = 0; i < 26; i++) {
                if (map[next][i] == 1 && !visited.contains(i))
                    set.add(i);
            }
        }
        set.clear();
        visited.clear();
        set.add(b);
        while (set.size() > 0) {
            int next = set.iterator().next();
            if (next == a)
                return -1;
            set.remove(next);
            visited.add(next);
            if (b != next && map[b][next] == 0) {
                map[b][next] = 1;
                map[next][b] = -1;
            }
            for (int i = 0; i < 26; i++) {
                if (map[next][i] == 1 && !visited.contains(i))
                    set.add(i);
            }
        }
        return 0;
    }

    private static void position(int ch) {
        pos[ch][left] = w;
        pos[ch][top] = h;
        pos[ch][right] = 0;
        pos[ch][bottom] = 0;
        for (int i = 0; i < h; i++)
            for (int j = 0; j < w; j++) {
                if (block[i][j] != ch) continue;
                if (i < pos[ch][top]) pos[ch][top] = i;
                if (i > pos[ch][bottom]) pos[ch][bottom] = i;
                if (j < pos[ch][left]) pos[ch][left] = j;
                if (j > pos[ch][right]) pos[ch][right] = j;
            }
    }
}
