package com.poj;
import java.io.BufferedInputStream;
import java.util.Scanner;

/**
 * 1090 chain 递推
 * 
 * <pre>
 * 1.   每次必须且只能改变一个
 * 2.   第1个随时都可以改变
 * 3.   1---k-1个为0,k为1时候，可以改变k+1   (1<=k<n)
 * 
 * f(n) 为前n个都变为0的最小步骤
 * t(n) 1-----n-1为0，n为1时，将1---n都变为0的最小步骤
 * o(n) 为1---n-1为0，n为1的步骤
 * 
 * f(1) = c[1];
 * o(1) = 1-c[1];
 * t(1) = 1;
 * 
 * f(n) = o(n-1)+1+t(n-1), c[n]==1
 *        f(n-1),          c[n]==0
 *        
 * o(n) = f(n-1)                c[n]==1
 *        o(n-1)+1+t(n-1)       c[n]==0
 *        
 * t(n) = 2*t(n-1)+1;
 * </pre>
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main1090 {
    private static int n;
    private static int[] c;

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        n = cin.nextInt();
        c = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            c[i] = cin.nextInt();
        }
        solve();
    }

    private static void solve() {
        BInt f = BInt.valueOf(c[1]);
        BInt o = BInt.valueOf(1 - c[1]);
        BInt t = BInt.valueOf(1);
        BInt f1 = BInt.valueOf(1);
        for (int i = 2; i <= n; i++) {
            if (c[i] == 1) {
                f1.copy(f);
                f.copy(o).add(t).increase();
                // f = o.add(t).add(BInt.ONE);
                // o = f1;
                o.copy(f1);
            } else {
                // o = o.add(t).add(BInt.ONE);
                o.add(t).increase();
            }
            // t = t.add(t).add(BInt.ONE);
            t.add(t).increase();
        }
        System.out.println(f.toString());
    }

    static class BInt {
        public int[] m = new int[L];
        public static int L = 330;
        private int h = L - 1;

        // public static BInt ONE = valueOf(1);

        public static BInt valueOf(int v) {
            BInt n = new BInt();
            n.m[L - 1] = v;
            return n;
        }

        public BInt add(BInt b) {
            for (int i = h < b.h ? h : b.h; i < L; i++) {
                m[i] += b.m[i];
            }
            // 进位
            for (int i = L - 1; i >= 1; i--) {
                if (m[i] >= 10) {
                    m[i - 1] += m[i] / 10;
                    m[i] %= 10;
                }
                if (m[i - 1] > 0)
                    h = i - 1;
            }
            return this;
        }

        // +1
        public BInt increase() {
            m[L - 1]++;
            if (m[L - 1] >= 10) {
                for (int i = L - 1; i >= 1; i--) {
                    if (m[i] >= 10) {
                        m[i - 1] += m[i] / 10;
                        m[i] %= 10;
                    } else
                        break;
                }
                if (m[h - 1] > 0)
                    h = h - 1;
            }
            return this;
        }

        public BInt copy(BInt b) {
            System.arraycopy(b.m, 0, m, 0, L);
            return this;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int j = h; j < L; j++)
                sb.append(m[j]);
            return sb.length() == 0 ? "0" : sb.toString();
        }

    }
}
