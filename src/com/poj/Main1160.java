package com.poj;
import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 1160 动态规划 dp
 */
public class Main1160 {
    // 村庄 1<=V<=300
    private static int V;
    private static int[] v;
    // 邮局 1<=P<=30
    private static int P;

    // dp[i][j], 代表前i个邮局，负责前j个村庄的最小距离和,并且第j处有一个邮局
    // 如果下一个邮局负责第j+1个到j+k个村庄, 并且放在第j+k处
    // dp[i+1][j+k] = dp[i][j]+sigma(j+1, j+k)
    // 从dp[i-1][j],开始计算dp[i][k]的最小距离
    // 如dp[1][4]--->dp[2][6].代表在6处在放一个邮局,距离的边化。1---4的村庄还是之前的位置，5的村庄看是离4近还是6近，6之后的村庄由6负责
    // dp[1][7]-->dp[2][6]的情况和dp[1][6]-->dp[2][7]一样，所以这里只考虑
    // dp[i-1][j]--->dp[i][k]，k>j的情况,k<j的情况不考虑
    // dp[i][k],可以从每一个dp[i-1][j],j<k计算得到
    // 这部分的和可以这样计算，在j+1---k-1之间找到第一个距离k比距离j近的点mid，mid之后的点都有k负责，mid之前的点都有j负责
    // dp[i-1][j] = a + b,
    // a 代表1---mid-1点的距离和，由原来的j负责, 这部分的和不变，
    // b 代表mid之后的距离和， 由更近的k来负责， 这部分的和会变少, 每个减少的数值分别是
    // mid---k， 一个一个k'计算， 新的距离，v[k]-v[k'] 老的距离 v[k']-v[j], 差值： v[k']-v[j] - (v[k]-v[k']) = 2v[k']-v[j]-v[k]
    // k+1--V, 新的距离 v[k']-v[k], 老的距离v[k']-v[j], 差值： v[k']-v[j] - (v[k']-v[k]) = v[k]-v[j];
    // dp[i][k] = dp[i-1][j] - {2v[k']-v[j]-v[k], k-mid部分} - (V-k)*(v[k]-v[j]), k+1部分
    private static int[][] dp;
    
    private static int[][] cost;

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        V = cin.nextInt();
        P = cin.nextInt();
        v = new int[V + 1];
        for (int i = 1; i <= V; i++) {
            v[i] = cin.nextInt();
        }
        solve();
    }

    private static void solve() {
        if (P == V) {
            System.out.println(0);
            return;
        }
        dp = new int[P + 1][V + 1];
        cost = new int[V+1][V+1];
        for(int i=1; i<=V; i++){
            cost[i][i] = v[i];
            for(int j=i+1; j<=V; j++){
                cost[i][j] = cost[i][j-1]+v[j];
            }
        }
        // 只有一个邮局摆放在i的位置上
        for (int i = 1; i <= V; i++) {
            int sum = 0;
            for (int j = 1; j <= V; j++) {
                if (i <= j)
                    sum += v[j] - v[i];
                else
                    sum += v[i] - v[j];
            }
            dp[1][i] = sum;
        }
        for (int i = 2; i <= P; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        // 一个一个增加邮局
        for (int i = 1; i < P; i++) {
            for (int j = i; j <= V; j++) {
                // 从dp[i][j]---计算dp[i+1][k]
                for (int k = j + 1; k <= V; k++) {
                    if (dp[i][j] - (V - j) * (v[k] - v[j]) >= dp[i + 1][k]){
                        //System.out.printf("ignore (%d,%d)--->(%d,%d)\n", i,j,i+1,k);
                        continue;
                    }
                    int sum = 0;
                    //int mid = 0;
                    // 先找到第一个距离k比距离j近的点
//                    for (int m = j + 1; m <= k; m++) {
//                        if (v[k] - v[m] < v[m] - v[j]) {
//                            mid = m;
//                            break;
//                        }
//                    }
                    int l=j+1,r=k,m=0;
                    int mid = 0;
                    while(l<=r){
                        m = l+(r-l)/2;
                        if(v[k]-v[m]<=v[m]-v[j]){
                            //m距离k比距离j近
                            r = m-1;
                            mid = m;
                        }else{
                            l=m+1;
                        }
                    }
                    //System.out.printf("%d %d\n",mid, m);
                    
                    // 算mid--k部分的差值
//                    for (int k1 = mid; k1 <= k; k1++) {
//                        sum += (v[k1] + v[k1] - v[j] - v[k]);
//                    }
                    sum += 2*cost[mid][k]-(k-mid+1)*(v[k]+v[j]);
                    // k+1， V部分
                    sum += (V - k) * (v[k] - v[j]);
                    if (dp[i][j] - sum < dp[i + 1][k]) {
//                        System.out.printf("dp[%d][%d]= %d =dp[%d][%d]-%d\n", i + 1, k, dp[i + 1][k], i, j, dp[i][j],
//                                sum);
                        dp[i + 1][k] = dp[i][j] - sum;
                    }
                }
            }
        }
        int min = dp[P][1];
        for (int i = 2; i <= V; i++) {
            if (dp[P][i] < min) {
                min = dp[P][i];
            }
        }
        System.out.println(min);
    }

}
