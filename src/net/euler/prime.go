package main

import "math"

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

func isPrime(n int) bool {
	if n < 2 {
		return false
	}
	for i := 2; i*i <= n; i++ {
		if n%i == 0 {
			return false
		}
	}
	return true
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

func bitsetSieve(n int) []int {
	// 只存奇数，索引 i 代表数字 2*i + 3
	half := (n - 1) / 2
	sieve := make([]uint64, half/64+1)

	// 预估 12 亿以内的素数个数约 6100 万，预先分配容量避免动态扩容
	cnt := int(float64(n) / math.Log(float64(n)))
	primes := make([]int, 0, cnt)
	primes = append(primes, 2)

	// 一个uint64有64位，每个位代表一个数字
	for i := 0; i < half; i++ {
		// 检查第 i 位是否为 0
		if (sieve[i>>6] & (1 << (i & 63))) == 0 {
			p := 2*i + 3
			primes = append(primes, p)

			// 标记 p 的倍数，从 p^2 开始
			if p*p <= n {
				// p^2 对应的索引是 (p^2 - 3) / 2
				for j := (p*p - 3) / 2; j < half; j += p {
					// 将第 j 位置为 1
					sieve[j>>6] |= (1 << (j & 63))
				}
			}
		}
	}
	return primes
}
