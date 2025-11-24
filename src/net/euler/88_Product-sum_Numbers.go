package main

import (
	"fmt"
	"sort"
)

var m = make(map[int]int)

func main() {
	for n := 1; n <= 13000; n++ {
		k := num(n)
		fmt.Printf("n=%d, s=%d\n", n, k)
		pre := m[k]
		if pre == 0 {
			m[k] = n
		} else if pre > n {
			m[k] = n
		}
	}
	a := 2
	b := 12000
	set := make(map[int]bool)
	for k := a; k <= b; k++ {
		fmt.Printf("k=%d, n=%d\n", k, m[k])
		set[m[k]] = true
	}
	sum := 0
	for k := range set {
		sum += k
	}
	fmt.Printf("sum = %d\n", sum)
}

var cache = make(map[string]int)

func num(n int) int {
	// n 因子分解
	// n = p1^a1 * p2^a2 * ... * pk^ak
	// 12 = 2*2*3
	// k = 3  s = 2+2+3 = 7 12-7 = 5, 还需要5个1.
	// k = 8 ok 12 = 2+2+3+1+1+1+1+1 = 2*2*3*1*1*1*1*1
	// k = 7  4*3 4+3+1+1+1+1+1 = 4*3*1*1*1*1*1
	// k = 6       2*6 = 2+6+1+1+1+1
	// k = 6
	// k = 5
	// k = 4
	// k = 3
	// k = 2

	// k = 6
	// m = a1*p1 + a2*p2 + ... + ak*pk
	// m = 2 + 2 + 3 = 7
	//
	factors := make([]int, 0)
	t := n
	for i := 2; i <= t; i++ {
		for t%i == 0 {
			t /= i
			factors = append(factors, i)
		}
	}
	fmt.Printf("n=%d, factors=%v\n", n, factors)
	cache = make(map[string]int)
	k := findMinK(n, factors)
	//	fmt.Printf("k=%d\n", k)
	return k
}

func update(k, n int) {
	pre, ok := m[k]
	if !ok {
		m[k] = n
		return
	}
	if n < pre {
		m[k] = n
	}
}

func findMinK(n int, factors []int) int {
	key := fmt.Sprintf("%v", factors)
	v, ok := cache[key]
	if ok {
		return v
	}
	if len(factors) == 2 {
		s := sum(factors)
		k := n - s + len(factors)
		//	fmt.Printf("n=%d,k=%d, factors=%v\n", n, k, factors)
		update(k, n)
		return k
	}
	s := sum(factors)
	k := n - s + len(factors)
	update(k, n)
	// 合并因子，合并因子再求新的k
	for i := 0; i < len(factors); i++ {
		if i-1 >= 0 && factors[i-1] == factors[i] {
			continue
		}
		last := 0
		for j := i + 1; j < len(factors); j++ {
			if factors[j] == last {
				continue
			}
			// 合并i,j
			tmp := make([]int, 0)
			for k := 0; k < len(factors); k++ {
				if k == i || k == j {
					continue
				}
				tmp = append(tmp, factors[k])
			}
			tmp = append(tmp, factors[i]*factors[j])
			sort.Ints(tmp)
			last = factors[j]
			k = min(k, findMinK(n, tmp))
			update(k, n)
		}
	}
	//	fmt.Printf("n=%d,k=%d, factors=%v\n", n, k, factors)
	cache[key] = k
	return k
}

func sum(n []int) int {
	s := 0
	for _, v := range n {
		s += v
	}
	return s
}
