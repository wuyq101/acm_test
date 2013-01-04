package com.poj;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 3979 分数加减法
 * @author wyq
 * @version 1.0
 */
public class Main3979 {
    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        while(cin.hasNext()){
            String line = cin.nextLine();
            //a/bOc/d
            int a = line.charAt(0)-'0';
            int b = line.charAt(2)-'0';
            int c = line.charAt(4)-'0';
            int d = line.charAt(6)-'0';
            //如果是减法，将c变为-c
            if('-'==line.charAt(3)){
                c = -c;
            }
            cal(a,b,c,d);
        }
    }
    
    public static void cal(int a, int b, int c, int d){
        //求b,d最小公倍数
        int gcd = gcd(b,d);
        //最小公倍数
        int e = b*d/gcd;
        a = a*(e/b);
        c = c*(e/d);
        a = a+c;
        //答案是a/e，化简
        if(a%e==0){
            System.out.println(a/e);
            return;
        }
        boolean flag = false;
        if(a<0){
            flag = true;
            a = -a;
        }
        gcd = gcd(a,e);
        if(flag){
            System.out.println("-"+(a/gcd)+"/"+(e/gcd));
        }else{
            System.out.println((a/gcd)+"/"+(e/gcd));
        }
    }

    //最大公约数
    public static int gcd(int a, int b){
        if(a==0){
            return b;
        }
        if(b==0){
            return a;
        }
        if(a<b){
            int temp = b;
            b = a;
            a = temp;
        }
        return gcd(b, a%b);
    }
    
}
