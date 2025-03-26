package main

func genPrimes(max int) []int {
	list := make([]bool, max+1)
	for i := 0; i < len(list); i++ {
		list[i] = true
	}
	list[0], list[1] = false, false
	for i := 0; i < len(list); i++ {
		if !list[i] {
			continue
		}
		for j := i + i; j < len(list); j += i {
			list[j] = false
		}
	}
	primes := make([]int, 0)
	for i := 0; i < len(list); i++ {
		if list[i] {
			primes = append(primes, i)
		}
	}
	return primes
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
