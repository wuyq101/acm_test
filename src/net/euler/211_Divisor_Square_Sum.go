package main

import (
	"fmt"
	"math"
)

/*

https://projecteuler.net/problem=211


*/

func main() {
	N := int64(64000000)
	sigma := make([]int64, N)
	for i := int64(1); i < N; i++ {
		k := i * i
		for j := i; j < N; j += i {
			sigma[j] += k
		}
	}
	sum := int64(0)
	for i := int64(1); i < N; i++ {
		if isSquare(sigma[i]) {
			sum += i

		}
	}
	fmt.Printf("sum=%d\n", sum)
}

func isSquare(n int64) bool {
	r := int64(math.Sqrt(float64(n)))
	return n == r*r
}
