import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * http://poj.org/problem?id=1273
 * Ford-Fulkerson 最大流
 * Created by wuyingqiang on 2017/11/11.
 */
public class Main {
    private static int N, M;
    private static final int MAX = 201;

    static class Edge {
        int s, e;
        int c, f;

        public Edge(int s, int e, int c, int f) {
            this.s = s;
            this.e = e;
            this.c = c;
            this.f = f;
        }
    }

    static class Graph {
        HashMap<String, Edge> edges = new HashMap<String, Edge>();
        HashMap<Integer, List<Edge>> nextNodes = new HashMap<Integer, List<Edge>>();
        HashMap<Integer, List<Edge>> preNodes = new HashMap<Integer, List<Edge>>();


        public void addEdge(Edge edge) {
            edges.put(edge.s + "" + edge.e, edge);
            List<Edge> list = nextNodes.get(edge.s);
            if (list == null) {
                list = new ArrayList<Edge>();
            }
            list.add(edge);
            nextNodes.put(edge.s, list);
            list = preNodes.get(edge.e);
            if (list == null) {
                list = new ArrayList<Edge>();
            }
            list.add(edge);
            preNodes.put(edge.e, list);
        }

        ArrayList<Integer> findArgumentPath() {
            LinkedList<ArrayList<Integer>> list = new LinkedList<ArrayList<Integer>>();
            ArrayList<Integer> start = new ArrayList<Integer>();
            start.add(1);
            list.addFirst(start);
            while (!list.isEmpty()) {
                ArrayList<Integer> path = list.pollFirst();
                int s = path.get(path.size() - 1);
                if (s == M) {
                    //find a path
                    return path;
                }
                List<Edge> edges = nextNodes.get(s);
                for (int i = 0; i < edges.size(); i++) {
                    Edge e = edges.get(i);
                    if (e.f < e.c && !path.contains(e.e)) {
                        ArrayList<Integer> copy = (ArrayList<Integer>) path.clone();
                        copy.add(e.e);
                        list.addLast(copy);
                    }
                }
                edges = preNodes.get(s);
                for (int i = 0; i < edges.size(); i++) {
                    Edge e = edges.get(i);
                    if (e.f > 0 && !path.contains(e.s)) {
                        ArrayList<Integer> copy = (ArrayList<Integer>) path.clone();
                        copy.add(e.s);
                        list.addLast(copy);
                    }
                }
            }
            return null;
        }

        void update(ArrayList<Integer> path) {
            int min = Integer.MAX_VALUE;
            for (int i = 1; i < path.size(); i++) {
                int s = path.get(i - 1);
                int e = path.get(i);
                Edge edge = edges.get(s + "" + e);
                min = Math.min(min, edge.c - edge.f);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader cin = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = cin.readLine();
            if (line == null || line.length() == 0) {
                break;
            }
            StringTokenizer tokenizer = new StringTokenizer(line);
            N = Integer.parseInt(tokenizer.nextToken());
            M = Integer.parseInt(tokenizer.nextToken());
            Graph g = new Graph();
            for (int i = 0; i < N; i++) {
                tokenizer = new StringTokenizer(cin.readLine());
                int s = Integer.parseInt(tokenizer.nextToken());
                int e = Integer.parseInt(tokenizer.nextToken());
                int c = Integer.parseInt(tokenizer.nextToken());
                g.addEdge(new Edge(s, e, c, 0));
            }
            solve(g);
            System.out.println();
        }
    }

    private static void solve(Graph g) {
        while (true) {
            ArrayList<Integer> path = g.findArgumentPath();
            if (path == null) {
                return;
            }
            g.update(path);
        }
    }
}
