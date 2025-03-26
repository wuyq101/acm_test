package main

import (
	"fmt"
	"math"
	"strconv"
)

func main() {
	primes := genPrimes(20000)
	sets := make([][]int, 0)
	// 两两组合
	for i := 0; i < len(primes); i++ {
		for j := i + 1; j < len(primes); j++ {
			if isPairSet([]int{primes[i]}, primes[j]) {
				sets = append(sets, []int{primes[i], primes[j]})
			}
		}
	}
	fmt.Printf("find %d two pairset\n", len(sets))
	//fmt.Printf("%v\n", sets)
	// 3个组合
	tmp := make([][]int, 0)
	for i := 0; i < len(primes); i++ {
		for j := 0; j < len(sets); j++ {
			if isPairSet(sets[j], primes[i]) {
				tmp = append(tmp, append(sets[j], primes[i]))
			}
		}
	}
	sets = tmp
	fmt.Printf("find %d three pairset\n", len(sets))
	// 4个组合
	tmp = make([][]int, 0)
	for i := 0; i < len(primes); i++ {
		for j := 0; j < len(sets); j++ {
			if isPairSet(sets[j], primes[i]) {
				tmp = append(tmp, append(sets[j], primes[i]))
			}
		}
	}
	sets = tmp
	fmt.Printf("find %d four pairset\n", len(sets))

	// 5个组合
	tmp = make([][]int, 0)
	for i := 0; i < len(primes); i++ {
		for j := 0; j < len(sets); j++ {
			if isPairSet(sets[j], primes[i]) {
				tmp = append(tmp, append(sets[j], primes[i]))
			}
		}
	}
	sets = tmp
	fmt.Printf("find %d five pairset\n", len(sets))

	min := math.MaxInt32
	for _, set := range sets {
		sum := 0
		for _, v := range set {
			sum += v
		}
		if sum < min {
			min = sum
			fmt.Printf("min sum = %d, set = %v\n", sum, set)
		}
	}
	fmt.Printf("min = %d\n", min)

}

var m = make(map[int]bool)

func isPrimeCache(n int) bool {
	b, ok := m[n]
	if ok {
		return b
	}
	b = isPrime(n)
	m[n] = b
	return b
}

func isPairSet(set []int, p int) bool {
	for _, q := range set {
		// pq & qp contact is also prime
		v := strconv.Itoa(p) + strconv.Itoa(q)
		n, _ := strconv.Atoi(v)
		if !isPrimeCache(n) {
			return false
		}

		v = strconv.Itoa(q) + strconv.Itoa(p)
		n, _ = strconv.Atoi(v)
		if !isPrimeCache(n) {
			return false
		}
	}
	return true
}
