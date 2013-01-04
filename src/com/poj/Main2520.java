package com.poj;
import java.util.Arrays;
import java.util.Scanner;
import static java.lang.Math.min;

/**
 * 2520 编辑距离
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main2520 {
    private static char[] A;
    private static char[] B;
    private static char[] C;
    // f[i][j] = min {f[i-1][j]+3, f[i][j-1]+3, f[i-1][j-1]+score(a,b)}
    private static int[][] f;
    private static int la;
    private static int lb;
    // 最大的可能值
    private static int mv;

    private static int score(char a, char b) {
        return a == b ? 0 : (a + b == 138 ? 4 : 5);
    }

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext()) {
            A = cin.next().toCharArray();
            B = cin.next().toCharArray();
            solve();
        }
    }

    private static void solve() {
        la = A.length;
        lb = B.length;
        if (la < lb) {
            C = A;
            A = B;
            B = C;
            la = A.length;
            lb = B.length;
        }
        mv = 3 * (la + lb) / 10;
        System.out.printf("===========mv========= %d\n", mv);
        f = new int[2][lb + 1];
        f[0][0] = 0;
        for (int j = 1; j <= lb; j++) {
            f[0][j] = f[0][j - 1] + 3;
        }
        for (int i = 0; i < la; i++) {
            // 当前行的结果(已经计算完毕)
            int p = i % 2;
            // 下一行的结果
            int q = 1 - p;
            Arrays.fill(f[p], Integer.MAX_VALUE);
            f[p][0] = f[q][0] + 3;
            for (int j = 0; j < lb; j++) {
                // f[p][j] ---> f[p][j+1],f[q][j],f[q][j+1].
                // f[q][j+1] ---> f[q][j+2]
                // f[p][j + 1] = f[p][j] + 3;
                if (f[p][j] != -1) {
                    f[p][j+1] = min(f[p][j+1],f[p][j] + 3);
                    f[q][j] = min(f[q][j], f[p][j] + 3);
                    f[q][j + 1] = min(f[q][j + 1], f[p][j] + score(A[i], B[j]));
//                    if (j + 2 <= lb)
//                        f[q][j + 2] = min(f[q][j + 2], f[q][j + 1] + 3);

                    // f[p][j] = Math.min(f[q][j], f[p][j - 1]) + 3;
                    // f[p][j] = Math.min(f[p][j], f[q][j - 1] + score(A[i - 1], B[j - 1]));
                   // if (f[p][j] > mv) {
                        System.out.printf("(%d,%d)=%d\n", i, j, f[p][j]);
                   // }
                }
            }
            // 所有的j计算完毕,然后开始剪枝条,为下一次搜索减少状态空间
            for (int j = 1; j <= lb; j++) {
                if (f[q][j] != Integer.MAX_VALUE) {
                    for (int j1 = j + 1; j1 <= lb; j1++) {
                        // 开始判断
                    }
                }
            }
        }
        int q = 1-la % 2;
        System.out.println(f[q][lb]);
    }
}