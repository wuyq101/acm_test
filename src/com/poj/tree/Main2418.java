package com.poj.tree;
import java.io.BufferedInputStream;
import java.util.Formatter;
import java.util.Scanner;

/**
 * 2418 AVL树
 * 
 * @author wuyq101
 * @version 1.0
 */
public class Main2418 {
    private static Node root;
    private static int total;
    private static StringBuilder sb = new StringBuilder();
    private static Formatter formatter = new Formatter(sb);

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        while (cin.hasNextLine()) {
            root = insert(root, cin.nextLine());
            total++;
        }
        print(root);
        System.out.println(sb.toString());
    }

    private static void print(Node rt) {
        if (rt.left != null)
            print(rt.left);
        formatter.format("%s %.4f\n", rt.species, rt.cnt * 100.0 / total);
        if (rt.right != null)
            print(rt.right);
    }

    private static Node insert(Node root, String species) {
        if (root == null) {
            root = new Node();
            root.species = species;
            root.cnt = 1;
            return root;
        }
        int cmp = species.compareTo(root.species);
        if (cmp == 0) {
            root.cnt++;
            return root;
        }
        if (cmp < 0) {
            root.left = insert(root.left, species);
            if (height(root.left) - height(root.right) == 2) {
                root = species.compareTo(root.left.species) < 0 ? LL(root) : LR(root);
            }
        } else {
            root.right = insert(root.right, species);
            if (height(root.left) - height(root.right) == -2) {
                root = species.compareTo(root.right.species) < 0 ? RL(root) : RR(root);
            }
        }
        root.height = Math.max(height(root.left), height(root.right)) + 1;
        return root;
    }

    private static int height(Node node) {
        return node == null ? -1 : node.height;
    }

    // 右旋, left-left case
    private static Node LL(Node p) {
        Node root = p.left;
        p.left = root.right;
        root.right = p;
        p.height = Math.max(height(p.left), height(p.right)) + 1;
        root.height = Math.max(height(root.left), p.height) + 1;
        return root;
    }

    // 双右旋 left-right case
    private static Node LR(Node p) {
        // 先左旋，然后右旋
        Node left = p.left;
        Node root = left.right;
        // 左旋
        left.right = root.left;
        root.left = left;
        // p.left = root;
        // 右旋
        p.left = root.right;
        root.right = p;
        // 重新计算高度
        left.height = Math.max(height(left.left), height(left.right)) + 1;
        p.height = Math.max(height(p.left), height(p.right)) + 1;
        root.height = Math.max(left.height, p.height) + 1;
        return root;
    }

    private static Node RL(Node p) {
        // 先右旋，然后左旋
        Node right = p.right;
        Node root = right.left;
        // 右旋
        right.left = root.right;
        root.right = right;
        // 左旋
        p.right = root.left;
        root.left = p;
        p.height = Math.max(height(p.left), height(p.right)) + 1;
        right.height = Math.max(height(right.left), height(right.right)) + 1;
        root.height = Math.max(p.height, right.height) + 1;
        return root;
    }

    // 左旋，right-right case
    private static Node RR(Node p) {
        Node root = p.right;
        p.right = root.left;
        root.left = p;
        p.height = Math.max(height(p.left), height(p.right)) + 1;
        root.height = Math.max(height(root.right), p.height) + 1;
        return root;
    }

    static class Node {
        public int cnt;
        public String species;
        public Node left;
        public Node right;
        public int height;
    }
}
