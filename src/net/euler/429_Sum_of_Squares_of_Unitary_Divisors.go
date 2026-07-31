package main

import (
	"fmt"
	"math/bits"
)

func main() {
	N := int(1e8)
	primes := eulerSieve(N)
	fmt.Printf("len=%d\n", len(primes))
	// N!中每个质数的次数
	e := make([]int, len(primes))
	for i := 0; i < len(primes); i++ {
		e[i] = expnonent(N, primes[i])
	}
	// 对某一个质数p^k来说，单一约数，必须是全部包含它，要么是全部不包含它。
	// 所以最后的结果可以用一个连乘积表示
	// π(1+p^2k)
	prod := int64(1)
	for i := 0; i < len(primes); i++ {
		p := int64(primes[i])
		exp := int64(e[i]) * 2
		v := pow(p, exp, M)
		v = (v + 1) % M
		prod = mulMod(v, prod, M)
	}
	fmt.Printf("prod=%d\n", prod)
}

var N = int(1e8)
var M = int64(1e9) + 9

func expnonent(n, p int) int {
	cnt := 0
	for n > 0 {
		cnt += n / p
		n /= p
	}
	return cnt
}

func pow(a, d, n int64) int64 {
	res := int64(1)
	for d > 0 {
		if d&1 == 1 {
			res = mulMod(res, a, n)
		}
		a = mulMod(a, a, n)
		d >>= 1
	}
	return res
}

func mulMod(a, d, n int64) int64 {
	hi, lo := bits.Mul64(uint64(a), uint64(d))
	return int64(bits.Rem64(hi, lo, uint64(n)))
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

func eulerSieve(n int) []int {
	primes := make([]int, 0)
	composite := make([]bool, n+1)
	for i := 2; i <= n; i++ {
		if !composite[i] {
			primes = append(primes, i)
		}
		for _, p := range primes {
			if i*p > n {
				break
			}
			composite[i*p] = true
			if i%p == 0 {
				break
			}
		}
	}
	return primes
}
