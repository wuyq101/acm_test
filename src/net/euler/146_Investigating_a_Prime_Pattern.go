package main

import (
	"fmt"
	"math/bits"
)

/*
http://projecteuler.net/problem=146

n^2 + 1
n^2 + 3
n^2 + 7
n^2 + 9
n^2 + 13
n^2 + 27
是连续的质数
求满足条件的，小于150*10^6的n的和

mod 2:
n^2 + 1是奇数， 所以n一定是偶数

n^2 = 0 (mod 2)   (1)

mod 3:
n^2 (mod 3)的可能结果只有 0,1
如果 n^2 = 0 (mod 3), 那么 n^2 + 3 = 0 (mod 3), 不满足
所以 n^2 = 1 (mod 3), -->  n != 0 (mod 3), n不能是3的倍数   (2)


mod 5:
n^2 (mod 5)的可能结果只有 0,1,4
如果 n^2 = 1 (mod 5), 那么 n^2 + 9 = 0 (mod 5), 不满足
如果 n^2 = 4 (mod 5), 那么 n^2 + 1 = 0 (mod 5), 不满足
所以 n^2 = 0 (mod 5), -->  n是5的倍数  (3)

结合条件（1）和（3）可知 n一定是10的倍数


mod 7:
n^2 (mod 7)的可能结果只有 0,1,2,4
如果 n^2 = 0 (mod 7), 那么 n^2 + 7 = 0 (mod 7), 不满足
如果 n^2 = 1 (mod 7), 那么 n^2 + 13 = 0 (mod 7), 不满足
如果 n^2 = 4 (mod 7), 那么 n^2 + 3 = 0 (mod 7), 不满足

所以 n^2 = 2 (mod 7), --> n = 3 0r 4 (mod 7) (4)

mod 11:
n^2 (mod 11)的可能结果只有 0,1,3,4,5,9

如果 n^2 = 0 (mod 11), 满足
如果 n^2 = 1 (mod 11), 满足
如果 n^2 = 3 (mod 11), 满足
如果 n^2 = 4 (mod 11), 那么 n^2 + 7 = 0 (mod 11), 不满足
如果 n^2 = 5 (mod 11), 满足
如果 n^2 = 9 (mod 11), 那么 n^2 + 13 = 0 (mod 11), 不满足
--> n^2 mod 11, 不能是4，9， --> n != (2,3,8,9) (mod 11)


观察中间剩余的其他数，必须是合数
n^2 + 21

因为n^2是偶数，所以 (+2,+4,+6,+8,+10,+12,+14,+16,+18,+20,+22,+24,+26)是偶数，所以这些数都是合数
因为n是10的倍数，（+5，+15，+25）一定是5的倍数，所以这些数都是合数
因为n^2 = 1 (mod 3), (+11, +17, +23)一定是3的倍数, 所以这些数都是合数
因为n^2 = 2 (mod 7), (+19) 一定是7的倍数，所以这些数都是合数

所以最后要要检查的是n^2+21必须是一个和数




*/

func main() {

	MAX := 150000000
	sum := 0
	for n := 10; n < MAX; n += 10 {
		// n != 0 (mod 3)
		if n%3 == 0 {
			continue
		}

		// n = 3 or 4 (mod 7)
		t := n % 7
		if !(t == 3 || t == 4) {
			continue
		}

		// n^2 mod 11, 不能是4，9， --> n != (2,3,8,9) (mod 11)
		t = n % 11
		if t == 2 || t == 3 || t == 8 || t == 9 {
			continue
		}

		// n^2 + 1
		s := int64(n)*int64(n) + 1
		if !isPrime(s) {
			continue
		}

		// n^2 + 3
		s = s + 2
		if !isPrime(s) {
			continue
		}

		// n^2 + 7
		s = s + 4
		if !isPrime(s) {
			continue
		}

		// n^2 + 9
		s = s + 2
		if !isPrime(s) {
			continue
		}

		// n^2 + 13
		s = s + 4
		if !isPrime(s) {
			continue
		}

		// n^2 + 21
		s = s + 8
		if isPrime(s) {
			continue
		}

		// n^2 + 27
		s = s + 6
		if !isPrime(s) {
			continue
		}

		fmt.Printf("n = %d\n", n)
		sum += n

	}
	fmt.Printf("sum=%d\n", sum)
}

var bases = []int64{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37}

// Miller-Rabin Test
func isPrime(n int64) bool {
	//fmt.Printf("is prime n = %d\n", n)
	// 费马小定理
	// 如果 n 是素数，且 a 是不能被 n 整除的正整数，那么必定满足
	// a^(n-1) = 1 (mod n)
	// 如果一个数连费马小定理都不能满足的话，那么它一定是合数

	// 二次探测定理
	// 如果 p 是素数，方程 x^2 = 1 (mod p)的解只有1 或者 p-1
	// 如果在计算中发现x^2 = 1 (mod p), 但x既不是1，也不是p-1, 那么p一定是合数

	// 1. 分解n-1 = d * 2^s
	d := n - 1
	s := 0
	for d%2 == 0 {
		d /= 2
		s++
	}

	//	fmt.Printf("d = %d, s = %d\n", d, s)

	// 2. 选择底数并计算初始值
	for _, a := range bases {
		x := pow(a, d, n)
		// 对于底数a，通过费马小定理测试
		if x == 1 || x == n-1 {
			continue
		}
		// 进行s-1次二次探测
		b := false
		for range s - 1 {
			x = pow(x, 2, n)
			if x == n-1 {
				b = true
				break
			}
		}
		if !b {
			return false
		}
	}

	return true
}

// a^d (mod n)
func pow(a, d, n int64) int64 {
	if d == 0 {
		return 1
	}
	if d%2 == 0 {
		v := pow(a, d/2, n)
		return mul(v, v, n)
	}
	v := pow(a, d-1, n)
	return mul(v, a, n)
}

func mul(a, b, n int64) int64 {
	hi, lo := bits.Mul64(uint64(a), uint64(b))
	_, rem := bits.Div64(hi, lo, uint64(n))
	return int64(rem)
}
