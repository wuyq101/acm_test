package main

import "fmt"

var M = 50000000
var primes []int

func main() {
	for i := 2; i*i < M; i++ {
		if isPrime(i) {
			primes = append(primes, i)
		}
	}
	fmt.Printf("%d\n", len(primes))
	set := make(map[int]bool)
	for i := 0; i < len(primes); i++ {
		a := primes[i]
		if a*a >= M {
			break
		}
		for j := 0; j < len(primes); j++ {
			b := primes[j]
			if a*a+b*b*b >= M {
				break
			}
			for k := 0; k < len(primes); k++ {
				c := primes[k]
				A := a*a + b*b*b + c*c*c*c
				if A <= M {
					set[A] = true
				} else {
					break
				}
			}
		}
	}
	fmt.Printf("%d\n", len(set))
}

func isPrime(n int) bool {
	if n < 2 {
		return false
	}
	for i := 2; i*i <= n; i++ {
		if n%i == 0 {
			return false
		}
	}
	return true
}
