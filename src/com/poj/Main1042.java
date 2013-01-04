package com.poj;
import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1042 贪心
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main1042 {
    private static int n;
    private static int h;
    private static int[] f = new int[26];
    private static int[] d = new int[26];
    private static int[] t = new int[25];
    // 记录中间答案, ans[i][0],代表从i鱼塘开始掉，最多能掉到多少鱼
    // ans[i][1---n],分别代表ans[i][0]方案时，在每个鱼塘呆的时间
    private static int[][] ans = new int[26][26];

    private static Lake[] heap = new Lake[26];
    private static int heap_size;

    public static void main(String[] args) {
        for (int i = 1; i <= 25; i++) {
            heap[i] = new Lake();
        }
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        while (true) {
            n = cin.nextInt();
            if (n == 0)
                break;
            h = cin.nextInt();
            // 将小时转化为5分钟的间隔次数
            h = h * 12;
            for (int i = 1; i <= n; i++) {
                f[i] = cin.nextInt();
                Arrays.fill(ans[i], 0);
            }
            for (int i = 1; i <= n; i++) {
                d[i] = cin.nextInt();
            }
            for (int i = 1; i <= n - 1; i++) {
                t[i] = cin.nextInt();
            }
            solve();
            print();
        }
    }

    private static void solve() {
        for (int i = 1; i <= n; i++) {
            // 前往i个湖中每次找鱼最多的湖下掉
            heap_size = i;
            for (int j = 1; j <= i; j++) {
                Lake l = heap[j];
                l.f = f[j];
                l.d = d[j];
                l.i = j;
            }
            build_max_heap();
            int flag = 0;
            h = h - t[i - 1];
            int time = h;
            while (time > 0 && flag != i) {
                // 找到最大的k
                ans[i][0] += heap[1].f;
                ans[i][heap[1].i]++;
                time--;
                if (heap[1].f > heap[1].d)
                    heap[1].f = heap[1].f - heap[1].d;
                else {
                    heap[1].f = 0;
                    flag++;
                }
                max_heapify(1);
            }
            ans[i][1] += time;
        }
    }

    private static void build_max_heap() {
        for (int i = heap_size / 2; i >= 1; i--)
            max_heapify(i);
    }

    private static void max_heapify(int i) {
        int left = i << 1;
        int right = left + 1;
        int largest = i;
        if (left <= heap_size && heap[left].compareTo(heap[largest]) == 1)
            largest = left;
        if (right <= heap_size && heap[right].compareTo(heap[largest]) == 1)
            largest = right;
        if (largest != i) {
            // swap i and largest
            Lake temp = heap[i];
            heap[i] = heap[largest];
            heap[largest] = temp;
            max_heapify(largest);
        }
    }

    private static void print() {
        int k = 1;
        for (int i = 2; i <= n; i++) {
            if (ans[i][0] > ans[k][0])
                k = i;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= n; i++) {
            //System.out.printf("%d", ans[k][i] * 5);
            sb.append(ans[k][i] * 5);
            if (i != n) {
                //System.out.printf(", ");
                sb.append(", ");
            }
        }
        sb.append("\nNumber of fish expected: ");
        sb.append(ans[k][0]);
        sb.append("\n\n");
        //System.out.printf("\nNumber of fish expected: %d\n\n", ans[k][0]);
        System.out.printf(sb.toString());
    }
}

class Lake implements Comparable<Lake> {
    public int f;
    public int i;
    public int d;

    @Override
    public int compareTo(Lake other) {
        if (f == other.f) {
            return i < other.i ? 1 : -1;
        }
        return f > other.f ? 1 : -1;
    }
}
