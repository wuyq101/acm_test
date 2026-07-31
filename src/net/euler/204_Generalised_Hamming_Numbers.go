package main

import "fmt"

func main() {
	primes = genPrimes(100)
	fmt.Printf("primes = %v\n", primes)
	dfs(1)
	fmt.Printf("len=%d\n", len(m))
}

var M = int64(1e9)
var primes []int
var m = make(map[int64]bool)

func dfs(n int64) {
	if m[n] {
		return
	}
	m[n] = true
	for _, p := range primes {
		v := n * int64(p)
		if v > M {
			break
		}
		dfs(v)
	}
}

func genPrimes(n int) []int {
	composites := make([]bool, n+1)
	for i := 2; i <= n; i++ {
		if composites[i] {
			continue
		}
		for j := i + i; j <= n; j += i {
			composites[j] = true
		}
	}
	primes := make([]int, 0)
	for i := 2; i <= n; i++ {
		if !composites[i] {
			primes = append(primes, i)
		}
	}
	return primes
}
