package com.poj.enumeration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * <pre>
 *     http://poj.org/problem?id=1054
 *     题意：青蛙过稻田，问走哪条路破坏的庄稼最多。每次跳相同的距离，一条线上至少要三个点，才能穿越稻田。
 *     分析：先将点排序，然后逐个枚举，假设点i作为起点，点j作为相邻的第二个点。这样枚举就是O（N^2），然后计算直线ij上其他点的个数
 *     也差不多是N，整体上来说复杂度应该算接近O（N^3）。进行如下剪枝，可减少枚举的次数，使复杂度接近N^2。
 *     1. 直线ij延伸出去的前一个点，如果在稻田里的话，那么这条直线要么已经被计算过了，要么青蛙无法跳入，剪枝。
 *     2. 在当前已知最优解best的基础上，如果i，j这条直线更优的话,那么这条直线上至少有best+1个点，则最后一个点（中间隔了best个间距）
 *         的坐标应该在稻田内，如果不在稻田内的话，剪枝，一定不是最优解
 *     3. 如果剩余枚举的总个数少于当前最优解的话，停止枚举，要保证 N-i>best
 * </pre>
 * User: wuyq101
 * Date: 13-6-12
 * Time: 下午10:04
 */
public class Main1054 {
    private static int R, C, N, MAX = 5000;
    private static boolean[][] map = new boolean[MAX + 1][MAX + 1];
    private static int[] nodes = new int[MAX];

    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(cin.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(cin.readLine());
        N = Integer.parseInt(st.nextToken());
        int r, c;
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(cin.readLine());
            r = Integer.parseInt(st.nextToken());
            c = Integer.parseInt(st.nextToken());
            nodes[i] = (r - 1) * C + c;
            map[r][c] = true;
        }
        solve();
    }

    private static void solve() {
        //想将各个点根据坐标排序
        Arrays.sort(nodes, 0, N);
        int c, c1, r1, c2, r2, dc, dr, prec, lastc, ans, best = 0;
        for (int i = 0; i < N && N - i > best; i++) {
            c = nodes[i] % C;
            c1 = c == 0 ? C : c;
            r1 = c == 0 ? nodes[i] / C : nodes[i] / C + 1;
            for (int j = i + 1; j < N; j++) {
                //计算i点作为起点，j点作为第二个点，是否可以构成一个可行解，并计算出这个解的大小
                c = nodes[j] % C;
                c2 = c == 0 ? C : c;
                r2 = c == 0 ? nodes[j] / C : nodes[j] / C + 1;
                dc = c2 - c1;
                dr = r2 - r1; //肯定》=0
                //i,j延伸出去的前一个点应该在稻田外,如果在稻田内，那这条直线已经被搜索过，或者青蛙跳不进来，剪枝
                prec = c1 - dc;
                if (prec >= 1 && prec <= C && r1 > dr) continue;
                //在当前已知最优解best的基础上，如果i，j这条直线更优的话,那么这条直线上至少有best+1个点，则最后一个点（中间隔了best个间距）的坐标应该在稻田内，
                //如果不在稻田内的话，剪枝，一定不是最优解
                if (r1 + best * dr > R) continue;
                lastc = c1 + best * dc;
                if (lastc < 1 || lastc > C) continue;
                //直线i，j可能是一个解，开始计算这条直线上，是否能构成以dr，dc为间距的可行解
                ans = calc(r2, c2, dr, dc);
                if (best < ans) best = ans;
            }
        }
        System.out.println(best);
    }

    private static int calc(int r1, int c1, int dr, int dc) {
        int cnt = 2;
        r1 += dr;
        c1 += dc;
        while (r1 <= R && c1 >= 1 && c1 <= C) {
            if (map[r1][c1]) {
                cnt++;
            } else
                return 0;
            r1 += dr;
            c1 += dc;
        }
        return cnt >= 3 ? cnt : 0;
    }
}
