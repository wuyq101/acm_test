package main

import "fmt"

/**
An array nums of length n is beautiful if:

nums is a permutation of the integers in the range [1, n].
For every 0 <= i < j < n, there is no index k with i < k < j where 2 * nums[k] == nums[i] + nums[j].
Given the integer n, return any beautiful array nums of length n. There will be at least one valid answer for the given n.



Example 1:

Input: n = 4
Output: [2,1,4,3]
Example 2:

Input: n = 5
Output: [3,1,2,5,4]
*/
func main() {
	for i := 1; i <= 100; i++ {
		result := beautifulArray(i)
		fmt.Printf("%d %v\n", i, result)
	}
}

// 用n-1对应的序列生成n的序列，
// 先生成对应的基数部分，然后生成对应的偶数部分
// [2k-1...] + [2k]
// n=1
// --> [1]
// n=2
// --> [1,2]
// n=3
// --> [1,3] + [2] = [1, 3, 2]
// n=4
// --> [1,3] + [2, 4] = [1, 3, 2, 4]
// n=5
// --> [1, 5, 3] + [2, 4] = [1,5,3,2,4]
func beautifulArray(n int) []int {
	result := make([]int, 0, n)
	result = append(result, 1)
	for len(result) < n {
		odd := make([]int, 0)
		even := make([]int, 0)
		for _, i := range result {
			v := 2*i - 1
			if v <= n {
				odd = append(odd, v)
			}
			v = v + 1
			if v <= n {
				even = append(even, v)
			}
		}
		result = append(odd, even...)
	}

	return result
}
