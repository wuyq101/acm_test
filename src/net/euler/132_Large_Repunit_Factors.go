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
*/

func main() {
	k := 9
	i := 3
	list := make([]int, 0)
	for {
		if isPrime(i) {
			m := 9 * i
			if pmod(k, m) == 1 {
				list = append(list, i)
				fmt.Printf("%d\n", i)
				if len(list) == 40 {
					break
				}
			}
		}
		i += 2
	}
	fmt.Printf("sum=%d\n", sm(list))
}

func sm(list []int) int {
	sum := 0
	for _, v := range list {
		sum += v
	}
	return sum
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

// 10^(10^k) mod m
func pmod(k, m int) int {
	p := Ω(m)
	k = mod(k, p)
	return mod(k, m)
}

func isPrime(n int) bool {
	for i := 2; i*i <= n; i++ {
		if n%i == 0 {
			return false
		}
	}
	return true
}
