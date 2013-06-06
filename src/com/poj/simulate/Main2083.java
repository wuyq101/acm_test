package com.poj.simulate;
import java.io.BufferedInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 2083
 * 
 * @author wyq
 * @version 1.0
 */
public class Main2083 {
    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        char[][] m = new char[3][3];
        m[0] = new char[] { 'X', ' ', 'X' };
        m[1] = new char[] { ' ', 'X', ' ' };
        m[2] = new char[] { 'X', ' ', 'X' };
        data.put(2, m);
        int n = cin.nextInt();
        while (n != -1) {
            print(n);
            System.out.println("-");
            n = cin.nextInt();
        }
    }

    private static void print(int n) {
        if (n == 1) {
            System.out.printf("%c\n", 'X');
        } else {
            char[][] chs = map(n);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < chs.length; i++) {
                for (int j = 0; j < chs[i].length; j++) {
                    sb.append(chs[i][j]);
                }
                sb.append('\n');
            }
            System.out.print(sb.toString());
        }
    }

    private static Map<Integer, char[][]> data = new HashMap<Integer, char[][]>();

    private static char[][] map(int n) {
        if (n == 2) {
            return data.get(2);
        }
        if (data.containsKey(n)) {
            return data.get(n);
        }
        char[][] m = map(n - 1);
        int len = len(n - 1);
        char[][] result = new char[len * 3][len * 3];
        // 第一行
        copy(result, m, 0, 0);
        fill(result, 0, len, len);
        copy(result, m, 0, len * 2);
        // 第二行
        fill(result, len, 0, len);
        copy(result, m, len, len);
        fill(result, len, len * 2, len);
        // 第三行
        copy(result, m, len * 2, 0);
        fill(result, len * 2, len, len);
        copy(result, m, len * 2, len * 2);
        data.put(n, result);
        return result;
    }

    private static void fill(char[][] map, int x, int y, int len) {
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                map[x + i][y + j] = ' ';
            }
        }
    }

    private static void copy(char[][] map, char[][] data, int x, int y) {
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                map[x + i][y + j] = data[i][j];
            }
        }
    }

    private static int len(int n) {
        return (int) Math.pow(3, n - 1);
    }
}
