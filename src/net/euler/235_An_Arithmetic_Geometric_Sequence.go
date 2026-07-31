package main

import (
	"fmt"
	"math"
)

/*
u(k) = (900-3k)*r^(k-1)

S(n) = ∑_{k=1}^{n} u(k)

S(n) = (900-3*1)*r^0 + (900-3*2)*r^1 + ... + (900-3*n)*r^(n-1)

S(n)*r = (900-3*1)*r^1 + (900-3*2)*r^2 + ... + (900-3*n)*r^n

S(n)*r-S(n) = 3*(r+r^2+r^3+...+r^(n-1))+(900-3n)*r^n-(900-3*1)
(r-1)S(n) = 3r(1-r^(n-1))/(1-r)+(900-3n)*r^n-897
S(n) = (897-(900-3n)*r^n)/(1-r) - 3r(1-r^(n-1))/(1-r)^2
*/

func main() {
	Target := float64(-6e11)
	n := float64(5000)
	left := 1.0
	right := 1.05
	// 在1.0 -- 1.05 单调递减
	mid := 0.0
	cnt := 0
	for left < right {
		mid = (left + right) / 2
		m := S(n, mid)
		if m > Target {
			left = mid
		} else {
			right = mid
		}
		cnt++
		if cnt >= 200 {
			break
		}
	}
	fmt.Printf("mid=%.12f m=%.12f\n", mid, S(n, mid))
}

func S(n float64, r float64) float64 {
	c := 1 - r
	A := (897 - (900-3*n)*math.Pow(r, n)) / c
	B := 3 * r * (1 - math.Pow(r, n-1)) / (c * c)
	return A - B
}
