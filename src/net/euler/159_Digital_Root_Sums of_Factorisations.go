package main

import "fmt"

/*

Euler's problem 159
Digital Root Sums of Factorisations

https://projecteuler.net/problem=159

*/

func main() {
	n := 212
	v := dr(n)
	fmt.Printf("drs = %d\n", v)
	n = 24
	v = mdrs(n)
	fmt.Printf("mdrs = %d\n", v)
	sum := 0
	for i := 2; i < 1000000; i++ {
		v = mdrs(i)
		//	fmt.Printf("mdrs(%d) = %d\n", i, v)
		sum += v
	}
	fmt.Printf("sum=%d\n", sum)
}

// digital root sum of n
// dr(n)= 1 + (n-1)%9
// 证明， 假设一个三位数 n = abc = 100a + 10b + c
// abc = (99a +a) + 9b + b + c
// n = abc = a+b+c (mod 9),  模9之后，就是各位数字之后对9取余数
// 为了避免18这种对9整除的情况，先-1，再+1
// dr(n)= 1 + (n-1)%9
func dr(n int) int {
	return 1 + (n-1)%9
}

var cache = map[int]int{}

func mdrs(n int) int {
	v, ok := cache[n]
	if ok {
		return v
	}

	list := factors(n)
	m := 0
	for _, v := range list {
		// v<=t, v*t==n
		t := n / v
		if v > t {
			continue
		}
		var s int
		if v == 1 {
			s = dr(n)
		} else {
			s = mdrs(v) + mdrs(t)
		}
		m = max(m, s)
	}
	cache[n] = m
	return m
}

func max(a, b int) int {
	if a > b {
		return a
	}
	return b
}

func factors(n int) []int {
	if n == 1 {
		return []int{1}
	}
	p := 0
	for i := 2; i*i <= n; i++ {
		if n%i == 0 {
			p = i
			break
		}
	}
	if p == 0 {
		return []int{1, n}
	}
	k := 1
	q := n / p
	for q%p == 0 {
		k++
		q /= p
	}
	list := factors(q)
	result := make([]int, 0)
	m := make(map[int]bool)
	for _, v := range list {
		for i := 0; i <= k; i++ {
			if !m[v] {
				result = append(result, v)
				m[v] = true
			}
			v *= p
		}
	}
	return result
}
