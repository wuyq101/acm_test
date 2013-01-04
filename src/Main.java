import java.util.Scanner;

/**
 * 1094 排序
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main {
    private static int n;
    private static int m;
    private static int[][] matrix = new int[26][26];

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (true) {
            n = cin.nextInt();
            m = cin.nextInt();
            if (n == 0 && m == 0)
                break;
            for (int i = 0; i < m; i++) {
                String line = cin.next();

            }

        }
    }
    
    private static void init(){
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                matrix[i][j] = 0;
            }
        }
    }

}
