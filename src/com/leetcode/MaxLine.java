package com.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 13-12-9.
 */
public class MaxLine {
    private static class Point {
        int x, y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public static double epsilon = 1e-6;

    private static class Line {
        double slope;
        double intercept;
        boolean infinite_slope;

        public Line(Point p, Point q) {
            if (p.x == q.x) {
                infinite_slope = true;
                intercept = p.x;
            } else {
                slope = (p.y - q.y) * 1.0 / (p.x - q.x);
                intercept = p.y - slope * p.x;
            }
        }

        public double key() {
            int r = (int) (slope / epsilon);
            return r * epsilon;
        }

        public boolean equals(Object o) {
            Line l = (Line) o;
            if (l.infinite_slope == infinite_slope && equals(l.slope, slope) && equals(l.intercept, intercept))
                return true;
            return false;
        }

        private static boolean equals(double a, double b) {
            return Math.abs(a - b) < epsilon;
        }

        public boolean online(Point p) {
            if (infinite_slope && equals(p.x, intercept))
                return true;
            return equals(p.y, p.x * slope + intercept);
        }
    }


    public int maxPoints(Point[] points) {
        if (points == null) return 0;
        if (points.length <= 2) return points.length;
        int best = 0;
        Line best_line = null;
        Map<Double, List<Line>> map = new HashMap<Double, List<Line>>();
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                Line line = new Line(points[i], points[j]);
                double key = line.key();
                List<Line> list = map.get(key);
                if (list == null) {
                    list = new ArrayList<Line>();
                    map.put(key, list);
                }
                list.add(line);
                int count = count(map, line);
                if (count > best) {
                    best = count;
                    best_line = line;
                }
            }
        }
        return count_point(best_line, points);
    }

    private int count_point(Line best_line, Point[] points) {
        int cnt = 0;
        for (Point p : points) {
            if (best_line.online(p))
                cnt++;
        }
        return cnt;
    }

    private int count(Map<Double, List<Line>> map, Line line) {
        double key = line.key();
        List<Line> list = map.get(key);
        int count = count(list, line);
        list = map.get(key + epsilon);
        count += count(list, line);
        list = map.get(key - epsilon);
        count += count(list, line);
        return count;
    }

    private int count(List<Line> list, Line line) {
        if (list == null) return 0;
        int count = 0;
        for (Line l : list)
            if (l.equals(line)) count++;
        return count;
    }

    public static void main(String[] args) {
        //(0,0),(-1,-1),(2,2)
        Point[] points = {new Point(0, 0), new Point(1, 1), new Point(2, 2), new Point(3, 3)};
        MaxLine test = new MaxLine();
        int t = test.maxPoints(points);
        System.out.println(t);
    }
}
