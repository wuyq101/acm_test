package com.leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 13-12-11.
 */
public class Gray {
    private static Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();

    public ArrayList<Integer> grayCode(int n) {
        ArrayList<Integer> list = map.get(n);
        if (list != null)
            return list;
        list = new ArrayList<Integer>();
        if (n == 0) {
            list.add(0);
            return list;
        }
        if (n == 1) {
            list = new ArrayList<Integer>();
            list.add(0);
            list.add(1);
            map.put(1, list);
            return list;
        }
        ArrayList<Integer> pre = grayCode(n - 1);
        for (int i = 0; i < pre.size(); i++) {
            list.add(pre.get(i));
        }
        for (int i = pre.size() - 1; i >= 0; i--) {
            list.add((1 << (n - 1)) | pre.get(i));
        }
        map.put(n, list);
        return list;
    }

    public static void main(String[] args) {
        Gray g = new Gray();
        System.out.println(g.grayCode(4));
    }
}
