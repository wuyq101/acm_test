package main

import "fmt"

/*
http://projecteuler.net/problem=149

*/

func main() {
	table = make([][]int, N)
	k := 1
	for i := 0; i < N; i++ {
		table[i] = make([]int, N)
		for j := 0; j < N; j++ {
			table[i][j] = lfg(k)
			k++
		}
	}
	fmt.Printf("s10 = %d\n", table[0][9])
	fmt.Printf("s100= %d\n", table[0][99])
	ans := F()
	fmt.Printf("ans = %d\n", ans)
}

var table [][]int

var M = 1000000
var N = 2000

// Lagged Fibonacci Generator
func lfg(k int) int {
	if 1 <= k && k <= 55 {
		return (100003-200003*k+300007*k*k*k)%M - 500000
	}
	// k-24在表中的下标对于在k-24-1上
	// k-55
	i0, j0 := loc(k - 24 - 1)
	i1, j1 := loc(k - 55 - 1)
	return (table[i0][j0]+table[i1][j1]+M)%M - 500000
}

func loc(k int) (int, int) {
	return k / N, k % N
}

// 从点(r,c)出发，按照x,y的方向，走到边界的一条路径上的最大和
func f(r, c, dx, dy int) int {
	v := 0
	sum := 0
	for r >= 0 && c >= 0 && r < N && c < N {
		sum += table[r][c]
		r += dx
		c += dy
		v = max(v, sum)
		sum = max(sum, 0)
	}
	return v
}

func F() int {
	ans := 0
	for i := 0; i < N; i++ {
		ans = max(ans, f(i, 0, 0, 1))    // 行
		ans = max(ans, f(0, i, 1, 0))    // 列
		ans = max(ans, f(i, 0, -1, 1))   // 斜率 -1 (左边缘出发，向右上)
		ans = max(ans, f(N-1, i, -1, 1)) // 斜率 -1 (底边缘出发，向右上)
		ans = max(ans, f(i, 0, 1, 1))    // 斜率 1  (左边缘出发，向右下)
		ans = max(ans, f(0, i, 1, 1))    // 斜率 1  (顶边缘出发，向右下)
	}
	return ans
}

func max(a, b int) int {
	if a > b {
		return a
	}
	return b
}
