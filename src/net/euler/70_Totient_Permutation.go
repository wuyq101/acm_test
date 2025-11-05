package main

import "fmt"

// https://projecteuler.net/problem=70
func main() {
	min := float64(0)
	idx := 0
	for i := 2; i <= 10000000; i++ {
		n := phi(i)
		if isPerm(i, n) {
			d := float64(i) / float64(n)
			if min == 0 || d < min {
				min = d
				idx = i
				fmt.Printf("i = %d, min = %f\n", idx, min)
			}

		}
	}
	fmt.Printf("i = %d, min = %f\n", idx, min)
}

func isPerm(a, b int) bool {
	cnt := make([]int, 10)
	for a > 0 {
		cnt[a%10]++
		a /= 10
	}
	for b > 0 {
		cnt[b%10]--
		b /= 10
	}
	for _, v := range cnt {
		if v != 0 {
			return false
		}
	}
	return true
}

// phi(n) = n * (1 - 1/p1) * (1 - 1/p2) * ...
// n = 20
// p = 2, result = 20 - 20/2 = 10, n = 5
// p = 3 ...
// result = 10 - 10/5 = 8  5,15
func phi(n int) int {
	result := n
	for p := 2; p*p <= n; p++ {
		if n%p == 0 {
			result -= result / p
			for n%p == 0 {
				n /= p
			}
		}
	}
	if n > 1 {
		result -= result / n
	}
	return result
}
