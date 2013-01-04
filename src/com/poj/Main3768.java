package com.poj;
import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 3768
 * 
 * @author wyq
 * @version 1.0
 */
public class Main3768 {
    private static int N, Q;
    private static char[][] template;

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        N = cin.nextInt();
        while (N != 0) {
            template = new char[N][N];
            cin.nextLine();
            // 读取模板
            for (int i = 0; i < N; i++) {
                String line = cin.nextLine();
                for (int j = 0; j < N; j++) {
                    template[i][j] = line.charAt(j);
                }
            }
            Q = cin.nextInt();
            print(Q);
            N = cin.nextInt();
        }
    }

    //private static Map<Integer, char[][]> data = new HashMap<Integer, char[][]>();

    private static void print(int n) {
        if (n == 1) {
            print(template);
            return;
        }
        //data.clear();
        //data.put(1, template);
        char[][] pic = map(n);
        print(pic);
    }

    private static char[][] map(int n) {
        if (n == 1) {
            return template;
        }
//        if (data.containsKey(n)) {
//            return data.get(n);
//        }
        char[][] m = map(n - 1);
        int len = m.length;
        char[][] result = new char[len * N][len * N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (template[i][j] == ' ') {
                    // 使用空格
                    fill(result, len * i, len * j, len);
                } else {
                    copy(result, m, len * i, len * j);
                }
            }
        }
        //data.put(n, result);
        return result;
    }

    private static void fill(char[][] map, int x, int y, int len) {
        for (int i = 0; i < len; i++) {
            Arrays.fill(map[x + i], y, y + len, ' ');
        }
    }

    private static void copy(char[][] map, char[][] data, int x, int y) {
        for (int i = 0; i < data.length; i++) {
            System.arraycopy(data[i], 0, map[x + i], y, data[i].length);
        }
    }

    private static void print(char[][] pic) {
        StringBuilder sb = new StringBuilder();
        int len = pic.length;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                sb.append(pic[i][j]);
            }
            sb.append('\n');
        }
        System.out.print(sb.toString());
    }
}
