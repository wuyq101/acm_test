package com.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by Administrator on 13-11-15.
 */
public class WordLadder {
    private static class Node {
        String val;
        int len;
        ArrayList<Node> children = new ArrayList<Node>();

        Node(String x) {
            val = x;
            len = Integer.MAX_VALUE;
        }
    }

    public ArrayList<ArrayList<String>> findLadders(String start, String end, HashSet<String> dict) {
        //check if there is no such path
        if (changeNext(start, dict).isEmpty() || changeNext(end, dict).isEmpty())
            return new ArrayList<ArrayList<String>>();
        //add the end to the dict
        dict.add(end);
        //build the graph;
        Map<String, Node> map = new HashMap<String, Node>();
        build_graph(map, start, end, dict);
        ArrayList<String> result = build_path(map, start, end);
        ArrayList<ArrayList<String>> answer = new ArrayList<ArrayList<String>>();
        for (String s : result) {
            String[] words = s.split(" ");
            ArrayList<String> e = new ArrayList<String>();
            for (String w : words) {
                e.add(w);
            }
            answer.add(e);
        }
        return answer;
    }

    private ArrayList<String> build_path(Map<String, Node> map, String start, String end) {
        Node e = map.get(end);
        if (e == null)
            return new ArrayList<String>();
        HashSet<String> dict = new HashSet<String>(map.keySet());
        ArrayList<String> result = new ArrayList<String>();
        path(result, end, map, dict, start, e);
        return result;
    }

    private void path(ArrayList<String> result, String post, Map<String, Node> map, HashSet<String> dict, String start, Node node) {
        if (node.val.equals(start)) {
            result.add(post);
            return;
        }
        ArrayList<String> pre = changeNext(node.val, dict);
        for (String word : pre) {
            Node w = map.get(word);
            if (w.len == node.len - 1) {
                post = word + " " + post;
                path(result, post, map, dict, start, w);
                post = post.substring(word.length() + 1);
            }
        }
    }

    private void build_graph(Map<String, Node> map, String start, String end, HashSet<String> dict) {
        Node s = new Node(start);
        s.len = 1;
        map.put(start, s);
        ArrayList<Node> visited = new ArrayList<Node>();
        visited.add(s);
        int best = -1;
        while (!visited.isEmpty()) {
            Node node = visited.remove(0);
            if (node.val.equals(end)) {
                if (best == -1)
                    best = node.len;
                continue;
            }
            if (best > 0 && node.len >= best) continue;
            ArrayList<String> next = changeNext(node.val, dict);
            for (String word : next) {
                if (map.containsKey(word)) continue;
                Node w = new Node(word);
                map.put(word, w);
                w.len = node.len + 1;
                visited.add(w);
            }
        }
    }

    private static ArrayList<String> changeNext(String start, HashSet<String> dict) {
        ArrayList<String> list = new ArrayList<String>();
        String pre, post, next;
        for (int i = 0; i < start.length(); i++) {
            pre = i > 0 ? start.substring(0, i) : "";
            post = i < start.length() - 1 ? start.substring(i + 1) : "";
            char ch = start.charAt(i);
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == ch) continue;
                next = pre + c + post;
                if (dict.contains(next))
                    list.add(next);
            }
        }
        return list;
    }

    public static void main(String[] args) {
        HashSet<String> dict = new HashSet<String>();
        dict.add("hot");
        dict.add("dot");
        dict.add("dog");
        dict.add("lot");
        dict.add("log");
        String start = "hit";
        String end = "cog";
        WordLadder test = new WordLadder();
        System.out.println(test.findLadders(start, end, dict));
    }

}
