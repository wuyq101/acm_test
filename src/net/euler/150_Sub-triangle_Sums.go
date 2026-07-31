package main

import (
	"fmt"
	"math"
)

/*
https://projecteuler.net/problem=150

*/

func main() {
	table = make([][]int64, N)
	prefix = make([][]int64, N)
	for r := 0; r < N; r++ {
		table[r] = make([]int64, N)
		prefix[r] = make([]int64, N)
	}
	t := 0
	M := 1 << 20
	R := 1 << 19
	i, j := 0, 0
	sum := int64(0)
	for k := 1; k <= 500500; k++ {
		t = (615949*t + 797807) % M
		table[i][j] = int64(t - R)
		sum += table[i][j]
		prefix[i][j] = sum
		j++
		if j > i {
			j = 0
			i++
			sum = 0
		}
	}
	for i := 0; i <= 3; i++ {
		for j := 0; j <= i; j++ {
			fmt.Printf("%d ", table[i][j])
		}
		fmt.Println()
	}
	v := F()
	fmt.Printf("v=%d\n", v)
}

var table [][]int64
var prefix [][]int64
var N = 1000

// 以i,j为起点的三角形最小和
func f(i, j int) int64 {
	v := int64(math.MaxInt64)
	sum := int64(0)
	for k := 0; k+i < N; k++ {
		// 第i+k行，一共k+1个元素, 从j开始到j+k
		r := i + k
		right := j + k
		sum += prefix[r][right]
		left := j - 1
		if left >= 0 {
			sum -= prefix[r][left]
		}
		v = min(v, sum)
	}
	return v
}

func F() int64 {
	v := int64(math.MaxInt64)
	for i := 0; i < N; i++ {
		for j := 0; j <= i; j++ {
			v = min(v, f(i, j))
		}
	}
	return v
}

func min(a, b int64) int64 {
	if a < b {
		return a
	}
	return b
}
