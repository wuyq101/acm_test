package main

import "fmt"

func main() {
	primes := genPrimes(1000050)
	b := primes[len(primes)-1]
	fmt.Printf("b=%d, b*b=%d\n", b, b*b)
	//N := int64(1000)
	N := int64(999966663333)
	sum := int64(0)
	for i := 0; i < len(primes)-1; i++ {
		p := int64(primes[i])
		q := int64(primes[i+1])
		if p*p <= N {
			s := f(p, q, N)
			sum += s
		} else {
			break
		}
	}
	fmt.Printf("sum=%d\n", sum)
}

func f(p, q, n int64) int64 {
	//	fmt.Printf("f p=%d, q=%d, n=%d\n", p, q, n)
	A, B := p*p, q*q
	if B > n {
		B = n + 1
	}
	// A,B 之间，p的倍数，q的倍数之和，减去p,q的公倍数
	a, b, c := g(p, A+1, B-1), g(q, A+1, B-1), g(p*q, A+1, B-1)
	//	fmt.Printf("a=%d, b=%d, c=%d\n", a, b, c)
	return a + b - c*2
}

func g(p, A, B int64) int64 {
	//	fmt.Printf("g p=%d, A=%d, B=%d\n", p, A, B)
	a := A / p
	for a*p < A {
		a++
	}
	b := B / p
	return p * (a + b) * (b - a + 1) / 2
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
