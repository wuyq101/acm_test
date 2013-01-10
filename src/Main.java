import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

/**
 * <pre>
 * 原题:http://poj.org/problem?id=2932
 * 题意：有n个圆锥在一个平面上，圆锥相互包含。求独立的没有被包含的圆锥。圆锥不相互接触。
 * 分析：因为圆锥的半径和高相等，问题退化为平面上n个圆，圆之间相互包含，求没有被其他圆包含的圆。圆不相交。
 *       1. 根据每个圆最左边的坐标排序（x-r,y）,x坐标相同的话，根据y从小到大排序。
 *       2. 假设一条扫描线平行于y轴，从左向右开始扫描。
 * 
 * </pre>
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main {
    private static int N;
    // 在扫描线上的圆,从下到上排序
    private static Integer[] up, left, right, rank;
    private static boolean[] used;
    private static double[] x, y, r;
    private static List<Integer> answer = new ArrayList<Integer>();

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        N = cin.nextInt();
        up = new Integer[N];
        left = new Integer[N];
        right = new Integer[N];
        rank = new Integer[N];
        used = new boolean[N];
        x = new double[N];
        y = new double[N];
        r = new double[N];
        for (int i = 0; i < N; i++) {
            up[i] = left[i] = right[i] = i;
            r[i] = cin.nextDouble();
            x[i] = cin.nextDouble();
            y[i] = cin.nextDouble();
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
            if (i < N && x[left[i]] - r[left[i]] <= x[right[j]] + r[right[i]]) {
                // 将left[i]挂到scan_line上
                insert(left[i]);
                i++;
            } else {
                // 将right[j]移除掉
                used[right[j]] = false;
                j++;
            }
        }
    }

    private static void insert(Integer a) {
        // 找a的up和bottom
        int r = rank[a];
        for (int i = r + 1; i < N; i++) {
            if (used[up[i]]) {
                // 判断圆a和圆up[i]
                if (circle_contains(a, up[i])) {
                    return;
                } else {
                    break;
                }
            }
        }
        for (int i = r - 1; i >= 0; i--) {
            if (used[up[i]]) {
                // 判断圆a和圆up[i]
                if (circle_contains(a, up[i])) {
                    return;
                } else {
                    break;
                }
            }
        }
        used[a] = true;
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
            double dx = x[a] - r[a] - (x[b] - r[b]);
            if (dx == 0)
                return 0;
            return dx < 0 ? -1 : 1;
        }
    }

    private static class Comparator_Right implements Comparator<Integer> {
        @Override
        public int compare(Integer a, Integer b) {
            double dx = x[a] + r[a] - (x[b] + r[b]);
            if (dx == 0)
                return 0;
            return dx < 0 ? -1 : 1;
        }
    }
}
