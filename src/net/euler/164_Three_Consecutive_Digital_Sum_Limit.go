package main

import "fmt"

/*
https://projecteuler.net/problem=164
20位数字，首位不为0，且没有三个连续数字和大于9. 求一共有多少个?

dp[i][state]  为长度为i，最后两位数字
*/

func main() {
	dp := make(map[int]int)
	dp[0] = 1
	for i := 0; i < 20; i++ {
		next := make(map[int]int)
		for pre, cnt := range dp {
			for j := 0; j <= 9; j++ {
				if i == 0 && j == 0 {
					continue
				}
				sum := pre/10 + pre%10
				if sum+j > 9 {
					continue
				}
				n := (pre%10)*10 + j
				next[n] += cnt
			}
		}
		dp = next
	}
	total := 0
	for sum, cnt := range dp {
		fmt.Printf("%02d %d\n", sum, cnt)
		total += cnt
	}
	fmt.Printf("total = %d, size=%d\n", total, len(dp))

}
