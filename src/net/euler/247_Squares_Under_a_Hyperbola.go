package main

import (
	"fmt"
	"math"
)

/*
y = 1/x, x>=1

求曲线下最大的正方形，正方形用(i,j)编码，i表示它左边的的正方形个数，j表示它下方的正方形个数

(1,+∞)中最大的正方形边长s
一开始的边界是曲线左下的点是(1,0)

假设找到一个最大的边长是s

之后剩余的区域分成两块，
上面的部分，它的左下点, (1,s)

右边的部分，它的左下点, (1+s,0)

然后在这两个区域继续找它的最大边长s1',s2', 取这两个中最大的作为s2

一直递归下去，找到最大的(3,3)...




*/

func main() {
	dfs(1.0, 0, 0, 0)
	fmt.Printf("side min=%.10f\n", side_min)
	f(1.0, 0)
	fmt.Printf("cnt=%d\n", cnt)

}

var side_min = math.MaxFloat64
var cnt = 0

func f(X, Y float64) {
	s := side(X, Y)
	if s < side_min {
		return
	}
	cnt++
	// 上半部分
	f(X, Y+s)
	// 右边部分
	f(X+s, Y)
}

func dfs(X, Y float64, left, below int) {
	if left > 3 || below > 3 {
		return
	}
	// 假设边长是s
	// 曲线 x*y = 1
	// (X+s)(Y+s) = 1
	// s = (√((X-Y)^2+4) - (X+Y))/2
	s := side(X, Y)
	if left == 3 && below == 3 {
		if s < side_min {
			side_min = s
		}
		return
	}
	// 上半部分
	dfs(X, Y+s, left, below+1)
	// 右边部分
	dfs(X+s, Y, left+1, below)
}

func side(X, Y float64) float64 {
	d := X - Y
	s := math.Sqrt(d*d+4) - (X + Y)
	return s / 2.0
}
