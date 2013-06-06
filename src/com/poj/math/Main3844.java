package com.poj.math;
import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 3844
 * 
 * 如果ai...aj(i<=j)是连续和能被整除的。可以推出: sum(a1,a2,...,ai-1)%d == sum(a1,a2,...,aj)%d. 同余 所以，如果出现一次同同余，表示有一个连续数组的和能够被整除。
 * 
 * @author wyq
 * @version 1.0
 */
public class Main3844 {
    private static int[] mod = new int[1000000];

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        int d, n;
        int c = cin.nextInt();
        while (c-- > 0) {
            d = cin.nextInt();
            n = cin.nextInt();
            Arrays.fill(mod, 0, d, 0);
            mod[0] = 1;
            int sum = 0;
            int count = 0;
            for (int i = 0; i < n; i++) {
                int a = cin.nextInt();
                sum = (sum + a) % d;
                count += mod[sum];
                mod[sum]++;
            }
            System.out.println(count);
        }
    }
}

/**
 *  使用java会TME，所以使用c++，靠！！！
#include <iostream>
using namespace std;

int c,d,n;
int mod[1000000];
int count,sum,a,i;

int main(){
    scanf("%d",&c);
    while(c-->0){
        scanf("%d%d",&d,&n);
        memset(mod, 0, sizeof(mod));
        mod[0]=1;
        sum = 0;
        count=0;
        for(i=0; i<n; i++){
            scanf("%d",&a);
            sum = (sum+a)%d;
            count += mod[sum];
            mod[sum]++;
        }
        printf("%d\n",count);
    }
    return 0;
}
 */
