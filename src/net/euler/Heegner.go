package main

import (
	"fmt"
	"math"
)

/*
https://projecteuler.net/problem=heegner
cos(pi*sqrt(n)), cos(A) 整数， A = k*pi/2
所以 sqrt(n) 和 k/2越接近，值越接近整数。
求根号n的分数形式，比较和k/2的差值
*/
func main() {
	m := 1.0
	idx := 0
	for i := 0; i <= 1000; i++ {
		if isSquare(i) {
			continue
		}
		v := math.Sqrt(float64(i))
		v = math.Cos(math.Pi * v)
		d := 0.0
		if v < 0 {
			d = v + 1.0
		} else {
			d = 1 - v
		}
		fmt.Printf("%d %f\n", i, d)
		if d < m {
			m = d
			idx = i
		}
	}
	fmt.Printf("%d %f\n", idx, m)
}

func isSquare(n int) bool {
	v := int(math.Sqrt(float64(n)))
	return v*v == n
}
