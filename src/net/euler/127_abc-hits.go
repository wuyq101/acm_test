package main

import (
	"fmt"
	"sort"
)

func main() {
	primes := genPrimes()
	fmt.Printf("len(primes) = %d\n", len(primes))
	fmt.Printf("first 10 primes = %v\n", primes[:10])
	p, c := factors(504)
	fmt.Printf("%v %v\n", p, c)
	r := rad(504)
	fmt.Printf("%d\n", r)
	hit(32, primes)
	sum := 0
	cnt := 0
	for c := 2; c < 120000; c++ {
		v := hit(c, primes)
		if v > 0 {
			sum += c * v
			cnt += v
		}
	}
	fmt.Printf("sum = %d, cnt=%d\n", sum, cnt)
}

func genPrimes() []int {
	MAX := 120000
	p := make([]bool, MAX)
	for i := 0; i < MAX; i++ {
		p[i] = true
	}
	p[0], p[1] = false, false
	for i := 0; i < MAX; i++ {
		if !p[i] {
			continue
		}
		for j := i + i; j < MAX; j += i {
			p[j] = false
		}
	}
	primes := make([]int, 0)
	for i := 0; i < MAX; i++ {
		if p[i] {
			primes = append(primes, i)
		}
	}
	return primes
}

func hit(c int, primes []int) int {
	// 针对一个c，是否存在对应的a,b
	// gcd(a,b)=gcd(a,c)=gcd(b,c)=1
	// a<b
	// a+b = c
	// rad(abc) < c --> rad(ab) < c/rad(c)
	p, _ := factors(c)
	//	fmt.Printf("hit for %d = %v %v\n", c, p, k)
	rad_c := 1
	for _, v := range p {
		rad_c *= v
	}
	max_prod := c / rad_c
	//fmt.Printf("rad(ab) < %d\n", max_prod)
	m := make(map[int]bool)
	for _, v := range p {
		m[v] = true
	}

	list := make([]int, 0)
	// 可选的构成a b的质数
	list = make([]int, 0)
	for _, v := range primes {
		if v <= max_prod {
			if m[v] {
				continue
			}
			list = append(list, v)
		} else {
			break
		}
	}
	primes = list

	//fmt.Printf("候选质数 = %v\n", primes)
	// 由质数构成的所有小于c的数集合
	list = genCandidate(c, primes)

	//fmt.Printf("所有小于%d的数集合 = %v\n", c, list)
	m = make(map[int]bool)
	for _, v := range list {
		m[v] = true
	}
	cnt := 0
	for _, a := range list {
		b := c - a
		// a<b
		if a >= b {
			break
		}
		if !m[b] {
			continue
		}
		// gcd
		if gcd(a, b) != 1 {
			continue
		}
		// 验证
		if rad(a)*rad(b) >= max_prod {
			continue
		}
		fmt.Printf("****** find a=%d,b=%d,c=%d\n", a, b, c)
		cnt++
	}

	return cnt
}

var candidate = make([]int, 0)
var candidateMap = make(map[int]bool)

func genCandidate(c int, primes []int) []int {
	candidate = make([]int, 0)
	candidateMap = make(map[int]bool)
	dfs(c, 1, primes)
	list := make([]int, 0, len(candidate))
	sort.Ints(candidate)
	for i, v := range candidate {
		if i == 0 || (i > 0 && v != candidate[i-1]) {
			list = append(list, v)
		}
	}
	return list
}

func dfs(c, v int, primes []int) {
	if v < c {
		candidate = append(candidate, v)
		candidateMap[v] = true
		for _, p := range primes {
			k := v * p
			if k > c {
				break
			}
			if candidateMap[k] {
				continue
			}
			dfs(c, k, primes)
		}
	}
}

func contains(list []int, v int) bool {
	for _, n := range list {
		if n == v {
			return true
		}
	}
	return false
}

func gcd(a, b int) int {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}

func rad(n int) int {
	p, _ := factors(n)
	s := 1
	for _, v := range p {
		s *= v
	}
	return s
}

var factorsCache = make(map[int][][]int)

func factors(n int) ([]int, []int) {
	k := n
	v, ok := factorsCache[k]
	if ok {
		//	fmt.Printf("factors for %d = %v %v\n", k, v[0], v[1])
		return v[0], v[1]
	}
	p := make([]int, 0)
	c := make([]int, 0)
	i := 2
	for n > 1 {
		if n%i == 0 {
			cnt := 0
			for n%i == 0 {
				cnt++
				n /= i
			}
			p = append(p, i)
			c = append(c, cnt)
		}
		i++
	}
	v = [][]int{p, c}
	factorsCache[k] = v
	return p, c
}
