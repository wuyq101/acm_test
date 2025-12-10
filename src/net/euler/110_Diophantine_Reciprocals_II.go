package main

import (
	"fmt"
	"math/big"
)

/*
https://projecteuler.net/problem=110

1/x + 1/y = 1/n
x,y 的种类就是 n^2的因数
n = p1^a1 * p2^a2 * ... * pk^ak
D(n^2) = (2a1+1)(2a2+1)...(2ak+1)
D(n^2) = (2a1+1)(2a2+1)...(2ak+1) >= 8000000
a1 >= a2 >= a3 >= ... >= ak

k = 15, n = 2*3*5*7*11*13*17*19*23*29*31*37*41*43*47 D(n^2) = 14348907  > 8000000
所以k最大不超过15
可以在k固定的情况下，优化a1,a2..是的D(n^2)超过8000000,并且n最小
*/

func main() {
	primes := genPrimes(100)
	fmt.Printf("%d\n", len(primes))
	fmt.Printf("%v\n", primes)

	a := make([]int, 15)
	for i := 0; i < 15; i++ {
		a[i] = 6
	}
	n = big.NewInt(0)
	dfs(a, primes)
	fmt.Printf("%v\n", n)
}

var M = int64(8000000)

var m = make(map[int64]int)

func hash(a []int) int64 {
	s := int64(1)
	// a[i] : 0,1,2,3,4,5  六进制
	for _, v := range a {
		s = s*7 + int64(v)
	}
	return s
}

var n = big.NewInt(0)

func D(a []int) int64 {
	s := int64(1)
	for _, v := range a {
		if v > 0 {
			s *= int64(2*v + 1)
		}
	}
	return s
}

func dfs(a []int, primes []int) {
	key := hash(a)
	_, ok := m[key]
	if ok {
		return
	} else {
		m[key] = 1
	}
	// D(n^2) >= 8000000
	d := D(a)
	if d >= M {
		t := big.NewInt(1)
		for i := 0; i < len(a); i++ {
			if a[i] > 0 {
				cnt := a[i]
				for cnt > 0 {
					t.Mul(t, big.NewInt(int64(primes[i])))
					cnt--
				}
			}
		}
		if n.Cmp(big.NewInt(0)) == 0 {
			n.Set(t)
			fmt.Printf("a=%v D=%d, n=%v\n", a, d, n)
		} else if n.Cmp(t) > 0 {
			n.Set(t)
			fmt.Printf("a=%v D=%d, n=%v\n", a, d, n)
		}
	} else {
		return
	}
	for i := len(a) - 1; i >= 0; i-- {
		if a[i] == 0 {
			continue
		}
		if i == len(a)-1 || (a[i]-1 >= a[i+1]) {
			a[i]--
			dfs(a, primes)
			a[i]++
		}
	}
}

func genPrimes(N int) []int {
	list := make([]bool, N+1)
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
