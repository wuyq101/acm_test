package main

import "fmt"

func main() {
	MAX := 0
	for i := 51; i <= 100; i++ {
		MAX += i * i
	}
	fmt.Printf("MAX=%d\n", MAX)
	dp := make([][]int, 51)
	for i := 0; i <= 50; i++ {
		dp[i] = make([]int, MAX+1)
	}
	dp[0][0] = 1
	for x := 1; x <= 100; x++ {
		v := x * x
		for i := 50; i >= 1; i-- {
			for j := MAX; j >= v; j-- {
				pre := j - v
				if dp[i-1][pre] > 0 {
					dp[i][j] += dp[i-1][pre]
					if dp[i][j] > 2 {
						dp[i][j] = 2
					}
				}
			}
		}
	}
	sum := 0
	for j := 0; j <= MAX; j++ {
		if dp[50][j] == 1 {
			sum += j
		}
	}
	fmt.Printf("sum=%d\n", sum)
}
