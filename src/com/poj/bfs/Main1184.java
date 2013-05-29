package com.poj.bfs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * <pre>
 *     http://poj.org/problem?id=1184
 *     题意：给出一个6位数字字符串，经过规定的几个操作之后，变为指定的目标字符串，求最少的操作步数。
 *     Swap0：按Swap0，光标位置不变，将光标所在位置的数字与录入区的1号位置的数字（左起第一个数字）交换。
 *     如果光标已经处在录入区的1号位置，则按Swap0键之后，录入区的数字不变；
 *
 *     Swap1：按Swap1，光标位置不变，将光标所在位置的数字与录入区的6号位置的数字（左起第六个数字）交换。
 *     如果光标已经处在录入区的6号位置，则按Swap1键之后，录入区的数字不变；
 *
 *     Up：按Up，光标位置不变，将光标所在位置的数字加1（除非该数字是9）。
 *     例如，如果光标所在位置的数字为2，按Up之后，该处的数字变为3；
 *     如果该处数字为9，则按Up之后，数字不变，光标位置也不变；
 *
 *     Down：按Down，光标位置不变，将光标所在位置的数字减1（除非该数字是0），
 *     如果该处数字为0，则按Down之后，数字不变，光标位置也不变；
 *
 *     Left：按Left，光标左移一个位置，如果光标已经在录入区的1号位置（左起第一个位置）上，则光标不动；
 *
 *     Right：按Right，光标右移一个位置，如果光标已经在录入区的6号位置（左起第六个位置）上，则光标不动。
 *
 *     分析：广度优先搜索，如果直接广度优先搜索的话，6个光标位置*1000000个可能的数字=6000000种状态。
 *     数量巨大，基本上不可能。分析发现，交换和移动光标操作不会改变数值，而且改变数值操作和顺序无关，只要光标到达过该位，就可以操作。
 *     所以，将变值操作分离出来，这样，就只有4个操作需要搜索：swap0，swap1，left，right。
 *     然后记录的状态为：操作之后的数字，光标的位置，光标到达过的位置，使用的步数。
 *     操作之后的数字，都是换位得到的，一共6的排列，720种，
 *     光标的位置，一个6种
 *     光标到达过的位置，使用二进制表示，2^6=64中
 *     这样状态压缩之后，就可以求解。
 *     每一个状态：使用当前的数字，和光标的位置，以及光标曾经到达过的位置信息，组合这些，再加上变值操作可以求出最少的步骤。
 *     然后在添加每个状态的时候，判断这个状态是否已经添加，是否已经被访问。（判断的时候，使用哈希）
 *     另外，如果到达该状态的步骤>=当前最优值-1,那这个状态可以忽略,因为它至少还需要一步操作，但是已经不可能比当前解更优。
 *     最后，注意给出的两个值是相同的情况，直接返回0.
 * </pre>
 * User: wuyq101
 * Date: 13-5-27
 * Time: 下午4:49
 */
public class Main1184 {
    private static int min;
    private static int M, N;//目标数字
    private static int[] target = new int[6];
    private static int[] idxv = {100000, 10000, 1000, 100, 10, 1};
    private static int[] curse_idxv = {32, 16, 8, 4, 2, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        String[] strings = cin.readLine().split(" ");
        M = Integer.parseInt(strings[0]);
        N = Integer.parseInt(strings[1]);
        for (int i = 0; i < 6; i++)
            target[i] = getIdxValue(N, i);
        min = 100;
        if (M == N) {
            System.out.println(0);
            return;
        }
        bfs();
        System.out.println(min);
    }

    private static List<int[]> list = new ArrayList<int[]>();
    //已经访问过的结点
    private static List<int[]> visited = new ArrayList<int[]>();
    private static Set<Integer> visited_set = new HashSet<Integer>();
    private static Set<Integer> next_set = new HashSet<Integer>();

    private static void bfs() {
        //中间状态：0数值, 1光标所在位置，2光标到达过的位置，3所使用的步骤数
        int[] state = new int[4];
        state[0] = M;
        state[1] = 0;
        state[2] = curse_idxv[0];
        state[3] = 0;
        list.add(state);
        while (list.size() > 0) {
            state = list.remove(0);
            next_set.remove(getKey(state));
            addVisited(state);
            if (state[3] >= min)
                continue;
            //左交换
            int[] next = swap(state, 0);
            addNext(next);
            //右交换
            next = swap(state, 5);
            addNext(next);
            //左移
            next = move(state, -1);
            addNext(next);
            //右移
            next = move(state, 1);
            addNext(next);
        }
    }

    private static int change(int[] state) {
        int cnt = state[3];
        for (int i = 0; i < 6; i++) {
            int a = getIdxValue(state[0], i);
            int b = target[i];
            if ((state[2] & curse_idxv[i]) == curse_idxv[i])
                cnt += Math.abs(a - b);
            else {
                cnt += 1;
                cnt += Math.abs(a - b);
            }
        }
        return cnt;
    }

    //增加已经访问
    private static void addVisited(int[] next) {
        visited.add(next);
        visited_set.add(getKey(next));
        int cnt = change(next);
        if (cnt < min) {
            min = cnt;
        }
    }

    private static int getKey(int[] state) {
        return state[0] * 1000 + state[1] * 100 + state[2];
    }

    private static void addNext(int[] next) {
        if (next[0] == N) {
            if (next[3] < min)
                min = next[3];
            return;
        } else {
            if (next[3] >= min - 1)
                return;
        }
        int key = getKey(next);
        if (visited_set.contains(key))
            return;
        if (next_set.contains(key))
            return;
        list.add(next);
        next_set.add(key);
    }

    //交换 idx=0,左交换   idx=5，右交换
    private static int[] swap(int[] state, int idx) {
        int[] next = new int[4];
        int a = getIdxValue(state[0], idx);
        int b = getIdxValue(state[0], state[1]);
        next[0] = state[0] + (b - a) * idxv[idx] + (a - b) * idxv[state[1]];
        next[1] = state[1];
        next[2] = state[2];
        //如果是右交换，表示光标到达过5这个位置
        if (idx == 5) {
            next[2] = state[2] | curse_idxv[5];
        }
        next[3] = state[3] + 1;
        return next;
    }

    //移动光标， dir=-1，左移， dir=1，右移
    private static int[] move(int[] state, int dir) {
        int[] next = new int[4];
        next[0] = state[0];
        next[1] = state[1] + dir;
        if (next[1] < 0)
            next[1] = 0;
        if (next[1] > 5)
            next[1] = 5;
        next[2] = state[2] | curse_idxv[next[1]];
        next[3] = state[3] + 1;
        return next;
    }

    /**
     * 获取n中idx光标上的数字
     */
    private static int getIdxValue(int n, int idx) {
        return n / idxv[idx] % 10;
    }
}
