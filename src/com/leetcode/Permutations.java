package com.leetcode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Administrator on 13-12-22.
 */
public class Permutations {
    public ArrayList<ArrayList<Integer>> permuteUnique(int[] num) {
        ArrayList<ArrayList<Integer>> lists = new ArrayList<ArrayList<Integer>>();
        if (num == null || num.length == 0) return lists;
        generate(lists, num, 0);
        return lists;
    }

    private void generate(ArrayList<ArrayList<Integer>> lists, int[] num, int start) {
        if (start == num.length) {
            //one permute
            lists.add(makeList(num));
            return;
        }
        for (int i = start; i < num.length; i++) {
            //check if num[i] is duplicate ...
            boolean flag = false;
            if (i > start) {
                for (int j = i - 1; j >= start; j--) {
                    if (num[j] == num[i]) {
                        flag = true;
                        break;
                    }
                }
            }
            if (flag) continue;
            swap(num, i, start);
            generate(lists, num, start + 1);
            swap(num, i, start);
        }
    }


    private ArrayList<Integer> makeList(int[] num) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < num.length; i++) {
            list.add(num[i]);
        }
        return list;
    }

    public String getPermutation(int n, int k) {
        int[] num = new int[n];
        for (int i = 0; i < num.length; i++)
            num[i] = i + 1;
        String result = getPermutation(num, 0, k);
        return result;
    }

    private String getPermutation(int[] num, int start, int k) {
        if (k == 1) {
            return makeString(num);
        }
        if (k == 2) {
            swap(num, num.length - 2, num.length - 1);
            return makeString(num);
        }
        int left = num.length - start;
        int cnt = factorial(left - 1);
        if (k <= cnt) {
            return getPermutation(num, start + 1, k);
        } else {
            int i = k / cnt;
            if (k % cnt == 0) i--;
            swap(num, start, start + i);
            Arrays.sort(num, start + 1, num.length);
            return getPermutation(num, start + 1, k - cnt * i);
        }
    }


    private String makeString(int[] num) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num.length; i++) {
            sb.append(num[i]);
        }
        return sb.toString();
    }

    private static int[] f = new int[10];

    private int factorial(int i) {
        if (f[i] == 0) {
            f[0] = 1;
            for (int k = 1; k <= 9; k++)
                f[k] = f[k - 1] * k;
        }
        return f[i];
    }

    public void prevPermutation(int[] num) {
        if (num.length == 0 || num.length == 1) return;
        if (num.length == 2) {
            swap(num, 0, 1);
            return;
        }
        //首先，从最尾端开始向前寻找两个相邻的元素，令第一个元素为i,第二个元素为ii,且满足i>ii
        //然后，从最尾端开始往前寻找第一个小于i的元素，令它为j
        //然后，将i和j对调，再将ii及其之后的所有元素反转
        int i = num.length - 2, ii = num.length - 1;
        while (i >= 0 && num[i] <= num[ii]) {
            i--;
            ii--;
        }
        if (i == -1) {
            reverse(num, 0, num.length - 1);
            return;
        }
        int j = num.length - 1;
        for (int k = num.length - 1; k >= 0; k--) {
            if (num[k] < num[i]) {
                j = k;
                break;
            }
        }
        swap(num, i, j);
        reverse(num, ii, num.length - 1);
    }

    public void nextPermutation(int[] num) {
        if (num.length == 0 || num.length == 1) return;
        if (num.length == 2) {
            swap(num, 0, 1);
            return;
        }
        //首先，从最尾端开始往前寻找两个相邻的元素，令第一个元素是i, 第二个元素是ii,且满足i<ii；
        //然后，再从最尾端开始往前搜索，找出第一个大于i的元素，设其为j；
        //然后，将i和j对调，再将ii及其后面的所有元素反转。
        int i = num.length - 2, ii = num.length - 1;
        while (i >= 0 && num[i] >= num[ii]) {
            i--;
            ii--;
        }
        if (i == -1) {
            reverse(num, 0, num.length - 1);
            return;
        }
        int j = num.length - 1;
        for (int k = num.length - 1; k >= 0; k--) {
            if (num[k] > num[i]) {
                j = k;
                break;
            }
        }
        swap(num, i, j);
        reverse(num, ii, num.length - 1);
    }

    private void swap(int[] num, int i, int j) {
        int t = num[i];
        num[i] = num[j];
        num[j] = t;
    }

    private void reverse(int[] num, int i, int j) {
        while (i < j) {
            swap(num, i++, j--);
        }
    }


    public static void main(String[] args) {
        int[] num = {5, 1, 1};
        Permutations test = new Permutations();
        test.prevPermutation(num);
        print(num);
    }

    private static void print(int[] num) {
        for (int i = 0; i < num.length; i++) {
            System.out.printf("%d ", num[i]);
        }
        System.out.println();
    }
}