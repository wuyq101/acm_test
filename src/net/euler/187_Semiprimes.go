package main

import "fmt"

func main() {
	M := 100000000
	composites := make([]bool, M)
	composites[2] = false
	for i := 2; i < M; i++ {
		if composites[i] {
			continue
		}
		for j := i + i; j < M; j += i {
			composites[j] = true
		}
	}
	primes := make([]int, 0)
	for i := 2; i < M; i++ {
		if !composites[i] {
			primes = append(primes, i)
		}
	}
	fmt.Printf("len=%d\n", len(primes))
	fmt.Printf("first 100 primes = %v\n", primes[:500])
	cnt := 0
	for i := 0; i < len(primes); i++ {
		for j := i; j < len(primes); j++ {
			v := primes[i] * primes[j]
			if v >= M {
				break
			}
			cnt++
		}
	}
	fmt.Printf("cnt=%d\n", cnt)
}
