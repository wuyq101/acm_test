package main

import "fmt"

/*
https://projecteuler.net/problem=181

本质上是一个背包问题
物品(B^i,W^j)组合代表 i个B和j个W, i从0到n,j从0到m， i,j不能同时为0
*/
func main() {
	N, M := 60, 40
	dp := [61][41]int64{}
	dp[0][0] = 1
	for i := 0; i <= N; i++ {
		for j := 0; j <= M; j++ {
			if i == 0 && j == 0 {
				continue
			}
			// 针对(i,j)组合成的物品
			for b := i; b <= N; b++ {
				for w := j; w <= M; w++ {
					dp[b][w] += dp[b-i][w-j]
				}
			}
		}
	}
	fmt.Printf("%d\n", dp[N][M])
}
