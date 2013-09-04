package com.poj.enumeration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <pre>
 *     原题：http://poj.org/problem?id=2329
 *     题意：给一个矩阵A，N*N非负整数。将矩阵中的0替换为其距离最近的那个非0整数。如果有多个非0整数，距离一样的话，就不替换。
 *     求替换之后的矩阵。
 *     其中距离的定义如下：A distance between two elements Aij and Apq is defined as |i − p| + |j − q|.也是就曼哈顿距离。
 *     分析：
 *         针对每个0点，从距离为1到2N-2一圈一圈向外扩展搜索，如果在某圈搜索到只有一个非负值，将该点更新为这个值，如果有多个值，则不
 *         更新，退出搜索。
 *         复杂度为N^3.
 * </pre>
 * Created by Administrator on 13-9-4.
 */
public class Main2329 {
    private static int N;
    private static int[][] A = new int[200][200], B = new int[200][200];

    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(cin.readLine());
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(cin.readLine());
            for (int j = 0; j < N; j++)
                A[i][j] = Integer.parseInt(st.nextToken());
        }
        solve();
    }

    private static void solve() {
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                if (A[i][j] > 0)
                    B[i][j] = A[i][j];
                else
                    B[i][j] = search(i, j);
            }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sb.append(B[i][j]).append(' ');
            }
            sb.append('\n');
        }
        System.out.print(sb.toString());
    }

    private static int search(int i, int j) {
        int n = 2 * N - 2;
        int result = 0;
        int count = 0;
        for (int len = 1; len <= n; len++) {
            for (int k = 0; k < len; k++) {
                for (int d = 0; d < 4; d++) {
                    int x = i + start[d][0] * len + direct[d][0] * k;
                    int y = j + start[d][1] * len + direct[d][1] * k;
                    if (x >= 0 && x < N && y >= 0 && y < N && A[x][y] > 0) {
                        result = A[x][y];
                        count++;
                    }
                }
            }
            if (result > 0 && count == 1)
                return result;
            if (count >= 2)
                return 0;
        }
        return 0;
    }

    private static int[][] start = {{0, 1}, {-1, 0}, {0, -1}, {1, 0}};
    private static int[][] direct = {{-1, -1}, {1, -1}, {1, 1}, {-1, 1}};
}
