package com.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 13-11-14.
 */
public class CloneGraph {
    private static class UndirectedGraphNode {
        int label;
        ArrayList<UndirectedGraphNode> neighbors;

        UndirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<UndirectedGraphNode>();
        }
    }

    public UndirectedGraphNode cloneGraph(UndirectedGraphNode root) {
        if (root == null)
            return null;
        Map<Integer, UndirectedGraphNode> copy = new HashMap<Integer, UndirectedGraphNode>();
        ArrayList<UndirectedGraphNode> unvisited = new ArrayList<UndirectedGraphNode>();
        unvisited.add(root);
        while (!unvisited.isEmpty()) {
            UndirectedGraphNode node = unvisited.remove(0);
            if (!copy.containsKey(node.label)) {
                copy.put(node.label, new UndirectedGraphNode(node.label));
            }
            for (int i = 0, len = node.neighbors.size(); i < len; i++) {
                UndirectedGraphNode child = node.neighbors.get(i);
                if (!copy.containsKey(child.label))
                    unvisited.add(child);
            }
        }
        //copy the neighbors
        Set<Integer> set = new HashSet<Integer>();
        unvisited.add(root);
        while (!unvisited.isEmpty()) {
            UndirectedGraphNode node = unvisited.remove(0);
            if (set.contains(node.label)) continue;
            UndirectedGraphNode clone = copy.get(node.label);
            for (int i = 0, len = node.neighbors.size(); i < len; i++) {
                int key = node.neighbors.get(i).label;
                clone.neighbors.add(copy.get(key));
            }
            set.add(node.label);
            for (int i = 0, len = node.neighbors.size(); i < len; i++) {
                UndirectedGraphNode child = node.neighbors.get(i);
                if (!set.contains(child.label))
                    unvisited.add(child);
            }
        }
        return copy.get(root.label);
    }

    public static void main(String[] args) {
        UndirectedGraphNode node = new UndirectedGraphNode(0);
        node.neighbors.add(node);
        CloneGraph test = new CloneGraph();
        test.cloneGraph(node);
    }
}
