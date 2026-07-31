package main

import "fmt"

/*
http://projecteuler.net/problem=249

S = {2,3,...,4999}, 小于5000的质数集合
求S的子集和，且和是质数
求符合条件的子集的个数N，N(mod 10^16), 求最低的16位

子集的个数：2^669个，如果遍历这些子集，再分别求，不现实。

最大的和是1548136， 那么所有小于1548136的质数，都可以作为子集和。

现在假设子集和是K，那么组成K的子集个数是多少呢？

可以用DP来求，
dp[k]表示子集和为k的子集个数，
现在有一个质数p

dp[k+p] += dp[k]





*/

func main() {
	primes := eulerSieve(5000)
	fmt.Printf("len=%d\n", len(primes))
	MAX := 0
	for _, p := range primes {
		MAX += p
	}
	fmt.Printf("MAX=%d\n", MAX)
	sumPrimes := eulerSieve(MAX)
	fmt.Printf("MAX prime = %d\n", sumPrimes[len(sumPrimes)-1])
	MP := sumPrimes[len(sumPrimes)-1]
	dp := make([]int64, MP+1)
	dp[0] = 1
	for _, p := range primes {
		for k := MP; k >= 0; k-- {
			if k >= p {
				dp[k] = add(dp[k], dp[k-p])
			}
		}
	}
	S := int64(0)
	for _, p := range sumPrimes {
		S = add(S, dp[p])
	}
	fmt.Printf("S=%d\n", S)
}

var M = int64(1e16)

func add(a, b int64) int64 {
	return (a + b) % M
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
