package main

import "fmt"

/*

dp[i][j][state] 表示长度是i，最后一位是j，状态是state的个数， 状态10位的二进制数，代表每个数字是否已经有了

state : 0---> 1024

dp[0][0][0] = 1

*/

func main() {
	// dp[最后一位数字][状态]
	var dp [10][1024]int64

	// 初始化长度为 1 的情况 (不能以 0 开头)
	for d := 1; d <= 9; d++ {
		dp[d][1<<d] = 1
	}

	totalSum := int64(0)

	// 长度从 2 迭代到 40 (因为长度为 1 的已经初始化了)
	for i := 2; i <= 40; i++ {
		var next [10][1024]int64

		// 遍历所有可能的最后一个数字
		for d := 0; d <= 9; d++ {
			// 遍历所有可能的状态
			for state := 0; state < 1024; state++ {
				// 从 d-1 转移过来
				if d-1 >= 0 && dp[d-1][state] > 0 {
					next[d][state|(1<<d)] += dp[d-1][state]
				}
				// 从 d+1 转移过来
				if d+1 <= 9 && dp[d+1][state] > 0 {
					next[d][state|(1<<d)] += dp[d+1][state]
				}
			}
		}
		dp = next

		for d := 0; d <= 9; d++ {
			totalSum += dp[d][1023]
		}
	}

	fmt.Printf("sum = %d\n", totalSum)
}
