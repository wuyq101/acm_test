package com.poj;
import java.util.List;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1010 n种面值的邮票，最多只卖4种,输出最优方案
 */
public class Main1010 {
    private static int N;
    private static int M;
    private static int[] type = new int[200];
    private static int[] flag = new int[200];
    private static Main1010.C best = null;
    private static boolean isTie = false;

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        while (cin.hasNext()) {
            // 读取第一行
            int value = cin.nextInt();
            N = 0;
            while (value != 0) {
                type[N++] = value;
                value = cin.nextInt();
            }
            // 读取第二行
            int m = cin.nextInt();
            while (m != 0) {
                // 解决
                Arrays.fill(flag, 0);
                best = null;
                isTie = false;
                Arrays.sort(type, 0, N);
                M = m;
                // 如果超过最大面值的4倍数，无解
                if (m > type[N - 1] * 4) {
                    // 直接输出none
                    System.out.printf("%d ---- none\n", M);
                    m = cin.nextInt();
                    continue;
                }
                // 过滤相同面值的邮票，如果相同面值的邮票种数超过4，保留4种就够了，将其他的相同面值的邮票值设置为M+1，保证不会被使用到
                filter();
                dfs(m, 0);
                // 输出答案
                print();
                m = cin.nextInt();
            }
        }
    }

    //过滤相同面值的邮票，如果相同面值的邮票种数超过4，保留4种就够了，将其他的相同面值的邮票值设置为M+1，保证不会被使用到
    private static void filter() {
        int flag = 0;
        int v = type[0];
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < N; i++) {
            //大于最大面值的，都不考虑
            if(type[i]>M){
                break;
            }
            if (v == type[i]) {
                flag++;
                if (flag > 4) {
                    continue;
                }else{
                    list.add(type[i]);
                }
            } else {
                v = type[i];
                flag = 1;
                list.add(type[i]);
            }
        }
        N = list.size();
        for(int i=0; i<N; i++){
            type[i] = list.get(i);
        }
    }

    private static void dfs(int m, int count) {
        if (count == 4 && m > 0) {
            // 已经达到4张,但是还没有找到答案
            return;
        }
        if (m == 0) {
            // 找到一个答案，比较是否比当前的优秀
            if (best == null) {
                best = new Main1010.C(flag.clone());
                isTie = false;
                return;
            }
            C anw = new Main1010.C(flag.clone());
            if (best.isSame(anw)) {
                return;
            }
            int t = anw.compareTo(best);
            if (t == 1) {
                best = anw;
                isTie = false;
                return;
            }
            if (t == -1) {
                return;
            }
            // t==0
            // 和当前解参数一样，但是又不相同
            isTie = true;
            return;
        }
        for (int i = 0; i < N; i++) {
            if (m >= type[i]) {
                // 使用一张type[i].
                m -= type[i];
                flag[i]++;
                count++;
                dfs(m, count);
                count--;
                flag[i]--;
                m += type[i];
            }
        }

    }

    private static void print() {
        if (best == null) {
            // 没有找到答案
            System.out.printf("%d ---- none\n", M);
            return;
        }
        if (isTie) {
            System.out.printf("%d (%d): tie\n", M, best.count);
        } else {
            System.out.println(best.str);
        }
    }

    // combination
    static class C implements Comparable<C> {
        // 种类
        public int count;
        // 张数
        public int sum;
        // 最大面值
        public int max;
        public int[] flag;
        public String str;

        public C(int[] flag) {
            this.flag = flag;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < N; i++) {
                if (flag[i] > 0) {
                    sum += flag[i];
                    count++;
                    max = type[i] > max ? type[i] : max;
                    for (int k = 0; k < flag[i]; k++) {
                        sb.append(" ").append(type[i]);
                    }
                }
            }
            str = M + " (" + count + "):" + sb.toString();
        }

        // The "best" combination is defined as the maximum number of different stamp types.
        // In case of a tie, the combination with the fewest total stamps is best.
        // If still tied, the set with the highest single-value stamp is best. If there is still a tie, print "tie".
        @Override
        public int compareTo(C o) {
            if (count > o.count) {
                return 1;
            } else if (count < o.count) {
                return -1;
            }
            // count==,比较sum
            if (sum < o.sum) {
                return 1;
            } else if (sum > o.sum) {
                return -1;
            }
            // sum==,比较max
            if (max > o.max) {
                return 1;
            } else if (max < o.max) {
                return -1;
            }
            // max==
            return 0;
        }

        // 判断是否相同
        public boolean isSame(C o) {
            for (int i = 0; i < N; i++) {
                if (this.flag[i] != o.flag[i]) {
                    return false;
                }
            }
            return true;
        }
    }
}