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
 * @author wuyq101
 * @version 1.0
 */
public class Checker {
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
        State init = new State(cube);
        cin.next();
        cin.next();
        int n = cin.nextInt();
        for(int i=0; i<n; i++){
            int k = cin.nextInt();
            init = move(init, k);
            System.out.printf("move %d",k);
            print();
        }
//        sovle();
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
            if (verify(state)) {
                System.out.printf("%d---%d\n", pq.size(), set.size());
                System.out.println("yes");
                printState(state);
                return;
            }
            for (int k = 1; k <= 18; k++) {
                State next = move(state, k);
//                System.out.printf("move %d", k);
//                print();
                if (next.cnt < 10000 && set.add(next.cb)) {
                    pq.add(next);
                }
            }
        }
        System.out.println("no");
    }

    private static int[] circle = new int[12];
    private static int[][] face = new int[3][3];

    private static State move(State state, int k) {
        toCube(state.cb);
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
        next.move = state.move * 18 + k - 1;
        return next;
    }

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
        return (state.cb >> 45) == 511;
    }

    private static void printState(State s) {
        System.out.println(s.cnt);
        List<Integer> list = new ArrayList<Integer>();
        long m = s.move;
        while (m > 0) {
            list.add((int) (m % 18 + 1));
            m /= 18;
        }
        for (int i = 0; i < s.cnt; i++) {
            System.out.println(list.get(i));
        }
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
        cnt += (cube[1][1][2] | cube[4][2][0] | cube[3][2][2]);
        cnt += (cube[4][0][2] | cube[6][2][2] | cube[3][0][2]);
        cnt += (cube[2][0][2] | cube[6][2][0] | cube[3][2][0]);

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

    private static void print() {
        System.out.println("------------------------------------------");
        StringBuilder sb = new StringBuilder();
        // back 6
        for (int i = 0; i < 3; i++) {
            sb.append("   ");
            for (int j = 0; j < 3; j++)
                sb.append(cube[6][i][j]);
            sb.append('\n');
        }
        // left top right bottom 2 3 4 5
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 12; j++) {
                sb.append(cube[j / 3 + 2][i][j % 3]);
            }
            sb.append('\n');
        }
        // front
        for (int i = 0; i < 3; i++) {
            sb.append("   ");
            for (int j = 0; j < 3; j++)
                sb.append(cube[1][i][j]);
            sb.append('\n');
        }
        System.out.print(sb.toString());
        System.out.println("------------------------------------------");
    }

    static class State implements Comparable<State> {
        public long cb;
        public int cnt;
        public long move;
        public int pq;

        public State(int[][][] c) {
            for (int s = 1; s <= 6; s++)
                for (int i = 0; i < 3; i++)
                    for (int j = 0; j < 3; j++)
                        cb = cb * 2 + c[s][i][j];
            pq = 4 * c[1][1][1] + 2 * (c[1][0][1] + c[1][1][0] + c[1][1][2] + c[1][2][1])
                    + (c[1][0][0] + c[1][0][2] + c[1][2][0] + c[1][2][2]);
        }

        @Override
        public int compareTo(State o) {
            if (pq == o.pq)
                return 0;
            return pq > o.pq ? -1 : 1;
        }
    }
}