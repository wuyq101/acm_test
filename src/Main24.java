import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 24点游戏
 * Created by wuyq on 15/10/24.
 */
public class Main24 {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (true) {
            int a = cin.nextInt();
            int b = cin.nextInt();
            int c = cin.nextInt();
            int d = cin.nextInt();
            List<Integer> list = new ArrayList<Integer>();
            list.add(a);
            list.add(b);
            list.add(c);
            list.add(d);
            List<String> ex = new ArrayList<String>();
            ex.add(String.valueOf(a));
            ex.add(String.valueOf(b));
            ex.add(String.valueOf(c));
            ex.add(String.valueOf(d));
            boolean r = dfs(list, ex);
            System.out.println(r);
        }
    }

    private static boolean dfs(List<Integer> list, List<String> ex) {
        if (list.size() == 1) {
            if (list.get(0) == 24) {
                System.out.println(ex.get(0));
                return true;
            }
            return false;
        }
        for (int i = 0; i < list.size(); i++) {
            int a = list.get(i);
            String ea = ex.get(i);
            for (int j = i + 1; j < list.size(); j++) {
                int b = list.get(j);
                String eb = ex.get(j);
                // +
                List<Integer> t = new ArrayList<Integer>(list);
                t.remove(j);
                t.remove(i);
                t.add(a + b);
                List<String> e = new ArrayList<String>(ex);
                e.remove(j);
                e.remove(i);
                e.add(ea + "+" + eb);
                if (dfs(t, e)) {
                    return true;
                }
                // -
                t = new ArrayList<Integer>(list);
                t.remove(j);
                t.remove(i);
                e = new ArrayList<String>(ex);
                e.remove(j);
                e.remove(i);
                if (a >= b) {
                    t.add(a - b);
                    if (eb.contains("+") || eb.contains("-")) {
                        e.add(ea + "-(" + eb + ")");
                    } else {
                        e.add(ea + "-" + eb);
                    }
                } else {
                    t.add(b - a);
                    if (ea.contains("+") || ea.contains("-")) {
                        e.add(eb + "-(" + ea + ")");
                    } else {
                        e.add(eb + "-" + ea);
                    }
                }
                if (dfs(t, e)) {
                    return true;
                }
                // *
                t = new ArrayList<Integer>(list);
                t.remove(j);
                t.remove(i);
                t.add(a * b);
                e = new ArrayList<String>(ex);
                e.remove(j);
                e.remove(i);
                String s = "";
                if (ea.contains("+") || ea.contains("-")) {
                    s += "(" + ea + ")*";
                } else {
                    s += ea + "*";
                }
                if (eb.contains("+") || eb.contains("-")) {
                    s += "(" + eb + ")";
                } else {
                    s += eb;
                }
                e.add(s);
                if (dfs(t, e)) {
                    return true;
                }
                // /
                t = new ArrayList<Integer>(list);
                t.remove(j);
                t.remove(i);
                e = new ArrayList<String>(ex);
                e.remove(j);
                e.remove(i);
                if (a >= b && b != 0 && a % b == 0) {
                    t.add(a / b);
                    s = "";
                    if (ea.contains("+") || ea.contains("-")) {
                        s += "(" + ea + ")/";
                    } else {
                        s += ea + "/";
                    }
                    if (eb.contains("+") || eb.contains("-")) {
                        s += "(" + eb + ")";
                    } else {
                        s += eb;
                    }
                    e.add(s);
                    if (dfs(t, e)) {
                        return true;
                    }
                } else if (b > a && a != 0 && b % a == 0) {
                    t.add(b / a);
                    s = "";
                    if (eb.contains("+") || eb.contains("-")) {
                        s += "(" + eb + ")/";
                    } else {
                        s += eb + "/";
                    }
                    if (ea.contains("+") || ea.contains("-")) {
                        s += "(" + ea + ")";
                    } else {
                        s += ea;
                    }
                    e.add(s);
                    if (dfs(t, e)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
