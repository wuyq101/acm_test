package com.poj.sort;

import java.util.Scanner;

/**
 * <pre>
 *      原题：http://poj.org/problem?id=2380
 *      题意：根据输入的记录，排序统计，输出报表结果
 *      分析：看论坛上说部分结果的q值已经有序，使用快排在最坏情况下复杂度n平方会超时，所以使用堆排序来写，堆排序最好最坏的复杂度都是nlgn。另外输出的时候，一次性输出，将结果
 *                先保存起来，避免多次调用io，由于数据量大，所以这部分也很耗时。
 *  </pre>
 * User: wuyq101
 * Date: 13-3-2
 * Time: 下午1:58
 */
public class Main2380 {
    private static int n, MAX = 500000;
    private static int[] q = new int[MAX], s = new int[MAX], v = new int[MAX];
    private static int[] cq = new int[MAX], sorted = new int[MAX];
    private static StringBuilder sb = new StringBuilder();


    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        n = cin.nextInt();
        for (int i = 0; i < n; i++) {
            cq[i] = q[i] = cin.nextInt();
            s[i] = cin.nextInt();
            v[i] = cin.nextInt();
            sorted[i] = i;
        }
        solve();
    }

    private static void solve() {
        //排序，根据q值排序
        heapsort(cq, n);
        print_q();
        heapsort(sorted, n);
        int sid = 0, sum = 0;
        for (int i = 0; i < n; i++) {
            if (s[sorted[i]] != sid) {
                sid = s[sorted[i]];
                sb.append(sid).append(' ');
                i = print_v(i, sid) - 1;
            }
        }
        System.out.printf(sb.toString());
    }

    private static int print_v(int start, int sid) {
        for (int i = 0; i < cnt_q; i++) {
            int qid = cq[i], sum = 0;
            for (int j = start; j < n; j++) {
                if (s[sorted[j]] != sid)
                    break;
                if (qid < q[sorted[j]])
                    break;
                if (q[sorted[j]] == qid) {
                    sum += v[sorted[j]];
                    start = j + 1;
                    continue;
                }
                if (q[sorted[j]] > qid)
                    break;
            }
            sb.append(sum).append(i == cnt_q - 1 ? '\n' : ' ');
        }
        return start;
    }

    private static int cnt_q = 0;

    //打印不同的q值，并且将这些值都移到数组的最前端
    private static void print_q() {
        sb.append("-1 ");
        int q = 0;
        for (int i = 0; i < n; i++) {
            if (cq[i] != q) {
                sb.append(cq[i]).append(' ');
                q = cq[i];
                cq[cnt_q++] = q;
            }
        }
        sb.deleteCharAt(sb.length() - 1).append('\n');
    }

    private static void heapsort(int[] a, int size) {
        //构造堆
        heapify(a, size);
        int end = size - 1;
        while (end > 0) {
            swap(a, 0, end--);
            sift_down(a, 0, end);
        }
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    private static void heapify(int[] a, int size) {
        //0的位置已经是一个堆，从1开始一个一个往前面里边添加元素，并且调整，使得end之前都保持堆结构
        int end = 1;
        while (end < size)
            sift_up(a, 0, end++);
    }

    private static void sift_up(int[] a, int start, int end) {
        int child = end, parent;
        while (child > start) {
            parent = (child - 1) / 2;
            if ((a != sorted && a[child] <= a[parent]) || a == sorted && (isSmaller(sorted[child], sorted[parent])))
                return;
            swap(a, parent, child);
            child = parent;
        }
    }

    private static boolean isSmaller(int i, int j) {
        if (s[i] < s[j])
            return true;
        if (s[i] > s[j])
            return false;
        return q[i] < q[j];
    }

    private static void sift_down(int[] a, int start, int end) {
        int root = start, child, swap;
        while (root * 2 + 1 <= end) {
            child = root * 2 + 1;//left child
            swap = root;
            //check if root is smaller than left child
            if (a != sorted && a[swap] < a[child] || a == sorted && isSmaller(sorted[swap], sorted[child]))
                swap = child;
            //check if right child exists, and if it's bigger than what we're currently swapping with
            if (child + 1 <= end && (a != sorted && a[swap] < a[child + 1] || a == sorted && isSmaller(sorted[swap], sorted[child + 1])))
                swap = child + 1;
            if (swap == root)
                return;
            swap(a, root, swap);
            root = swap;
        }
    }
}
