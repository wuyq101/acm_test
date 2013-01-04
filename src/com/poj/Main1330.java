package com.poj;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/**
* 1330 LCA 最近公共祖先   Tarjan算法
*
* @author wyq@palmdeal.com
* @version 1.0
*/
public class Main1330 {
    private static int N;
    private static int M;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        // 5:(3)
        Pattern p1 = Pattern.compile("(\\d+):\\((\\d+)\\)");
        Pattern p2 = Pattern.compile("\\((\\d+) (\\d+)\\)");
        while (cin.hasNextInt()) {
            N = cin.nextInt();
            while (cin.hasNext(p1)) {
                MatchResult m = cin.match();
                int lable = Integer.valueOf(m.group(1));
                int cnt = Integer.valueOf(m.group(2));
                //cin.skip(p1);
                cin.next();
                for (int i = 0; i < cnt; i++) {
                    System.out.println(cin.nextInt());
                }
            }
            M = cin.nextInt();
            for(int i=0; i<M; i++){
                String s1 = cin.next();
                String s2 = cin.next();
                System.out.printf("u===%s      v=======%s",s1,s2);
            }
        }
    }
   
    private static void lca(Node u){
        //MAKE-SET(u)
        //ancestor[FIND-SET(u)]=u;
        //for(u的每个儿子v){
            //lca(v);
            //UNION(u,v)
            //ancestor[FIND-SET(u)]=u;
        //}
        //color[u] = black;
        //for(Q(u)中的所有元素 v){
            //if(color[v]==black)
                //lca(u,v)---->ancestor[FIND-SET(v)];
        //}
    }

    static class Node {
        public int label;
    }

}