package main

import "fmt"

/*
https://projecteuler.net/problem=189
轮廓线DP

针对每个点，有三个颜色，两个方向，可以有六种状态
一个三角形可以用2个二进制位表示
__, 最低两位表示颜色，01,10,11 red,blue,green


*/

func main() {
	dp := map[int]int64{0: 1}
	fmt.Printf("dp=%v\n", dp)
	N := 8
	mask := 1<<28 - 1
	for i := 0; i < N; i++ {
		for j := 0; j < 2*i+1; j++ {
			fmt.Printf("i=%d,j=%d\n", i, j)
			// j是偶数，三角形尖朝上，颜色只和前一个相关
			// j是奇数，三角形尖朝下, 颜色和前一个相关,以及上一行相邻的三角形有关
			next := make(map[int]int64)
			for state, cnt := range dp {
				for color := 1; color <= 3; color++ {
					// 比较和前一个三角形的颜色关系
					if j > 0 {
						pre := state & 3
						if color == pre {
							continue
						}
					}
					// 如果是三角形尖朝下，比较和上一行的三角形的颜色关系
					if j&1 == 1 {
						// 和上一行，刚好相差2i个三角形
						shift := (2*i - 1) * 2
						pre := (state >> shift) & 3
						if pre == color {
							continue
						}
					}
					// i,j这个位置可以使用color颜色
					ns := (state<<2 | color) & mask
					next[ns] += cnt
				}
			}
			dp = next
		}
	}
	fmt.Printf("len=%v\n", len(dp))
	total := int64(0)
	for _, cnt := range dp {
		total += cnt
	}
	fmt.Printf("total=%d\n", total)

}
