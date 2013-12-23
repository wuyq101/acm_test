package com.leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Administrator on 13-12-22.
 */
public class Intervals {
    private static class Interval {
        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }
    }

    public ArrayList<Interval> merge(ArrayList<Interval> intervals) {
        if (intervals == null || intervals.size() == 0) return intervals;
        ArrayList<Interval> list = new ArrayList<Interval>();
        Collections.sort(intervals, new Comparator<Interval>() {
            @Override
            public int compare(Interval a, Interval b) {
                if (a.start < b.start)
                    return -1;
                if (a.start > b.start)
                    return 1;
                if (a.end < b.end)
                    return -1;
                if (a.end > b.end)
                    return 1;
                return 0;
            }
        });
        list.add(intervals.get(0));
        for (int i = 1; i < intervals.size(); i++) {
            Interval pre = list.get(list.size() - 1);
            Interval cur = intervals.get(i);
            if (isOverlapping(pre, cur)) {
                list.remove(list.size() - 1);
                list.add(merge(pre, cur));
            } else {
                list.add(cur);
            }
        }
        return list;
    }

    private boolean isOverlapping(Interval a, Interval b) {
        if (a.start > b.start) {
            Interval t = a;
            a = b;
            b = t;
        }
        return a.start <= b.start && b.start <= a.end;
    }

    private Interval merge(Interval a, Interval b) {
        if (a.start > b.start) {
            Interval t = a;
            a = b;
            b = t;
        }
        return new Interval(a.start, Math.max(a.end, b.end));
    }

    public ArrayList<Interval> insert(ArrayList<Interval> intervals, Interval newInterval) {
        if (newInterval == null) return intervals;
        if (intervals == null || intervals.size() == 0) {
            intervals = new ArrayList<Interval>();
            intervals.add(newInterval);
            return intervals;
        }
        int left = 0, right = intervals.size() - 1;
        int mid = 0;
        while (left <= right) {
            mid = (left + right) / 2;
            Interval m = intervals.get(mid);
            if (m.start == newInterval.start) break;
            if (m.start < newInterval.start) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        if (intervals.get(mid).start <= newInterval.start)
            mid++;
        intervals.add(mid, newInterval);
        int post = mid + 1;
        while (post < intervals.size()) {
            Interval cur = intervals.get(post);
            if (isOverlapping(newInterval, cur)) {
                intervals.remove(post);
                newInterval = merge(newInterval, cur);
            } else
                break;
        }
        intervals.set(mid, newInterval);
        int prev = mid - 1;
        while (prev >= 0 && intervals.size() > 1) {
            Interval cur = intervals.get(prev);
            if (isOverlapping(newInterval, cur)) {
                newInterval = merge(newInterval, cur);
                intervals.remove(prev);
                mid--;
                prev--;
            } else break;
        }
        intervals.set(mid, newInterval);
        return intervals;
    }

    public static void main(String[] args) {
        Intervals test = new Intervals();
        ArrayList<Interval> list = new ArrayList<Interval>();
        list.add(new Interval(1, 5));
        test.insert(list, new Interval(6, 8));
    }


}
