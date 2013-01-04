import java.util.Scanner;

/**
 * 1102
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main1102 {
    private static int s;
    private static String n;
    // 行：23=2*10+3,
    // 列：最多8位数 (10+2)*8+7, 7为8个数字的中间空列
    private static char[][] p = new char[23][103];

    /**
     * <pre>
     * 0--9数字的形状,使用7条边来表示，顺序如下
     *     ----(0)
     *    |(1) |(2)
     *    |    |
     *     ----(3)
     *    |(4) |(5)
     *    |    |
     *     ----(6)
     *     
     *     0,3,6为行，其余为列
     * </pre>
     */
    private static int[][] digit = { { 1, 1, 1, 0, 1, 1, 1 },// 0
            { 0, 0, 1, 0, 0, 1, 0 },// 1
            { 1, 0, 1, 1, 1, 0, 1 },// 2
            { 1, 0, 1, 1, 0, 1, 1 },// 3
            { 0, 1, 1, 1, 0, 1, 0 },// 4
            { 1, 1, 0, 1, 0, 1, 1 },// 5
            { 1, 1, 0, 1, 1, 1, 1 },// 6
            { 1, 0, 1, 0, 0, 1, 0 },// 7
            { 1, 1, 1, 1, 1, 1, 1 },// 8
            { 1, 1, 1, 1, 0, 1, 1 },// 9
    };

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (true) {
            s = cin.nextInt();
            n = cin.next();
            if (s == 0 && "0".equals(n))
                break;
            print();
        }
    }

    private static void print() {
        // 初始化
        int R = 2 * s + 3;
        int C = (s + 2) * n.length() + n.length() - 1;
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                p[i][j] = ' ';
            }
        }
        for (int i = 0; i < n.length(); i++) {
            // 对每一个数字开始填充
            int k = n.charAt(i) - '0';
            // 数字k开始的列数
            int x = (s + 3) * i;
            // 正对数字k的每一条边
            for (int j = 0; j < 7; j++) {
                if (digit[k][j] == 1) {
                    // 表示这条边有
                    if (j % 3 == 0) {
                        // 横边
                        int r = (j / 3) * (s + 1);
                        fill(r, x + 1, s, 0);
                    } else {
                        // 纵边
                        int r = j == 1 || j == 2 ? 1 : 2 + s;
                        int c = j == 1 || j == 4 ? x : x + s + 1;
                        fill(r, c, s, 1);
                    }
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < R; i++) {
            for (int j = 0; j < C; j++) {
                sb.append(p[i][j]);
            }
            sb.append('\n');
        }
        System.out.println(sb.toString());
    }

    private static void fill(int r, int c, int s, int v) {
        if (v == 0) {
            // 横
            for (int i = c; i < c + s; i++)
                p[r][i] = '-';
        } else {
            // 纵
            for (int i = r; i < r + s; i++)
                p[i][c] = '|';
        }
    }
}
