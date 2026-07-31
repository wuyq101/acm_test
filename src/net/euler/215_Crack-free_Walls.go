package main

import "fmt"

/*

https://projecteuler.net/problem=215
N行 M列的墙，
使用2*1，3*1砖，要求连续两行直接没有对齐的缝隙，求有多少种方法

W(32,10)

M <= 32,

状态中0-1分界的地方为1
不分界的地方为0

使用轮廓线dp。保持最后的M位
放入2*1, 01
放入3*1，001

*/

func main() {
	N := 10
	M := 32
	dp := make(map[int]int64)
	dp[1] = 1
	next := make(map[int]int64)
	MASK := (1 << M) - 1
	H := 1 << (M - 1)
	for i := 0; i < N; i++ {
		for j := 0; j < M; j++ {
			//	fmt.Printf("i=%d,j=%d,dp=%v\n", i, j, dp)
			clear(next)
			for state, cnt := range dp {
				//		fmt.Printf("i=%d,j=%d,state=%09b cnt=%d\n", i, j, state, cnt)
				h := state&H == 0 || j == M-1
				// 0 --> 00,01,001
				if state&1 == 0 {
					if state&3 == 0 {
						if h {
							// 00 --> 001
							s := state << 1 & MASK
							s |= 1
							next[s] += cnt
						}
						continue
					}
					// 0 --> 00
					s := state << 1 & MASK
					if j != M-1 {
						next[s] += cnt
					}
					if h {
						// 0 --> 01
						s := state << 1 & MASK
						s |= 1
						next[s] += cnt
					}
				} else {
					// 1 --> 10
					s := state << 1 & MASK
					if j != M-1 {
						next[s] += cnt
					}
				}
			}
			dp, next = next, dp
		}
	}
	//	fmt.Printf("len(dp)=%d\n", dp)
	sum := int64(0)
	for _, cnt := range dp {
		//	fmt.Printf("state=%032b cnt=%d\n", state, cnt)
		sum += cnt
	}
	fmt.Printf("sum=%d\n", sum)
}
