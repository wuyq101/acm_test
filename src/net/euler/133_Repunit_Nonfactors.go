package main

import "fmt"

/*
R(k) = (10^k - 1) / 9
题目要求 R(k) 能被 n 整除，即：
(10^k - 1) / 9 ≡ 0 (mod n)
这等价于：
10^k - 1 ≡ 0 (mod 9n)
由于 GCD(n, 10)=1，且 GCD(9, 10)=1，所以 GCD(9n, 10)=1。因此，上述同余式等价于：
10^k ≡ 1 (mod 9n)

一个质数 p（p ≠ 2, 5）能整除某个全一数 R(k) 的条件是：
10^k ≡ 1 (mod 9p)。
最小的满足条件的 k 称为 A(p)，即10模 9p 的阶。
因此，p 能整除 R(10^n) 的条件是：10^(10^n) ≡ 1 (mod 9p)。
根据阶的性质，这等价于：A(p) 必须能整除 10^n
求出A(p), 然后A(p)只能有2和5构成
*/
func main() {
	MAX := 100000
	n := 5
	sum := 2 + 3 + 5
	for {
		n += 2
		if n > MAX {
			break
		}
		if !isPrime(n) {
			continue
		}
		m := 9 * n
		p := Ω(m)
		k := A(m, p)
		// 如果k的因子只有2和5,那么一定存在R（10^k)能被n整除
		if !check(k) {
			fmt.Printf("n=%d, m=%d, p=%d, k=%d\n", n, m, p, k)
			sum += n
		}
	}
	fmt.Printf("sum=%d\n", sum)
}

func check(k int) bool {
	for k%2 == 0 {
		k /= 2
	}
	for k%5 == 0 {
		k /= 5
	}
	return k == 1
}

/*
10^k = 1 mod m
p = phi(m)
*/
func A(m, p int) int {
	for i := 2; i <= p; i++ {
		if p%i == 0 {
			// i是p的一个因子，开始检查10^i对m的余数是否为1
			t := mod(i, m)
			//fmt.Printf("%d * %d = %d, t = %d\n", i, p/i, p, t)
			if t == 1 {
				return i
			}
		}
	}
	return 0
}

var pm = map[int]int{}

func Ω(n int) int {
	v, ok := pm[n]
	if ok {
		return v
	}
	if isPrime(n) {
		pm[n] = n - 1
		return n - 1
	}
	for i := 2; i*i <= n; i++ {
		if n%i == 0 {
			// n = i*k
			k := n / i
			if isPrime(i) {
				// 若 p 是质数，且 p 能整除 n，则 φ(n * p) = φ(n) * p。
				// 若 p 是质数，且 p 不能整除 n，则 φ(n * p) = φ(n) * (p - 1)。
				if k%i == 0 {
					v := Ω(k) * i
					pm[n] = v
					return v
				} else {
					v := Ω(k) * (i - 1)
					pm[n] = v
					return v
				}
			}
		}
	}
	return 0
}

// 10^k mod m
func mod(k, m int) int {
	if k == 1 {
		return 10 % m
	}
	if k%2 == 0 {
		a := mod(k/2, m)
		return (a * a) % m
	}
	a := mod(k-1, m)
	return a * 10 % m
}

var primes = map[int]bool{
	1: false,
	2: true,
	3: true,
}

func isPrime(n int) bool {
	b, ok := primes[n]
	if ok {
		return b
	}
	for i := 2; i*i <= n; i++ {
		if n%i == 0 {
			primes[n] = false
			return false
		}
	}
	primes[n] = true
	return true
}
