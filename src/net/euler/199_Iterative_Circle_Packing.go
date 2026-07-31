package main

import (
	"fmt"
	"math"
)

/*
https://projecteuler.net/problem=199
笛卡尔定理
平面上四个圆两两相切，曲率k = 1/r
外切时，k为1/r，内切时，k为-1/r
曲率满足：

2(k1^2+k2^2+k3^2+k4^2) = (k1+k2+k3+k4)^2

一开始的三个小圆曲率为k

2*(k*k*3+1) = (3k-1)^2
6k^2 + 2 = 9k^2 - 6k + 1
3k^2 - 6k - 1 = 0
∆ = 36-4*3*(-1) = 48
k = 6+4√3/6 = 1+2/√3




k4 = k1 + k2 + k3 + 2 * √(k1 * k2 + k2 * k3 + k3 * k1)



*/

func main() {
	// 初始化, 最大圆的曲率
	K := -1.0
	k := 1 + 2.0/math.Sqrt(3.0)
	area := 1 - 3/(k*k)
	area -= dfs(k, k, k, 10)
	area -= 3 * dfs(K, k, k, 10)
	fmt.Printf("uncovered=%.8f\n", area)
}

func dfs(k1, k2, k3 float64, depth int) float64 {
	if depth == 0 {
		return 0
	}
	k4 := k1 + k2 + k3 + 2*math.Sqrt(k1*k2+k2*k3+k3*k1)
	return 1/(k4*k4) + dfs(k1, k2, k4, depth-1) + dfs(k1, k3, k4, depth-1) + dfs(k2, k3, k4, depth-1)
}
