package main

import "fmt"

/*
https://projecteuler.net/problem=97

x ≡ 28433*2^7830457 + 1 (mod 10^10)
等价于方程组

1. x ≡ 28433*2^7830457 + 1 (mod 2^10)
2. x ≡ 28433*2^7830457 + 1 (mod 5^10)

1式的解为 x ≡ 1 (mod 1024)

2式的解为 x ≡ r (mod 9765625)
--使用欧拉定理
ϕ(2^10) = 5^10 - 5^9 = 7812500
2^7812500 ≡ 1 (mod 9765625)
其中7830457 = 7812500 + 17957

因此 x ≡ 28433*2^17957 + 1 (mod 9765625)
x ≡ 9523827 (mod 9765625)

1. x ≡ 1 (mod 1024)
2. x ≡ 9523827 (mod 9765625)

M = 10^10
m1 = 2^10, M1 = 5^10 = 9765625
M1y1 ≡ 1(mod 1024) y1 = 841

m2 = 5^10, M2 = 1024
M2y2 ≡ 1(mod 9765625) y2 = 1745224


x = 1*9765625*841 + 9523827*1024*1745224 mod 10000000000

*/

func main() {
	// brute force
	x := int64(28433)
	for i := 0; i < 7830457; i++ {
		x = (x * 2) % M
	}
	x = (x + 1) % M
	fmt.Printf("x=%d\n", x)

	y := ny(1024, 9765625)
	fmt.Printf("y=%d\n", y)
	r := pow(7830457)
	fmt.Printf("r=%d\n", r)
	r = (28433*r + 1) % M
	fmt.Printf("r=%d\n", r)
	x = (1*9765625*841 + 9523827*1024*1745224) % 10000000000
	fmt.Printf("x=%d\n", x)
}

// 求逆元 ay ≡ 1(mod b)
func ny(a, b int64) int64 {
	for i := int64(1); i < b; i++ {
		if (a*i)%b == 1 {
			return i
		}
	}
	return 0
}

var M = int64(10000000000)

func pow(a int) int64 {
	if a == 1 {
		return 2
	}
	if a%2 == 0 {
		b := pow(a / 2)
		return (b * b) % M
	}
	b := pow(a - 1)
	return (b * 2) % M
}
