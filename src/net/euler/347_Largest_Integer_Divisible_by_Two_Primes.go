package main

import "fmt"

func main() {
	N := 10000000
	primes := genPrimes(N)
	sum := int64(0)
	for i := 0; i < len(primes); i++ {
		for j := i + 1; j < len(primes); j++ {
			v := M(primes[i], primes[j], N)
			if v == 0 {
				break
			}
			//	fmt.Printf("%d %d %d\n", primes[i], primes[j], v)
			sum += int64(v)
		}
	}
	fmt.Printf("sum=%d\n", sum)

}

func M(p, q, N int) int {
	if p*q > N {
		return 0
	}
	m := make(map[int]bool)
	a := p * q
	list := []int{a}
	m[a] = true
	f := []int{p, q}
	for len(list) > 0 {
		v := list[0]
		list = list[1:]
		for i := 0; i < 2; i++ {
			t := v * f[i]
			if t <= N && m[t] == false {
				list = append(list, t)
				m[t] = true
				if t > a {
					a = t
				}
			}
		}
	}
	return a
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
