package com.poj;

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

public class Main1116 {
    // width, height of the niche
    private static int XN;
    private static int YN;
    // width, height of the old tome
    private static int XT;
    private static int YT;
    // the number of shelves
    private static int N;
    private static Shelf[] shelves;
    // 需要移动的peg数
    private static int opt_peg = Integer.MAX_VALUE;
    // 需要减掉的plank数目
    private static int opt_plank = Integer.MAX_VALUE;

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        XN = cin.nextInt();
        YN = cin.nextInt();
        XT = cin.nextInt();
        YT = cin.nextInt();
        N = cin.nextInt();
        shelves = new Shelf[N];
        for (int i = 0; i < N; i++) {
            Shelf s = new Shelf();
            shelves[i] = s;
            s.y = cin.nextInt();
            s.x = cin.nextInt();
            s.len = cin.nextInt();
            // 直接转化为绝对位置
            s.x1 = s.x + cin.nextInt();
            s.x2 = s.x + cin.nextInt();
        }
        // 按照y排序
        Arrays.sort(shelves);
        for (int i = 0; i < N; i++) {
            if (shelves[i].y + YT > YN)
                break;
            if (shelves[i].len >= XT)
                solve(i);
        }
        System.out.printf("%d %d\n", opt_peg, opt_plank);
    }

    // 在木板i上放置书本
    private static void solve(int k) {
        Shelf s = shelves[k];
        for (int j = 0; j + XT <= XN; j++) {
            // j的位置在木板的左边，还够不到左边的钉子
            if (j + s.len < s.x1)
                continue;
            // j的位置已经超过了木板的最右边
            if (j + XT > s.x2 + s.len)
                break;
            minPeg = 0;
            minPlank = 0;
            // 2 * (s.x1 - j) > s.len <--- 如果j和x1的距离保持在长度的1/2以内,原来的钉子都能继续使用
            // 2 * (j + XT - s.x2) > s.len <--- 如果x2和j的距离保持在长度的1/2，原来的钉子都能继续使用
            // s.x2 - j > s.len <--- 如果j和x2的距离在len之内，则原来的可以使用
            // (j + XT) - s.x1 > s.len <--- 如果x1与书本的最右端j+XT的距离在len之内,则原来的钉子可用
            if (2 * (s.x1 - j) > s.len || 2 * (j + XT - s.x2) > s.len)
                minPeg++;
            else if (s.x2 - j > s.len || (j + XT) - s.x1 > s.len)
                minPeg++;
            check(k, j);
            if (minPeg < opt_peg || (minPeg == opt_peg && minPlank < opt_plank)) {
                opt_peg = minPeg;
                opt_plank = minPlank;
            }
        }
    }

    private static int minPeg;
    private static int minPlank;

    // 在木板k的j位置上放置书本
    private static void check(int k, int j) {
        Shelf sk = shelves[k];
        for (int i = k + 1; i < N; i++) {
            //在这块木板上已经找不到最优解
            if (minPeg > opt_peg || (minPeg == opt_peg && minPlank >= opt_plank))
                break;
            // 检查木板i与木板k在j位置开始，到j+XT位置结束这一段上的冲突情况
            Shelf s = shelves[i];
            if (s.y >= sk.y + YT)
                break;
            if (s.x + s.len <= j || s.x >= j + XT)
                continue;
            if (s.x2 <= j) {
                // 两个钉子都在书本的左边， 以x1，x2为原来的钉子，当x1处于木板的最中点时，木板最长的长度max。
                // 当max小于原来的长度时候，需要割掉
                int max = j - s.x1 <= s.x1 ? 2 * (j - s.x1) : j;
                if (max < s.len)
                    minPlank += (s.len - max);
                continue;
            }
            if (s.x1 >= j + XT) {
                // 两个钉子都在书本的右边,以x1，x2为原来的钉子，当x2处于木板的最中点时，木板最长的长度为max
                int max = s.x2 - (j + XT) <= XN - s.x2 ? 2 * (s.x2 - (j + XT)) : XN - (j + XT);
                if (max < s.len)
                    minPlank += (s.len - max);
                continue;
            }
            if (s.x1 <= j && s.x2 > j && s.x2 < j + XT) {
                // 左边的钉子在书本的左边，右边的钉子在书的范围内,将x2的钉子移掉，并将木板左移
                if (j == 0) {
                    minPeg += 2;
                    minPlank += s.len;
                    continue;
                }
                minPeg++;
                if (s.len > j)
                    minPlank += (s.len - j);
                continue;
            }
            if (s.x1 > j && s.x1 < j + XT && s.x2 >= j + XT) {
                // 左边的钉子在书的范围内，右边的钉子在书的右边, 将x1的钉子移掉，并将木板右移
                if (j + XT == XN) {
                    minPeg += 2;
                    minPlank += s.len;
                    continue;
                }
                minPeg++;
                if (s.len > XN - (j + XT))
                    minPlank += s.len - (XN - (j + XT));
                continue;
            }
            if (s.x1 <= j && s.x2 >= j + XT) {
                // 左边的钉子在书本的左边，右边的钉子在书的右边
                minPeg += (j == 0 && XT == XN ? 2 : 1);
                int max = j > XN - (j + XT) ? j : XN - (j + XT);
                if (s.len > max)
                    minPlank += s.len - max;
                continue;
            }
            if (s.x1 > j && s.x2 < j + XT) {
                // 两个钉子都在书本的范围内
                minPeg += 2;
                minPlank += s.len;
            }
        }
    }

    private static class Shelf implements Comparable<Shelf> {
        public int y;
        public int x;
        public int len;
        public int x1;
        public int x2;

        @Override
        public int compareTo(Shelf o) {
            if (y == o.y) {
                return 0;
            }
            return y < o.y ? -1 : 1;
        }
    }

}

