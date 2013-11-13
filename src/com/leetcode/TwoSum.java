package com.leetcode;

/**
 * Created by Administrator on 13-11-12.
 */
public class TwoSum {
    public int[] twoSum(int[] numbers, int target) {
        // IMPORTANT: Please reset any member data you declared, as
        // the same Solution instance will be reused for each test case.
        int[] result = new int[2];
        int[] sorted = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++)
            sorted[i] = i;
        int pivot = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[sorted[i]] < target)
                swap(sorted, i, pivot++);
        }
        quicksort(numbers, sorted, 0, pivot - 1);
        int left = 0, right = pivot - 1;
        while (true) {
            int sum = numbers[sorted[left]] + numbers[sorted[right]];
            if (sum == target) {
                result[0] = sorted[left] + 1;
                result[1] = sorted[right] + 1;
                break;
            }
            if (sum > target)
                right--;
            else
                left++;
        }
        if (result[0] > result[1])
            swap(result, 0, 1);
        return result;
    }

    private void quicksort(int[] numbers, int[] sorted, int left, int right) {
        if (left >= right) return;
        int pivot = (left + right) / 2;
        pivot = partition(numbers, sorted, left, right, pivot);
        quicksort(numbers, sorted, left, pivot - 1);
        quicksort(numbers, sorted, pivot + 1, right);
    }

    private int partition(int[] numbers, int[] sorted, int left, int right, int pivot) {
        int v = sorted[pivot];
        swap(sorted, pivot, right);
        pivot = left;
        for (int i = left; i < right; i++) {
            if (numbers[sorted[i]] < numbers[v])
                swap(sorted, i, pivot++);
        }
        swap(sorted, pivot, right);
        return pivot;
    }

    private void swap(int[] sorted, int i, int j) {
        int t = sorted[i];
        sorted[i] = sorted[j];
        sorted[j] = t;
    }

    public static void main(String[] args) {
        TwoSum twoSum = new TwoSum();
        int[] numbers = {2, 7, 11, 15,234,22,1343,2344};
        int target = 13;
        int[] result = twoSum.twoSum(numbers, target);
        System.out.println(result[0] + " " + result[1]);
    }
}
