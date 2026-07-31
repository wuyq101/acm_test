package main

import (
	"fmt"
	"math/bits"
)

/*
p>=5,p是质数
威尔逊定理
(p-1)! = p-1 (mod p)

(p-2)! = 1 (mod p)


(p-3)! = x (mod p)
(p-2)*(p-3)! = -2x = 1 (mod p)
x = (p-1)/2 (mod p)

(p-3)! = (p-1)/2 (mod p)


(p-4)! =x (mod p)
(p-3)*x = (p-1)/2 (mod p)
2(p-3)*x = -1 (mod p)
2px - 6x = -1 (mod p)
6x=1 (mod p)
求6的逆元
当 p=1 (mod 6)时
可知p-1时6的倍数
两边乘以-1
-6x = -1 (mod p)
-1 等价于 p-1
-6x = p-1 (mod p)
x = -(p-1)/6 (mod p)
将x=x+p转为正数
x= (5p+1)/6 (mod p)

当p=5(mod 6)时
p+1是6的倍数
6x=1 (mod p)
1等价与 p+1 (mod p)
6x = 1 = 1+p (mod p)
x = (p+1)/6 (mod p)

(p-4)! (mod p)  6的逆元


(p-5)!=x (mod p)
(p-3)(p-4)*x = (p-1)/2 (mod p)

(p-5)! mod p = 24的逆元


在mod p的情况下，k的逆元通用解法

k^(p-1) = 1 (mod p)
k * k^(p-2) = 1 (mod p)
所以k的逆元 = k^(p-2) mod p



S(p) = (p-1)! + (p-2)! + (p-3)! + (p-4)! + (p-5)! (mod p)
= -1 + 1 - 1/2 + 1/6 - 1/24 (mod p)
= -3/8 (mod p)
求8的逆元* -3

假设 8x=1 (mod p)
当 p=1 (mod 8)时
7p+1是8的倍数， x=(7p+1)/8

当 p=3 (mod 8)时，x=(5p+1)/8
p=5 (mod 8), x=(3p+1)/8
p=7 (mod 8), x=(p+1)/8


解法二：
x = -3/8 (mod p)
8x = -3 (mod p)
寻找一个k，
8x = kp-3 (mod p)
如果kp-3 = 0 mod 8, 即kp-3是8的倍数，
则x = (kp-3)/8

kp-3=0 (mod 8), 那么如何找到这个k呢？
kp = 3 (mod 8)
两边同时乘以p
k*p*p = 3p (mod 8)
任何>=5的质数都是奇数，然后奇数的平方对8取余都是1
1^2 = 1 (mod 8)
3^2 = 1 (mod 8)
5^2 = 1 (mod 8)
7^2 = 1 (mod 8)

所以 k = 3p (mod 8)




*/

func main() {
	fmt.Printf("S(7)=%d\n", S(7))
	fmt.Printf("inv(6, 7)=%d\n", inv(6, 7))
	fmt.Printf("inv(24, 7)=%d\n", inv(24, 7))
	N := int(1e8)
	primes := genPrimes(N)
	sum := int64(0)
	for _, p := range primes[2:] {
		k := p * 3 % 8
		sum += int64(k*p-3) / 8
	}
	fmt.Printf("sum = %d\n", sum)

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

func S(p int) int64 {
	// (p-1)! = p-1 (mod p)
	s := p - 1
	// (p-2)! = 1 (mod p)
	s += 1
	// (p-3)! = (p-1)/2 (mod p)
	s += (p - 1) / 2
	// (p-4)!  6的逆元
	s += inv(6, p)
	// (p-5)! -24的逆元
	s += p - inv(24, p)
	return int64(s % p)
}

func inv(k, p int) int {
	k = k % p
	return pow(k, p-2, p)
}

func pow(a, d, n int) int {
	if d == 0 {
		return 1
	}
	if d%2 == 0 {
		v := pow(a, d/2, n)
		return mul(v, v, n)
	}
	v := pow(a, d-1, n)
	return mul(a, v, n)
}

func mul(a, b, n int) int {
	hi, lo := bits.Mul64(uint64(a), uint64(b))
	_, rem := bits.Div64(hi, lo, uint64(n))
	return int(rem)
}
