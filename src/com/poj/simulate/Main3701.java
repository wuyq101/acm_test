package com.poj.simulate;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

/**
 * 3701 解魔方，只要求其中一面即可
 * 
 * <pre>
 * 1 front, 2 left, 3 top, 4 right, 5 bottom, 6 back
 * 
 *   6
 * 2 3 4 5
 *   1
 * </pre>
 * 
 * @author wyq
 * @version 1.0
 */
public class Main3701 {
    private static int[][][] cube = new int[7][3][3];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        // back 6
        for (int i = 0; i < 3; i++) {
            String line = cin.next();
            for (int j = 0; j < 3; j++)
                cube[6][i][j] = line.charAt(j) - '0';
        }
        // left top right bottom 2 3 4 5
        for (int i = 0; i < 3; i++) {
            String line = cin.next();
            for (int j = 0; j < 12; j++) {
                cube[j / 3 + 2][i][j % 3] = line.charAt(j) - '0';
            }
        }
        // front
        for (int i = 0; i < 3; i++) {
            String line = cin.next();
            for (int j = 0; j < 3; j++)
                cube[1][i][j] = line.charAt(j) - '0';
        }
        sovle();
    }

    private static PriorityQueue<State> pq = new PriorityQueue<State>();
    private static Set<Long> set = new HashSet<Long>();

    private static void sovle() {
        if (!check()) {
            System.out.println("no");
            return;
        }
        State init = new State(cube);
        set.add(init.cb);
        pq.add(init);
        while (pq.size() > 0) {
            State state = pq.poll();
            state = move(state);
            if (verify(state)) {
                System.out.println("yes");
                printState(state);
                return;
            }
            for (int k = 1; k <= 18; k++) {
                toCube(state.cb);
                State next = move(state, k);
                if (set.add(next.cb)) {
                    pq.add(next);
                }
            }
        }
        System.out.println("no");
    }

    private static int[] center_order = { 0, 0, 4, 10, 3, 9, 3 };

    //棱位转到front面cube[1][0][1]上的基本步骤
    private static int[][] side_moves = { { 17, 7, 16, 8 },// 2,1
            { 15, 2, 15, 1 },// 2,3
            { 1, 1, 13, 16, 17, 16, 13, 17, 16, 6, 15, 6 },// 2,5
            {},// 2,7
            { 1, 18, 18, 16, 13, 13, 5 },// 3,1
            { 15, 15, 2, 15, 1 },// 3,3
            { 15, 1, 1, 13, 16, 17, 16, 13, 17, 16, 6, 15, 6 },// 3,5
            { 2, 14, 18, 18, 13, 5, 13, 13, 5, 18, 15, 8, 18, 13, 2, 15, 2 },// 3,7
            { 18, 3, 17, 10, 18, 4 },// 4,1
            { 18, 16, 11, 17, 2, 13, 1, 13, 13, 18, 12, 18, 11, 13, 15, 17, 11, 17, 2 },// 4,3
            { 15, 15, 1, 1, 13, 16, 17, 16, 13, 17, 16, 6, 15, 6 },// 4,5
            {},// 4,7
            { 14, 17, 7, 16, 8 },// 5,1
            { 2, 15, 1 },// 5,3
            { 16, 1, 1, 13, 16, 17, 16, 13, 17, 16, 6, 15, 6 },// 5,5
            {},// 5,7
            { 2, 13, 18, 12, 14, 18, 5, 16, 18, 13, 13, 16, 8, 18, 1, 14, 1, 17, 7, 15, 17, 6, 15, 6 },// 6,1
            { 2, 18, 12, 14, 12, 13, 13, 12, 18, 6, 15, 15, 6, 16, 16, 6, },// 6,3
            { 1, 1, 18, 11, 14, 17, 16, 1, 18, 18, 6 },// 6,5
            { 1, 18, 18, 6, 18, 7, 13, 13, 8, 18, 1 },// 6,7
    };

    //角位转到front面cube[1][0][0]上的基本步骤
    private static int[][] corner_moves = { { 2, 13, 16, 2, 11, 1, 1, 18, 11, 11, 18, 18, 8 },// 2,0
            { 1, 18, 11, 15, 18, 6, 13, 6, 18, 8, 15, 15, 8 },// 2,2
            {},// 2,6
            { 8, 14, 15, 14, 15, 8, 17, 12, 5, 5, 18, 7 },// 2,8
            { 2, 18, 15, 18, 5, 13, 13, 18, 7, 7, 17, 7, 18, 8 },// 3,0
            { 2, 13, 18, 18, 13, 5, 13, 16, 5, 17, 12, 18, 5 },// 3,2
            { 2, 13, 13, 18, 11, 18, 5, 18, 13, 7, 18, 1, 14, 15, 15, 2, 13, 2, 18, 13, 18, 18, 7 },// 3,6
            {},// 3,8
            { 2, 18, 13, 11, 11, 13, 18, 15, 15, 5, 13, 6, 15, 17, 12, 14, 18, 15, 5 },// 4,0
            { 2, 18, 11, 14, 11, 18, 6, 14, 7, 18, 8 },// 4,2
            {},// 4,6
            {},// 4,8
            { 13, 18, 1, 13, 16, 17, 7, 14, 17, 14, 17, 12, 17, 1, 15, 1, 15, 15, 2, 18, 11, 14, 12, 17, 1 },// 5,0
            { 1, 18, 16, 18, 13, 16, 5 },// 5,2
            {},// 5,6
            {},// 5,8
            { 2, 18, 12, 13, 11, 17, 1 },// 6,0
            { 14, 2, 18, 12, 13, 11, 17, 1 },// 6,2
            { 13, 2, 18, 12, 13, 11, 17, 1 },// 6,6
            { 13, 13, 2, 18, 12, 13, 11, 17, 1 },// 6,8
    };

    //每次只实现一个目标
    //1：如果front面中心不是1，将其他面的中心1转过来
    //2：如果棱位上还有0，先将这个0转到cube[1][0][1]的位置，然后在其他面的棱中找1，找到1之后，运用相应的基本公式，将1转过来。
    //3: 如果角位上还有0，先将这个0转到cube[1][0][0]的位置,然后在其他面的角中找1，找到1之后，运用相应的基本公式，将1转过来。
    private static State move(State s) {
        toCube(s.cb);
        // 先转中心
        if (cube[1][1][1] == 0) {
            for (int side = 2; side <= 6; side++) {
                if (cube[side][1][1] == 1) {
                    s = move(s, center_order[side]);
                    if (side == 6) {
                        s = move(s, 3);
                    }
                    return s;
                }
            }
        }
        // 转棱位
        int d = cube[1][0][1] + cube[1][1][0] + cube[1][1][2] + cube[1][2][1];
        if (d < 4) {
            if (cube[1][0][1] == 1 && cube[1][1][0] == 0) {
                s = move(s, 18);
            } else {
                while (cube[1][0][1] == 1) {
                    s = move(s, 17);
                }
            }
            for (int k = 2; k <= 6; k++) {
                for (int t = 1; t <= 7; t += 2) {
                    int i = t / 3;
                    int j = t % 3;
                    if (cube[k][i][j] == 1) {
                        int idx = (k - 2) * 4 + t / 2;
                        for (int m = 0; m < side_moves[idx].length; m++) {
                            s = move(s, side_moves[idx][m]);
                        }
                        return s;
                    }
                }
            }
        }

        // 转角位
        d = cube[1][0][0] + cube[1][0][2] + cube[1][2][0] + cube[1][2][2];
        if (d < 4) {
            if (cube[1][0][0] == 1 && cube[1][2][0] == 0) {
                s = move(s, 18);
            } else {
                while (cube[1][0][0] == 1) {
                    s = move(s, 17);
                }
            }
            for (int k = 2; k <= 6; k++) {
                for (int t = 0; t < 4; t++) {
                    int i = corner_idx[t] / 3;
                    int j = corner_idx[t] % 3;
                    if (cube[k][i][j] == 1) {
                        int idx = (k - 2) * 4 + t;
                        for (int m = 0; m < corner_moves[idx].length; m++) {
                            s = move(s, corner_moves[idx][m]);
                        }
                        return s;
                    }
                }
            }
        }
        return s;
    }

    //每个面上角位的坐标
    private static int[] corner_idx = { 0, 2, 6, 8 };
    //圈圈临时存放
    private static int[] circle = new int[12];
    //面临时存放
    private static int[][] face = new int[3][3];

    private static State move(State state, int k) {
        // 先旋转四周的棱
        // 先拷贝到circle中
        for (int t = 0; t < 12; t++) {
            circle[t] = cube[order[k][t / 3]][col[k][t] / 3][col[k][t] % 3];
        }
        // 从circle中拷贝回cube
        for (int t = 0; t < 12; t++) {
            int idx = t - 3 < 0 ? t + 9 : t - 3;
            cube[order[k][t / 3]][col[k][t] / 3][col[k][t] % 3] = circle[idx];
        }
        // 再选择面
        if (face_order[k] > 0) {
            int s = face_order[k];
            // 将该面的数据先拷贝出来
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    face[i][j] = cube[s][i][j];
            // 将face中的数据拷贝回cube
            if (k == 1 || k == 6 || k == 8 || k == 11 || k == 13 || k == 18) {
                // 顺时针
                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < 3; j++)
                        cube[s][i][j] = face[2 - j][i];
            } else {
                // 逆时针
                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < 3; j++)
                        cube[s][i][j] = face[j][2 - i];
            }
        }
        State next = new State(cube);
        next.cnt = state.cnt + 1;
        next.move = state.move.multiply(n_19).add(BigInteger.valueOf(k));
        return next;
    }

    private static BigInteger n_19 = BigInteger.valueOf(19);

    private static int[][] order = { {},// 0
            { 1, 2, 6, 4 },// 1
            { 1, 4, 6, 2 },// 2
            { 1, 2, 6, 4 },// 3
            { 1, 4, 6, 2 },// 4
            { 1, 2, 6, 4 },// 5
            { 1, 4, 6, 2 },// 6
            { 1, 3, 6, 5 },// 7
            { 1, 5, 6, 3 },// 8
            { 1, 3, 6, 5 },// 9
            { 1, 5, 6, 3 },// 10
            { 1, 3, 6, 5 },// 11
            { 1, 5, 6, 3 },// 12
            { 3, 2, 5, 4 },// 13
            { 3, 4, 5, 2 },// 14
            { 3, 2, 5, 4 },// 15
            { 3, 4, 5, 2 },// 16
            { 3, 2, 5, 4 },// 17
            { 3, 4, 5, 2 },// 18
    };

    private static int[] face_order = { 0, 3, 3, 0, 0, 5, 5, 2, 2, 0, 0, 4, 4, 6, 6, 0, 0, 1, 1 };

    private static int[][] col = { {},// 0
            { 2, 1, 0, 8, 5, 2, 6, 7, 8, 0, 3, 6 },// 1
            { 0, 1, 2, 6, 3, 0, 8, 7, 6, 2, 5, 8 },// 2
            { 5, 4, 3, 7, 4, 1, 3, 4, 5, 1, 4, 7 },// 3
            { 3, 4, 5, 7, 4, 1, 5, 4, 3, 1, 4, 7 },// 4
            { 8, 7, 6, 6, 3, 0, 0, 1, 2, 2, 5, 8 },// 5
            { 6, 7, 8, 8, 5, 2, 2, 1, 0, 0, 3, 6 },// 6
            { 6, 3, 0, 6, 3, 0, 6, 3, 0, 2, 5, 8 },// 7
            { 0, 3, 6, 8, 5, 2, 0, 3, 6, 0, 3, 6 },// 8
            { 7, 4, 1, 7, 4, 1, 7, 4, 1, 1, 4, 7 },// 9
            { 1, 4, 7, 7, 4, 1, 1, 4, 7, 1, 4, 7 },// 10
            { 8, 5, 2, 8, 5, 2, 8, 5, 2, 0, 3, 6 },// 11
            { 2, 5, 8, 6, 3, 0, 2, 5, 8, 2, 5, 8 },// 12
            { 2, 1, 0, 2, 1, 0, 2, 1, 0, 2, 1, 0 },// 13
            { 0, 1, 2, 0, 1, 2, 0, 1, 2, 0, 1, 2 },// 14
            { 5, 4, 3, 5, 4, 3, 5, 4, 3, 5, 4, 3 },// 15
            { 3, 4, 5, 3, 4, 5, 3, 4, 5, 3, 4, 5 },// 16
            { 8, 7, 6, 8, 7, 6, 8, 7, 6, 8, 7, 6 },// 17
            { 6, 7, 8, 6, 7, 8, 6, 7, 8, 6, 7, 8 },// 18
    };

    private static boolean verify(State state) {
        return state.cb >> 45 == 511;
    }

    private static void printState(State s) {
        List<Integer> list = new ArrayList<Integer>();
        BigInteger m = s.move;
        while (m.compareTo(BigInteger.ZERO) > 0) {
            list.add((m.mod(n_19)).intValue());
            m = m.divide(n_19);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(list.size()).append('\n');

        for (int i = list.size() - 1; i >= 0; i--) {
            sb.append(list.get(i)).append('\n');
        }
        System.out.printf(sb.toString());
    }

    private static boolean check() {
        // 检查6个中心点, 6
        int cnt = 0;
        for (int s = 1; s <= 6; s++) {
            cnt += cube[s][1][1];
        }
        if (cnt < 1)
            return false;
        // 检查8个角位, 24
        // (1,2,3) (1,4,3) (4,6,3) (2,6,3)
        // (1,2,5) (1,4,5) (4,6,5) (2,6,5)
        cnt = 0;
        cnt += (cube[1][0][0] | cube[2][2][2] | cube[3][2][0]);
        cnt += (cube[1][0][2] | cube[4][2][0] | cube[3][2][2]);
        cnt += (cube[4][0][0] | cube[6][2][2] | cube[3][0][2]);
        cnt += (cube[2][0][2] | cube[6][2][0] | cube[3][0][0]);

        cnt += (cube[1][2][0] | cube[2][2][0] | cube[5][2][2]);
        cnt += (cube[1][2][2] | cube[4][2][2] | cube[5][2][0]);
        cnt += (cube[4][0][2] | cube[6][0][2] | cube[5][0][0]);
        cnt += (cube[2][0][0] | cube[6][0][0] | cube[5][0][2]);
        if (cnt < 4)
            return false;
        // 检查12个棱位 24
        // (1,3) (2,3), (6,3) (4,3)
        // (1,5) (2,5), (6,5) (4,5)
        // (2,1) (1,4), (4,6) (6,2)
        cnt = 0;
        cnt += (cube[1][0][1] | cube[3][2][1]);
        cnt += (cube[2][1][2] | cube[3][1][0]);
        cnt += (cube[6][2][1] | cube[3][0][1]);
        cnt += (cube[4][1][0] | cube[3][1][2]);

        cnt += (cube[1][2][1] | cube[5][2][1]);
        cnt += (cube[2][1][0] | cube[5][1][2]);
        cnt += (cube[6][0][1] | cube[5][0][1]);
        cnt += (cube[4][1][2] | cube[5][1][0]);

        cnt += (cube[2][2][1] | cube[1][1][0]);
        cnt += (cube[1][1][2] | cube[4][2][1]);
        cnt += (cube[4][0][1] | cube[6][1][2]);
        cnt += (cube[6][1][0] | cube[2][0][1]);
        if (cnt < 4)
            return false;
        return true;
    }

    private static void toCube(long cb) {
        for (int s = 6; s >= 1; s--)
            for (int i = 2; i >= 0; i--)
                for (int j = 2; j >= 0; j--) {
                    cube[s][i][j] = (int) (cb & 1);
                    cb >>= 1;
                }
    }

//    private static void print() {
//        System.out.println("------------------------------------------");
//        StringBuilder sb = new StringBuilder();
//        // back 6
//        for (int i = 0; i < 3; i++) {
//            sb.append("   ");
//            for (int j = 0; j < 3; j++)
//                sb.append(cube[6][i][j]);
//            sb.append('\n');
//        }
//        // left top right bottom 2 3 4 5
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 12; j++) {
//                sb.append(cube[j / 3 + 2][i][j % 3]);
//            }
//            sb.append('\n');
//        }
//        // front
//        for (int i = 0; i < 3; i++) {
//            sb.append("   ");
//            for (int j = 0; j < 3; j++)
//                sb.append(cube[1][i][j]);
//            sb.append('\n');
//        }
//        System.out.print(sb.toString());
//        System.out.println("------------------------------------------");
//    }

    static class State implements Comparable<State> {
        public long cb;
        public int cnt;
        public BigInteger move = BigInteger.ZERO;
        public int pq;

        public State(int[][][] c) {
            for (int s = 1; s <= 6; s++)
                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < 3; j++)
                        cb = cb * 2 + c[s][i][j];
            int d = 0;
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    d += c[1][i][j];
            pq = -d;
        }

        @Override
        public int compareTo(State o) {
            if (pq == o.pq) {
                if (cnt == o.cnt)
                    return 0;
                return cnt < o.cnt ? 1 : -1;
            }
            return pq < o.pq ? -1 : 1;
        }
    }
}