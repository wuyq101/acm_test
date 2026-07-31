package main

import (
	"fmt"
	"math"
	"strconv"
)

func main() {
	N := 50000000
	primes := genPrimes(N)
	idx := 0
	sum := 0
	for _, p := range primes {
		s2 := p * p
		if isPalindrome(s2) {
			continue
		}
		v := reverse(s2)
		k := v % 10
		if k == 3 || k == 7 {
			continue
		}
		if !isSquare(v) {
			continue
		}
		q := int(math.Sqrt(float64(v)))
		f := false
		for j := 0; j < len(primes); j++ {
			if q == primes[j] {
				f = true
				break
			}
			if primes[j] > q {
				break
			}
		}
		if !f {
			continue
		}
		idx++
		sum += s2
		fmt.Printf("idx=%d, sum=%d s2=%d\n", idx, sum, s2)
		if idx == 50 {
			break
		}
	}
	fmt.Printf("sum=%d\n", sum)
}

func reverse(n int) int {
	str := strconv.Itoa(n)
	buf := []byte(str)
	for i, j := 0, len(buf)-1; i < j; i, j = i+1, j-1 {
		buf[i], buf[j] = buf[j], buf[i]
	}
	v, _ := strconv.Atoi(string(buf))
	return v
}

func isSquare(n int) bool {
	p := int(math.Sqrt(float64(n)))
	return p*p == n
}

func isPalindrome(p int) bool {
	str := strconv.Itoa(p)
	for i, j := 0, len(str)-1; i < j; i, j = i+1, j-1 {
		if str[i] != str[j] {
			return false
		}
	}
	return true
}

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
