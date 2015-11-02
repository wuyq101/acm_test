import java.util.*;


/**
 * An orienteering map is to be given in the following format.
 * <p/>
 * ########
 * #@....G#
 * ##.##@##
 * #..@..S#
 * #@.....#
 * ########
 * Calculate the minimum distance from the start(S) to the goal(G) with passing all the checkpoints(@).
 * <p/>
 * ‘.’ means an opened-block that players can pass.
 * ‘#’ means a closed-block that players cannot pass.
 * It is allowed to move only by one step vertically or horizontally.
 * 1 <= width <= 100, 1 <= height <= 100
 * The maximum number of checkpoints is 18.
 * Return -1 if given arguments do not satisfy specifications, or players cannot arrive at the goal from the start by passing all the checkpoints.
 * Created by wuyq on 15/11/1.
 */
public class MainMaze {
    private static int w, h;
    private static char[][] map;
    private static int[][] dist;
    private static int start, end;
    private static List<Integer> points;
    private static int[] dx = {-1, 0, 1, 0};
    private static int[] dy = {0, -1, 0, 1};
    private static int min;

    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        w = cin.nextInt();
        h = cin.nextInt();
        map = new char[h][w];
        dist = new int[h][w];
        for (int i = 0; i < h; i++) {
            Arrays.fill(dist[i], -1);
            String line = cin.next();
            for (int j = 0; j < w; j++) {
                map[i][j] = line.charAt(j);
            }
        }
        solve();
    }

    private static void solve() {
        points = new LinkedList<Integer>();
        //扫描一遍记录开始点和结束点，以及所有的check point
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (map[i][j] == 'S') {
                    start = i * w + j;
                }
                if (map[i][j] == 'G') {
                    end = i * w + j;
                }
                if (map[i][j] == '@') {
                    points.add(i * w + j);
                }
            }
        }
        //从开始点出发，flood一遍，计算到所有点的最短距离
        int j = start % w;
        int i = (start - j) / w;
        flood(i, j, 0);
        for (i = 0; i < h; i++) {
            for (j = 0; j < w; j++) {
                System.out.print(dist[i][j] + "\t");
            }
            System.out.println();
        }
        //检查所有的check point最短记录，和结束点，是否能到达。
        //如果有一个点不能到达，则返回-1
        int max = 0;
        for (int k = 0; k < points.size(); k++) {
            int p = points.get(k);
            j = p % w;
            i = (p - j) / w;
            if (dist[i][j] == -1) {
                System.out.println("impossible");
                return;
            }
            max += 2 * dist[i][j];
        }
        j = end % w;
        i = (end - j) / w;
        if (dist[i][j] == -1) {
            System.out.println("impossible");
            return;
        }
        max += 2 * dist[i][j];
        System.out.println(max);
        //依次从S出发到达每个检查点，然后回来，最后去G点，所需要的步数，作为bfs的一个剪枝条件
        min = max;
        //从S开始做bfs，求最短的步数
        j = start % w;
        i = (start - j) / w;
        Node minNode = null;
        Node node = new Node();
        node.points = new HashSet<Integer>(points);
        node.d = 0;
        node.j = j;
        node.i = i;
        int endj = end % w;
        int endi = (end - endj) / w;
        LinkedList<Node> nodes = new LinkedList<Node>();
        nodes.add(node);
        while (!nodes.isEmpty()) {
            //System.out.println("list size " + nodes.size() + " current min " + min);
            node = nodes.removeFirst();
            //System.out.println(node);
            //已经走到最后了
            if ((node.points == null || node.points.size() == 0) && (node.i == endi && node.j == endj)) {
                if (node.d < min) {
                    min = node.d;
                    minNode = node;
                }
                continue;
            }
            //如果已经超过当前最有解
            if (node.d >= min) {
                continue;
            }
            //四个方向，走一步
            for (int k = 0; k < 4; k++) {
                i = node.i + dy[k];
                j = node.j + dx[k];
                if (i < 0 || i >= h || j < 0 || j >= w) continue;
                if (map[i][j] == '#') continue;
                Node temp = new Node();
                temp.points = new HashSet<Integer>(node.points);
                if (map[i][j] == '@') {
                    temp.points.remove(i * w + j);
                }
                temp.d = node.d + 1;
                temp.i = i;
                temp.j = j;
                temp.pre = node;
                //检查当前队列中是否已经有此位置的，并且距离比现在的小，同时point的个数也是一样的
                boolean flag = true;
                for (i = 0; i < nodes.size(); i++) {
                    Node n = nodes.get(i);
                    if (n.d <= temp.d && n.i == temp.i && n.j == temp.j && n.points.equals(temp.points)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    nodes.add(temp);
                } else {
                    //System.out.println("do not add node " + temp);
                }
            }
        }
        System.out.println(min);
        Node cur = minNode;
        while (cur != null) {
            System.out.println("cur --> " + cur.i + " " + cur.j);
            cur = cur.pre;
        }
    }

    static class Node {
        public Node() {
        }

        Set<Integer> points;//当前还剩余未访问的检查点
        int d; //当前走的步数
        int i;
        int j;
        Node pre;

        public String toString() {
            return d + " " + i + " " + j + " " + points;
        }
    }

    private static void flood(int i, int j, int d) {
        if (i < 0 || i >= h || j < 0 || j >= w) return;
        if (map[i][j] == '#') return;
        if (dist[i][j] >= 0 && d >= dist[i][j]) return;
        dist[i][j] = d;
        for (int k = 0; k < 4; k++) {
            flood(i + dy[k], j + dx[k], d + 1);
        }
    }
}
