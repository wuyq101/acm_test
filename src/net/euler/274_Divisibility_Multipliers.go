package main

import (
	"fmt"
	"math/bits"
)

/*
假设n = 10a+b
f(n) = a+m*b
gcd(10,p) = 1
10*f(n) = 10a+10mb
10a = n-b 代入

10*f(n) = n-b + 10mb (mod p)
10*f(n) = n + (10m-1)*b (mod p)

当f(n)=0(mod p) 当且仅当n=0(mod p)成立，说明上式中 10m-1 = 0 (mod p)， 对任意的b都成立。
因此 10m = 1 (mod p),
即m是10的逆元

根据费马小定理
10^(p-1) = 1 (mod p)
10 * 10^(p-2) = 1 (mod p)
所以 m = 10^(p-2) (mod p)



方法二：扩展欧几里得求逆元

10m = 1 (mod p)
10m-1 = 0 (mod p)
10m-1 能被p整除,假设 10m-1 = -y*p

10m-1 = -yp = 0 (mod p)

10m-1 = -yp
10m+yp = 1, 已知 gcd(10,p) = 1
通过ExGCD, 可以求得  10*X + p*Y = 1
所以这里的X就是m，也就是10的逆元




*/

func main() {
	primes := genPrimes(int(N))
	primes = primes
	sum := int64(0)
	for _, p := range primes {
		if p == 2 || p == 5 {
			continue
		}
		m := inv(10, p)
		sum += m
	}
	fmt.Printf("sum=%d\n", sum)

}

func ExGCD(a, b int) (int, int, int) {
	if b == 0 {
		return a, 1, 0
	}
	g, x, y := ExGCD(b, a%b)
	return g, y, x - (a/b)*y
}

var N = 1e7

func inv(n, p int) int64 {
	// return pow(int64(n), int64(p-2), int64(p))
	_, x, _ := ExGCD(n, p)
	x = (x%p + p) % p
	return int64(x)
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
