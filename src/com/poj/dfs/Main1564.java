package com.poj.dfs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * <pre>
 *     http://poj.org/problem?id=1564
 *     题意：给出t和n个数，找出n个数中加起来和为t一共有几种方法，并按从大到小的顺序列举出来。
 *     分析：深度优先搜索，搜索的时候注意记录到达过的每个状态，重复的状态就不要搜了，另外保存已经搜到的答案，重复的答案就不输出。
 *     因为给的12个数字中会有重复的数字可以多次使用，所以先使用桶排序，统计每个数字的个数。然后在深搜，比起直接搜索的2^12次要
 *     少一些状态。
 *     状态的描述：一共就12个数字，可以使用13进制，我这里直接就使用字符串来做了，例如400 12 50 50 50 50 50 50 25 25 25 25 25 25
 *     这样的数据，506256，代表还有6个50,6个25，然后使用了一个50，就变成505256。
 *     注意，题目中说的n个数在1--100之间，实际测试数据远远大于100.数据开到了1000才去掉Runtime Error。
 * </pre>
 * User: wuyq101
 * Date: 13-5-30
 * Time: 上午10:37
 */
public class Main1564 {
    private static int t, n, cnt;
    private static int MAX = 1000;
    private static int[] value = new int[MAX + 1];
    private static int[] answer = new int[MAX + 1];
    private static int[] list = new int[12];
    private static Set<String> set = new HashSet<String>();
    private static Set<String> visited = new HashSet<String>();
    private static boolean find = false;

    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            init();
            StringTokenizer st = new StringTokenizer(cin.readLine());
            t = Integer.parseInt(st.nextToken());
            n = Integer.parseInt(st.nextToken());
            if (n == 0) break;
            for (int i = 0; i < n; i++) {
                value[Integer.parseInt(st.nextToken())]++;
            }
            cnt = 0;
            for (int i = MAX; i >= 0; i--) {
                if (value[i] > 0)
                    list[cnt++] = i;
            }
            System.out.printf("Sums of %d:\n", t);
            dfs(t);
            if (!find) {
                System.out.println("NONE");
            }
        }
    }

    private static void init() {
        Arrays.fill(value, 0);
        Arrays.fill(answer, 0);
        Arrays.fill(list, 0);
        set.clear();
        visited.clear();
        find = false;
    }

    private static void dfs(int sum) {
        if (sum == 0) {
            //find a solution
            find = true;
            //print the answer
            print_answer();
            return;
        }
        for (int i = 0; i < cnt; i++) {
            int v = list[i];
            if (value[v] > 0 && sum - v >= 0) {
                value[v]--;
                answer[v]++;
                //判断当前状态是否已经搜索过了
                String key = getKey();
                if (visited.add(key))
                    dfs(sum - v);
                value[v]++;
                answer[v]--;
            }
        }
    }

    private static void print_answer() {
        StringBuilder sb = new StringBuilder();
        for (int i = MAX; i >= 0; i--) {
            if (answer[i] > 0) {
                for (int j = 0; j < answer[i]; j++)
                    sb.append('+').append(i);
            }
        }
        String new_answer = sb.deleteCharAt(0).toString();
        if (set.contains(new_answer))
            return;
        System.out.println(new_answer);
        set.add(new_answer);
    }

    public static String getKey() {
        String key = "";
        for (int i = 0; i < cnt; i++) {
            key += list[i] + "" + value[list[i]];
        }
        return key;
    }
}


