package main

import "fmt"

func main() {
	p := make([][]float64, N*N)
	for i := 0; i < N*N; i++ {
		p[i] = P(i)
	}
	E := 0.0
	for i := 0; i < N*N; i++ {
		// 第i个格子为空的概率 = 所有flea都没有跳到这个格子的概率的乘积
		prob := 1.0
		for j := 0; j < N*N; j++ {
			prob *= (1 - p[j][i])
		}
		E += prob
	}
	fmt.Printf("%.6f\n", E)
}

var N = 30

// 4个方向
var dx = []int{-1, 1, 0, 0}
var dy = []int{0, 0, -1, 1}

// 第i个flea，经过50步落在每个格子上的概率
func P(i int) []float64 {
	dp := make([]float64, N*N)
	dp[i] = 1.0
	for range 50 {
		next := make([]float64, N*N)
		for j := 0; j < N*N; j++ {
			r, c := j/N, j%N
			for k := 0; k < 4; k++ {
				x := r + dx[k]
				y := c + dy[k]
				if x >= 0 && x < N && y >= 0 && y < N {
					next[x*N+y] += dp[j] * p(r, c)
				}
			}
		}
		dp = next
	}
	return dp
}

func p(x, y int) float64 {
	// 角落上
	if (x == 0 || x == N-1) && (y == 0 || y == N-1) {
		return 0.5
	}
	// 边上
	if x == 0 || x == N-1 || y == 0 || y == N-1 {
		return 1.0 / 3.0
	}
	return 0.25 // 中间的点
}
