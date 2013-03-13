package com.poj.binary_indexed_tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * <pre>
 *     http://poj.org/problem?id=1990
 *     树状数组的应用
 * </pre>
 * User: wuyq101
 * Date: 13-3-6
 * Time: 下午4:13
 */
public class Main1990 {
    private static class Cow {
        int x, v;
    }

    private static class BinaryIndexedTree {
        private static int max = 20000;
        private long[] tree = new long[20001];

        //在i出增加d
        private void update(int i, int d) {
            while (i <= max) {
                tree[i] += d;
                i += (i & -i);
            }
        }

        //读取从1-i的数组和
        private long read(int i) {
            long sum = 0;
            while (i > 0) {
                sum += tree[i];
                i -= (i & -i);
            }
            return sum;
        }
    }

    private static int n;
    private static Cow[] cows = new Cow[20001];
    private static BinaryIndexedTree total = new BinaryIndexedTree();
    private static BinaryIndexedTree count = new BinaryIndexedTree();

    public static void main(String[] args) throws IOException {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(cin.readLine());
        n = Integer.parseInt(st.nextToken());
        for (int i = 1; i <= n; i++) {
            st = new StringTokenizer(cin.readLine());
            cows[i] = new Cow();
            cows[i].v = Integer.parseInt(st.nextToken());
            cows[i].x = Integer.parseInt(st.nextToken());
        }
        solve();
    }

    private static void solve() {
        Arrays.sort(cows, 1, n + 1, new Comparator<Cow>() {
            @Override
            public int compare(Cow a, Cow b) {
                if (a.v != b.v)
                    return a.v < b.v ? -1 : 1;
                return 0;
            }
        });
        long sum = 0, allTotal = 0;
        for (int i = 1; i <= n; i++) {
            int x = cows[i].x;
            //count_x: 在x之前，且v都小于v[i]的牛的头数
            //total_x:在x之前，且v都小于v[i]的x坐标总和
            //count_x * x - total_x为在x[i]之前，且v都小于v[i]的总距离
            //allTotal - total_x - (i - count_x - 1) * x： 在x[i]之后且v小于v[i]的那些牛和x之间的总距离
            long count_x = count.read(x), total_x = total.read(x);
            sum += cows[i].v * (count_x * x - total_x + allTotal - total_x - (i - count_x - 1) * x);
            count.update(x, 1);
            total.update(x, x);
            allTotal += x;
        }
        System.out.println(sum);
    }
}
