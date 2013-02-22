package com.poj.greedy;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/**
 * <pre>
 *     原题：http://poj.org/problem?id=1727
 *     题意：一个事件e有时间和位置决定，记为(t,x)。e1 is a possible cause for e2 iff t2 >= t1+|x2-x1|，所以一个事件的cause在图形上就是
 *               它下方的三角形范围，斜率为1和-1这两条直线决定。
 *               给定n个事件，以及因事件的个数m。求因事件最晚的时间。
 *               根据题目的数值范围，在时间-2000000的时候，一个因事件能触发所有的事件。
 *               因事件最晚的时间不大于这些事件中的最早发生的那个时间。
 *               确定了范围之后，开始二分查找最晚的时间。假设在一个时间t0上，至少需要num个因事件才能触发，比较这个num和m，
 *               如果num<=m，那么t0是更优的解。如果num>m,那么t0这个时间不是解，将范围缩小为mid-1。
 *               在某个时间t0上，求至少需要的因事件个数，使用贪心。将每个原事件，在这个时间上，因事件最左边和最右边的范围确定。然后根据
 *               右边的范围排序。选第一个右边的位置right。下一个范围如何包含right了，就跳过，如果没有包括，个数加1，right更新为当前范围的right。
 *               这样就求出了再时间t0上，至少需要的因事件个数。
 * </pre>
 * User: wuyq101
 * Date: 13-2-22
 * Time: 下午1:29
 */
public class Main1727 {
    private static class Event {
        int t, x, left, right;
    }

    private static void cause_range(Event e, int t0) {
        e.left = e.x - e.t + t0;
        e.right = e.x + e.t - t0;
    }

    private static int n, m;
    private static Event[] events = new Event[100000];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        int t = cin.nextInt();
        while (t-- > 0) {
            input(cin);
            solve();
        }
    }

    private static int case_num = 1;

    private static void solve() {
        //先找最小值
        int max = events[0].t;
        for (int i = 1; i < n; i++)
            if (events[i].t < max)
                max = events[i].t;
        int min = -2000000;
        //二分查找
        while (min < max) {
            int mid = (min + max) / 2;
            if (max - min == 1) {
                if (greed(max) <= m) {
                    min = max;
                }
                break;
            }
            int num = greed(mid);
            if (num <= m) {
                min = mid;
            } else {
                max = mid - 1;
            }
        }
        System.out.printf("Case %d: %d\n", case_num++, min);
    }

    /**
     * 当时间是t的时候，最少需要的cause个数，每个event与t产生一个cause_range,排序，贪心求最少的range个数
     * 如果个数大于m，那么这个时间t不满足要求，要继续减少
     *
     * @param t
     * @return
     */
    private static int greed(int t) {
        for (int i = 0; i < n; i++) {
            cause_range(events[i], t);
        }
        Arrays.sort(events, 0, n, new Comparator<Event>() {
            @Override
            public int compare(Event a, Event b) {
                if (a.right < b.right)
                    return -1;
                if (a.right > b.right)
                    return 1;
                return 0;
            }
        });
        //从左向右统计个数
        int num = 1;
        int right = events[0].right;
        for (int i = 1; i < n; i++) {
            if (right >= events[i].left && right <= events[i].right)
                continue;
            if (right < events[i].left) {
                right = events[i].right;
                num++;
            }
        }
        return num;
    }

    private static void input(Scanner cin) {
        n = cin.nextInt();
        m = cin.nextInt();
        for (int i = 0; i < n; i++) {
            if (events[i] == null)
                events[i] = new Event();
            events[i].t = cin.nextInt();
            events[i].x = cin.nextInt();
        }
    }
}
