package com.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 13-11-13.
 */
public class LRUCache {
    private int capacity, size;
    private Node head, tail;
    private Map<Integer, Node> hash;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        size = 0;
        head = new Node();
        tail = new Node();
        hash = new HashMap<Integer, Node>();
    }

    public int get(int key) {
        Node node = hash.get(key);
        if (node != null) {
            //move the node to the first place
            if (node.pre != head) {
                node.pre.next = node.next;
                node.next.pre = node.pre;
                node.next = head.next;
                head.next.pre = node;
                head.next = node;
                node.pre = head;
            }
            return node.value;

        }
        return -1;
    }

    public void set(int key, int value) {
        //exist key
        Node node = hash.get(key);
        if (node != null) {
            node.value = value;
            //move the node to the first place
            if (node.pre != head) {
                node.pre.next = node.next;
                node.next.pre = node.pre;
                head.next.pre = node;
                node.next = head.next;
                head.next = node;
                node.pre = head;
            }
            return;
        }
        //need to add a new key. check the size
        if (size == capacity) {
            //use the last one
            node = tail.pre;
            hash.remove(node.key);
            node.key = key;
            node.value = value;
            hash.put(key, node);
            //move this node to the first place
            if (node.pre != head) {
                node.pre.next = tail;
                tail.pre = node.pre;
                head.next.pre = node;
                node.next = head.next;
                head.next = node;
                node.pre = head;
            }
            return;
        }
        node = new Node();
        node.key = key;
        node.value = value;
        if (size == 0) {
            node.next = tail;
            tail.pre = node;
            node.pre = head;
            head.next = node;
        } else {
            //add a new node to the first place
            head.next.pre = node;
            node.next = head.next;
            head.next = node;
            node.pre = head;
        }
        hash.put(key, node);
        size++;
    }

    private static class Node {
        Node pre, next;
        int key, value;
    }

    private static LRUCache cache = new LRUCache(2);

    private static void _set(int key, int value) {
        cache.set(key, value);
    }

    private static void _get(int key) {
        System.out.println(cache.get(key));
    }

    public static void main(String[] args) {
        _set(2, 1);
        _set(1, 1);
        _set(2, 3);
        _set(4, 1);
        _get(1);
        _get(2);
    }
}
