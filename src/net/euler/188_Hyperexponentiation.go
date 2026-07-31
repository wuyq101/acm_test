package main

import "fmt"

/*

1777 †† 1855 的最后8个数字

††:高德纳箭号

欧拉定理：Euler's Totient Theorem
若 gcd(a,m) = 1,

a^b = a^(b (mod ø(m)) (mod m)


*/

func main() {
	a := 1777
	b := 1855
	M := 100000000
	result := Tetration(a, b, M)
	fmt.Printf("result = %d\n", result)
}

// a †† b (mod m)
func Tetration(a, b, m int) int {
	fmt.Printf("a=%d,b=%d,m=%d\n", a, b, m)
	if m == 1 {
		return 0
	}
	if b == 1 {
		return a % m
	}
	// a^(a††(b-1)) mod m = a^(a††(b-1) mod ø(m)) (mod m)
	k := Tetration(a, b-1, ø(m))
	// a^k mod m
	fmt.Printf("pow(%d,%d,%d)\n", a, k, m)
	return pow(a, k, m)
}

func pow(a, k, m int) int {
	if k == 0 {
		return 1
	}
	if k&1 == 0 {
		v := pow(a, k>>1, m)
		return v * v % m
	}
	return a * pow(a, k-1, m) % m
}

// ø(n) = n * (1-1/p1) * (1-1/p2) * ... * (1 - 1/pm)
func ø(n int) int {
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
		// 此时n是最后一个质因子
		result -= result / n
	}
	return result
}
