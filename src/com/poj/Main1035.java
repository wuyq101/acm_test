package com.poj;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * 1035
 * 
 * @author wyq
 * @version 1.0
 */
public class Main1035 {
    private static List<String> dict = new ArrayList<String>();
    private static Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();

    public static void main(String[] args) {
        Scanner cin = new Scanner(new BufferedInputStream(System.in));
        // 读取词典
        while (cin.hasNext()) {
            String line = cin.next();
            if ("#".equals(line)) {
                break;
            }
            dict.add(line);
            int len = line.length();
            if (!map.containsKey(len)) {
                map.put(len, new ArrayList<String>());
            }
            map.get(len).add(line);
        }
        // 读取单词
        while (cin.hasNext()) {
            String word = cin.next();
            if ("#".equals(word)) {
                return;
            }
            check(word);
        }
    }

    private static void check(String word) {
        int len = word.length();
        if (map.get(len).contains(word)) {
            System.out.printf("%s is correct\n", word);
            return;
        }
        System.out.printf("%s:", word);
        List<String> result = new ArrayList<String>();
        for (int k = -1; k <= 1; k++) {
            List<String> list = map.get(len + k);
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (isValid(word, list.get(i))) {
                        result.add(list.get(i));
                    }
                }
            }
        }
        // 按照原来顺序排列输出
        for (int i = 0; i < dict.size() && result.size() > 0; i++) {
            if (result.contains(dict.get(i))) {
                System.out.printf(" %s", dict.get(i));
                result.remove(dict.get(i));
            }
        }
        System.out.printf("\n");
    }

    private static boolean canReplace(String a, String b) {
        int len1 = a.length();
        int count = 0;
        for (int i = 0; i < len1; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                count++;
            }
            if (count > 1) {
                return false;
            }
        }
        return count == 1;
    }

    private static boolean canDelete(String a, String b) {
        int len1 = a.length();
        for (int i = 0; i < len1; i++) {
            if (a.charAt(i) == b.charAt(i)) {
                continue;
            } else {
                // 有一个不相同的字母，删除该字母，然后比较是否相同
                for (int j = i; j < len1; j++) {
                    if (a.charAt(j) != b.charAt(j + 1)) {
                        return false;
                    }
                }
                return true;
            }
        }
        return true;
    }

    // 是否可以通过增，删或者改动一个字母变得相同
    private static boolean isValid(String a, String b) {
        int len1 = a.length();
        int len2 = b.length();
        // 变动a中的一个字母
        if (len1 == len2) {
            return canReplace(a, b);
        }
        if (len1 < len2) {
            // a添加一个字母--->删除b中的一个字母
            return canDelete(a, b);
        } else {
            // 删除a中的一个字母
            return canDelete(b, a);
        }
    }

}
