package com.poj.heap;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * http://poj.org/problem?id=3190
 * Created by wuyq on 16/5/31.
 */
public class Main3190 {
    private static int N;
    private static int count;
    private static Gap[] gaps;
    private static Stall[] stalls = new Stall[50000]; //小顶堆
    private static int[] d;

    private static class Gap implements Comparable<Gap> {
        int idx;
        int start;
        int end;

        public Gap(int idx, int start, int end) {
            this.idx = idx;
            this.start = start;
            this.end = end;
        }

        /**
         * 根据开始时间排序，开始时间小的排前面
         * 如果开始时间相等，按照结束时间排序，结束早的排前面
         * 如果两者都相等，根据编号排序
         *
         * @param that
         * @return
         */
        @Override
        public int compareTo(Gap that) {
            if (start != that.start)
                return start < that.start ? -1 : 1;
            if (end != that.end)
                return end < that.end ? -1 : 1;
            if (start == that.start && end == that.end) {
                if (idx == that.idx) return 0;
                return idx < that.idx ? -1 : 1;
            }
            return 0;
        }
    }

    public static class Stall {
        int end;
        int idx;
    }

    public static void main(String[] args) throws Exception {
        input();
        solve();
        output();
    }

    private static void output() {
        StringBuilder sb = new StringBuilder();
        sb.append(count).append('\n');
        for (int i = 0; i < N; i++) {
            sb.append(d[i]).append('\n');
        }
        System.out.print(sb.toString());
    }

    private static void solve() {
        d = new int[N];
        Arrays.sort(gaps);
        count = 0;
        for (int i = 0; i < N; i++) {
            Gap g = gaps[i];
            Stall s = stalls[0];
            if (s == null) {
                count++;
                s = new Stall();
                s.idx = count;
                s.end = g.end;
                d[g.idx] = s.idx;
                stalls[0] = s;
                continue;
            }
            //比较当前最早结束时间和g的开始时间
            if (s.end < g.start) {
                //可以占用
                s.end = g.end;
                d[g.idx] = s.idx;
                //更新s在堆中的位置
                down(0);
                continue;
            }
            //没有合适的已经存在的stall可以使用,给g重新分配一个stall
            count++;
            s = new Stall();
            s.idx = count;
            s.end = g.end;
            d[g.idx] = s.idx;
            //将s放入到堆中
            stalls[count - 1] = s;
            up(count - 1);
        }
    }

    private static void up(int idx) {
        if (idx == 0) return;
        int p = (idx - 1) / 2;
        if (stalls[p].end > stalls[idx].end) {
            swap(p, idx);
            up(p);
        }
    }

    private static void swap(int i, int j) {
        Stall temp = stalls[i];
        stalls[i] = stalls[j];
        stalls[j] = temp;
    }

    private static void down(int idx) {
        int child = idx * 2 + 1;
        if (child < count) {
            if (child + 1 < count && stalls[child + 1].end < stalls[child].end) {
                child++;
            }
            if (stalls[child].end < stalls[idx].end) {
                swap(child, idx);
                down(child);
            }
        }
    }

    private static void input() throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(cin.readLine());
        gaps = new Gap[N];
        for (int i = 0; i < N; i++) {
            String[] s = cin.readLine().split(" ");
            gaps[i] = new Gap(i, Integer.parseInt(s[0]), Integer.parseInt(s[1]));
        }
    }
}
