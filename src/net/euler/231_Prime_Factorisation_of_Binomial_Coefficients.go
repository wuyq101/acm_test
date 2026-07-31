package main

import "fmt"

func main() {
	primes := genPrimes(N)
	fmt.Printf("len=%d\n", len(primes))
	sum := int64(0)
	for _, p := range primes {
		sum += int64(p) * int64(cnt(N, p)-cnt(M, p)-cnt(N-M, p))
	}
	fmt.Printf("sum=%d\n", sum)

}

var N = int(2e7)
var M = int(15e6)

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

func cnt(n, p int) int {
	cnt := 0
	for n > 0 {
		cnt += n / p
		n /= p
	}
	return cnt
}
