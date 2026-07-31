package main

/*

https://projecteuler.net/problem=272
从271题，分析我们可以知道解的个数和
x^3 = 1 (mod p), p是n的质因数有关。
242+1 = 243 = 3^5

x^3 = 1 (mod p) 最多只有3个根。

所以n = P1*P2*P3*P4*P5*M
其中P1,P2,P3,P4,P5, 每个质数产生3个根，剩下的M都只有1个根，只要乘积不超过上界就行。

1. 3个根
p=1(mod 3), 对这些p以及它的任意次幂，都有3个根

p = 3^k, k>=2, 也是3个根

2. 1个根
p=2(mod 3), 对这些p以及它的任意次幂，都有1个根
p=3, 1个根



*/
import "fmt"

func main() {
	primes = genPrimes(1e7)
	fmt.Printf("len=%d\n", len(primes))
	dfs(1, 0, 0)
	fmt.Printf("sum=%d\n", sum)
}

var LIMIT = int64(1e11)
var sum = int64(0)
var primes []int

func dfs(n int64, idx int, core int) {
	if !check(n, idx, core) {
		return
	}
	// 使用质数primes[idx], k次，k从1开始
	p := int64(primes[idx])
	//	fmt.Printf("p=%d, n=%d, idx=%d, core=%d\n", p, n, idx, core)
	k := int64(1)
	dc := 0
	if p%3 == 1 {
		dc = 1
	}
	for {
		v := n * pow(p, k)
		if v > LIMIT {
			break
		}
		if p == 3 {
			if k >= 2 {
				dc = 1
			} else {
				dc = 0
			}
		}
		k++
		nc := core + dc
		if nc <= 5 {
			if nc == 5 {
				sum += v
			}
			dfs(v, idx+1, nc)
		}
	}
	// 不使用质数primes[idx]
	dfs(n, idx+1, core)
}

func check(n int64, idx int, core int) bool {
	if idx >= len(primes) {
		return false
	}

	if core == 5 {
		// 至少乘一个最小的，否则后面计算也没有意义了
		return int64(primes[idx]) <= LIMIT/n
	}
	// 还剩余core的个数，假设只使用最小的那几个质数
	// 这个是必须乘
	left := 5 - core
	prod := int64(1)
	for left > 0 {
		if idx >= len(primes) {
			return false
		}
		p := int64(primes[idx])
		idx++
		if p%3 == 1 {
			if LIMIT/prod < p {
				return false
			}
			prod *= p
			left--
		} else if p == 3 {
			if LIMIT/prod < 9 {
				return false
			}
			prod *= 9
			left--
		}
	}
	return n <= LIMIT/prod
}

func pow(p, k int64) int64 {
	r := int64(1)
	for k > 0 {
		if k&1 == 1 {
			r *= p
		}
		p *= p
		k >>= 1
	}
	return r
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
