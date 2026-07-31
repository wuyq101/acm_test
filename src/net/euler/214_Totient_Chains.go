package main

import "fmt"

func main() {
	primes := genPrimes(int(N))
	fmt.Printf("len(primes) = %d\n", len(primes))
	fmt.Printf("chains(5)=%d\n", chains(5))
	sum := int64(0)
	for _, p := range primes {
		v := chains(p)
		if v == 25 {
			sum += int64(p)
		}
	}
	fmt.Printf("sum=%d\n", sum)
}

var N = 4e7

func genPrimes(n int) []int {
	composite := make([]bool, n+1)
	for i := 2; i <= n; i++ {
		if composite[i] {
			continue
		}
		for j := i + i; j <= n; j += i {
			composite[j] = true
		}
	}
	primes := make([]int, 0, n/2)
	for i := 2; i <= n; i++ {
		if !composite[i] {
			primes = append(primes, i)
		}
	}
	return primes
}

var cache = map[int]int{
	1: 1,
	2: 2,
}

func chains(n int) int {
	v, ok := cache[n]
	if ok {
		return v
	}
	next := ø(n)
	v = 1 + chains(next)
	cache[n] = v
	return v
}

// ø(n) = n * (1 - 1/p1) * (1 - 1/p2) * ...
// n = 20
// p = 2, result = 20 - 20/2 = 10, n = 5
// p = 3 ...
// result = 10 - 10/5 = 8  5,15
func ø(n int) int {
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
