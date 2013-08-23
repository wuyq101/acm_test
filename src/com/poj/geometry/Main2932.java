package com.poj.geometry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * <pre>
 * 原题:http://poj.org/problem?id=2932
 * 题意：有n个圆锥在一个平面上，圆锥相互包含。求独立的没有被包含的圆锥。圆锥不相互接触。
 * 分析：因为圆锥的半径和高相等，问题退化为平面上n个圆，圆之间相互包含，求没有被其他圆包含的圆。圆不相交。
 *       1. 根据每个圆最左边的坐标排序（x-r,y）,x坐标相同的话，根据y从小到大排序。
 *       2. 假设一条扫描线平行于y轴，从左向右开始扫描。
 *       3. 当扫描线遇到是圆的最左边，判断是否是powful圆，判断的时候，找到现在的powful圆，只需要找它的上下两个判断是否包含关系
 *          如果不是包含关系，则认为这个圆是powful的
 *       4. 当扫描线遇到圆的最右边，将这个圆从集合中删除，因为以后在碰到的圆，都和它无关。
 * </pre>
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main2932 {
    private static int N;
    // 在扫描线上的圆,从下到上排序
    private static Integer[] up, left, right, rank;
    private static double[] x, y, r;
    private static double[] d_l, d_r;
    private static boolean[] removed;
    private static List<Integer> answer = new ArrayList<Integer>();
    private static TreeSet<Integer> circle_rank_on_scan = new TreeSet<Integer>();

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        N = cin.nextInt();
        up = new Integer[N];
        left = new Integer[N];
        right = new Integer[N];
        rank = new Integer[N];
        x = new double[N];
        y = new double[N];
        r = new double[N];
        d_l = new double[N];
        d_r = new double[N];
        removed = new boolean[N];
        for (int i = 0; i < N; i++) {
            up[i] = left[i] = right[i] = i;
            r[i] = cin.nextDouble();
            x[i] = cin.nextDouble();
            y[i] = cin.nextDouble();
            d_l[i] = x[i] - r[i];
            d_r[i] = x[i] + r[i];
        }
        // 排序
        sort();
        scan();
        print();
    }

    private static void print() {
        Collections.sort(answer);
        StringBuilder sb = new StringBuilder();
        sb.append(answer.size()).append("\n");
        for (int idx : answer) {
            sb.append(idx + 1).append(" ");
        }
        System.out.println(sb.substring(0, sb.length() - 1));
    }

    private static void sort() {
        Arrays.sort(up, new Comparator_Up());
        for (int i = 0; i < N; i++)
            rank[up[i]] = i;
        Arrays.sort(left, new Comparator_Left());
        Arrays.sort(right, new Comparator_Right());
    }

    private static void scan() {
        int i = 0, j = 0;
        while (i < N || j < N) {
            int L = left[i], R = right[j];
            if (d_l[L] <= d_r[R]) {
                // 将left[i]挂到scan_line上
                insert(L);
                i++;
                if (i == N)
                    return;
            } else {
                // 将right[j]移除掉
                if (!removed[R]) {
                    circle_rank_on_scan.remove(rank[R]);
                }
                j++;
            }
        }
    }

    private static void insert(Integer a) {
        // 找a的up和bottom
        int r = rank[a];
        Integer lower = circle_rank_on_scan.lower(r);
        if (lower != null && circle_contains(a, up[lower])) {
            removed[a] = true;
            return;
        }
        Integer higher = circle_rank_on_scan.higher(r);
        if (higher != null && circle_contains(a, up[higher])) {
            removed[a] = true;
            return;
        }
        circle_rank_on_scan.add(r);
        answer.add(a);
    }

    private static boolean circle_contains(Integer a, Integer b) {
        double dx = x[a] - x[b], dy = y[a] - y[b];
        double dr = r[a] + r[b];
        return dx * dx + dy * dy <= dr * dr;
    }

    private static class Comparator_Up implements Comparator<Integer> {
        @Override
        public int compare(Integer a, Integer b) {
            double dy = y[a] - y[b];
            if (dy == 0) {
                double dx = x[a] - x[b];
                if (dx == 0)
                    return 0;
                return dx < 0 ? -1 : 1;
            }
            return dy < 0 ? -1 : 1;
        }
    }

    private static class Comparator_Left implements Comparator<Integer> {
        @Override
        public int compare(Integer a, Integer b) {
            return d_l[a] == d_l[b] ? 0 : d_l[a] < d_l[b] ? -1 : 1;
        }
    }

    private static class Comparator_Right implements Comparator<Integer> {
        @Override
        public int compare(Integer a, Integer b) {
            return d_r[a] == d_r[b] ? 0 : d_r[a] < d_r[b] ? -1 : 1;
        }
    }
}
