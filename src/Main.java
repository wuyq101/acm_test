import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * <pre>
 *     http://poj.org/problem?id=2184
 *
 * </pre>
 * User: wuyq101
 * Date: 13-5-31
 * Time: 下午5:29
 */
public class Main {
    private static int n, T, TS, TF;
    private static int bestT;
    private static int[] s = new int[100], f = new int[100], used = new int[100];


    public static void main(String[] args) throws Exception {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(cin.readLine());
        for (int i = 0; i < n; i++) {
            StringTokenizer st = new StringTokenizer(cin.readLine());
            s[i] = Integer.parseInt(st.nextToken());
            f[i] = Integer.parseInt(st.nextToken());
            used[i] = 0;
        }
        dfs(0);
    }

    private static void dfs(int i) {
        if (T > bestT) {

        }
        //use i
        used[i] = 1;
    }
}
