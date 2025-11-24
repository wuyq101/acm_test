package main

import "fmt"

var M = 1000000

func main() {
	n := 12496
	/*
		p := primes(n)
		fmt.Printf("p=%v\n", p)
		sum = 0
		dfs(1, p)
		fmt.Printf("sum=%d\n", sum-n)
	*/

	list := chain(n)
	fmt.Printf("%d\n", len(list))
	fmt.Printf("list=%v\n", list)

	m := 0
	for i := 1; i <= M; i++ {
		list := chain(i)
		if len(list) > m {
			m = len(list)
			fmt.Printf("i=%d, m=%d list=%v\n", i, m, list)
		}
	}
}

func nx(n int) int {
	p := primes(n)
	sum = 0
	dfs(1, p)
	return sum - n
}

func primes(n int) map[int]int {
	cnt := make(map[int]int)
	i := 2
	for n > 1 {
		for n%i == 0 {
			n /= i
			cnt[i]++
		}
		i++
	}
	return cnt
}

var sum = 0

func dfs(p int, m map[int]int) int {
	//fmt.Printf("p=%d, m=%v\n", p, m)
	if len(m) == 0 {
		sum += p
		return p
	}
	for k, v := range m {
		// 取其中一个因子
		// fmt.Printf("k=%d, v=%d\n", k, v)
		// 一共有v个k
		// 选0个k
		dfs(p, cp(m, k))
		for i := 1; i <= v; i++ {
			p *= k
			dfs(p, cp(m, k))
		}
		break
	}
	return 0
}

func cp(m map[int]int, a int) map[int]int {
	n := make(map[int]int)
	for k, v := range m {
		if k == a {
			continue
		}
		n[k] = v
	}
	return n
}

func next(n int) int {
	// n = a^3*b^5
	// (3+1)*(5+1)
	// a^3 * b^1
	// (3+1)*(1+1) = 8个因子
	// 1 a aa aaa
	// 1 b
	//
	// 1 b
	// a ab
	// aa aab
	// aaa aaab
	sum := 0
	for i := 1; i <= n/2; i++ {
		if n%i == 0 {
			sum += i
		}
	}
	//	fmt.Printf("%d next %d\n", n, sum)
	return sum
}

func chain(n int) []int {
	list := make([]int, 0)
	list = append(list, n)
	m := make(map[int]bool)
	m[n] = true
	for {
		n = nx(n)
		if n > M {
			return []int{}
		}
		if n == 1 {
			return []int{}
		}
		if m[n] {
			if n == list[0] {
				return list
			}
			return []int{}
		}
		list = append(list, n)
		m[n] = true
	}
	return list
}
