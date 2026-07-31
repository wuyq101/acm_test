package main

import "fmt"

/*
n * m (n行m列)
轮廓线DP，在每个格子(r,c)往后2m+1个格子的01状态
2m+1是因为3*1放的时候，最远的格子(r+2,c)举例现在(r,c)刚好是2m+1

*/

func main() {
	n, m := 9, 2
	shapes := []int{
		// 1*3 (r,c) (r,c+1) (r,c+2)
		0x7,
		// 3*1 (r,c) (r+1,c) (r+2,c)
		1 | (1 << m) | (1 << (2 * m)),
		// L, (r,c) (r+1,c) (r+1,c+1)
		1 | (1 << m) | (1 << (m + 1)),
		// L, (r,c) (r+1,c) (r+1,c-1)
		1 | (1 << m) | (1 << (m - 1)),
		// ¬, (r,c) (r,c+1) (r+1,c+1)
		3 | (1 << (m + 1)),
		// ¬, (r,c) (r,c+1) (r+1,c)
		3 | (1 << m),
	}
	dp := make(map[int]int64)
	dp[0] = 1
	for r := 0; r < n; r++ {
		for c := 0; c < m; c++ {
			next := make(map[int]int64)
			for mask, cnt := range dp {
				if mask&1 == 1 {
					// 当前位置已经被占据，跳过
					next_mask := mask >> 1
					next[next_mask] += cnt
					continue
				}
				// 当前位置可以使用
				// 1. 1*3 (r,c) (r,c+1) (r,c+2)
				if c+2 < m && mask&shapes[0] == 0 {
					next_mask := (mask | shapes[0]) >> 1
					next[next_mask] += cnt
				}
				// 2. 1*3 (r,c) (r+1,c) (r+2,c)
				if r+2 < n && mask&shapes[1] == 0 {
					next_mask := (mask | shapes[1]) >> 1
					next[next_mask] += cnt
				}
				// 3. L, (r,c) (r+1,c) (r+1,c+1)
				if r+1 < n && c+1 < m && mask&shapes[2] == 0 {
					next_mask := (mask | shapes[2]) >> 1
					next[next_mask] += cnt
				}
				// 4. L, (r,c) (r+1,c) (r+1,c-1)
				if r+1 < n && c-1 >= 0 && mask&shapes[3] == 0 {
					next_mask := (mask | shapes[3]) >> 1
					next[next_mask] += cnt
				}
				// 5. ¬, (r,c) (r,c+1) (r+1,c+1)
				if r+1 < n && c+1 < m && mask&shapes[4] == 0 {
					next_mask := (mask | shapes[4]) >> 1
					next[next_mask] += cnt
				}
				// 6. ¬, (r,c) (r,c+1) (r+1,c)
				if r+1 < n && c+1 < m && mask&shapes[5] == 0 {
					next_mask := (mask | shapes[5]) >> 1
					next[next_mask] += cnt
				}
			}
			fmt.Printf("r=%d, c=%d, next-len: %d\n", r, c, len(next))
			dp = next
		}
	}
	fmt.Printf("total = %d\n", dp[0])
}
