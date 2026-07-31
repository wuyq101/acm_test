package main

import "fmt"

/*

172. Few Repeated Digits

https://projecteuler.net/problem=172

dp[i][j] 长度为i，状态是j的个数有多少，j是10位的4进制数, 每位0，1，2，3代表数字k，当前使用的情况

10位的4进制数，用20位的二进制数来表示
比如数字k，占用2k,2k+1这两位
0: 0 1
1: 2 3
2: 4 5
3: 6 7
...
9: 18 19

dp[0][0] = 1

*/

func main() {
	dp := map[int]int64{0: 1}
	for i := 1; i <= 18; i++ {
		next := make(map[int]int64)
		for mask, cnt := range dp {
			for k := 0; k <= 9; k++ {
				if i == 1 && k == 0 {
					continue
				}
				//	fmt.Printf("mask = %d, cnt = %d, k = %d\n", mask, cnt, k)
				// 数字k对应的掩码
				v := mask >> (k * 2) & 3
				if v == 3 {
					// 已经使用3次了，跳过
					continue
				}
				nm := mask + (1 << (k * 2))
				next[nm] += cnt
			}
		}
		dp = next
		fmt.Printf("i=%d dp size = %d\n", i, len(dp))
	}
	fmt.Printf("dp size = %d\n", len(dp))
	sum := int64(0)
	for _, cnt := range dp {
		sum += cnt
	}
	fmt.Printf("sum = %d\n", sum)
}
