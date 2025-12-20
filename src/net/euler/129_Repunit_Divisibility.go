package main

import "fmt"

/*
全一数 R(k) 可以用公式表示：
R(k) = (10^k - 1) / 9
题目要求 R(k) 能被 n 整除，即：
(10^k - 1) / 9 ≡ 0 (mod n)
这等价于：
10^k - 1 ≡ 0 (mod 9n)
由于 GCD(n, 10)=1，且 GCD(9, 10)=1，所以 GCD(9n, 10)=1。因此，上述同余式等价于：
10^k ≡ 1 (mod 9n)

结论：A(n) 就是满足 10^k ≡ 1 (mod 9n) 的最小正整数 k。在数论中，这个 k 被称为 10 模 9n 的阶（Order）。

存在性：根据欧拉定理，由于 GCD(10, 9n) = 1，有 10^φ(9n) ≡ 1 (mod 9n)，其中 φ 是欧拉函数。因此 A(n) 总是存在的，并且 A(n) 必定整除 φ(9n)。


*/

func main() {

	M := 1000000
	n := 3
	for {
		if gcd(n, 10) != 1 {
			n += 2
			continue
		}
		m := 9 * n
		p := Ω(m)
		if p >= M {
			k := A(m, p)
			if k >= M {
				fmt.Printf("find %d %d\n", n, k)
				return
			}
			fmt.Printf("n=%d, m=%d, p=%d, k=%d\n", n, m, p, k)
		}
		n += 2
	}
}

func isPrime(n int) bool {
	for i := 2; i*i <= n; i++ {
		if n%i == 0 {
			return false
		}
	}
	return true
}

func gcd(a, b int) int {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
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

// phi(n) = n * (1 - 1/p1) * (1 - 1/p2) * ...
// n = 20
// p = 2, result = 20 - 20/2 = 10, n = 5
// p = 3 ...
// result = 10 - 10/5 = 8  5,15
func phi_simple(n int) int {
	result := n
	for p := 2; p*p <= n; p++ {
		if n%p == 0 {
			result -= result / p
			for n%p == 0 {
				n /= p
			}
		}
	}
	if n > 1 {
		result -= result / n
	}
	return result
}
