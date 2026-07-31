package main

import (
	"fmt"
	"math"
	"math/bits"
)

/*
t(n) = 2*n*n-1
求t(n)是质数的个数，当n<=N时，t(n)是质数的个数
N=5*10^7


t(n) = (√2n+1)*(√2n-1)


假设t(n) = 2*n*n-1不是质数 = p*q

2*n*n = p*q + 1
n*n = (p*q+1)/2

两个质数乘积+1的一半，是一个平方数。

假设n = p1^a1 * p2^a2 * ... * pk^ak

2n*n - 1 = 2*p1*2a1*p2^2a2*...*pk^2ak-1

= n*n + n*n - 1
= n*n + (n+1)(n-1)


如果N能够被p整除
2*n*n - 1 = 0 (mod p)
2*n*n = 1 (mod p)
2*n*n = p+1 (mod p)
两边除以2
n*n = (p+1)/2 (mod p)

用Tonelli-Shanks算法计算 x^2 = a (mod p), 其中a = (p+1)/2
如果方程有解，则N一定不是质数



*/

func main() {
	N := int64(5e7)
	maxT := 2*N*N - 1
	maxP := math.Sqrt(float64(maxT))
	primes := genPrimes(int(maxP))
	fmt.Printf("maxP=%d, len=%d\n", int64(maxP), len(primes))
	t := make([]bool, N+1)
	for _, v := range primes {
		if v == 2 {
			continue
		}
		// x^2 = (p+1)/2 (mod p)， 如果有根，x1,x2
		p := int64(v)
		x1 := TonelliShanks((p+1)/2, p)
		if x1 == p {
			continue
		}
		x2 := p - x1
		for x := x1; x <= N; x += p {
			if 2*x*x-1 > p {
				t[x] = true
			}
		}
		for x := x2; x <= N; x += p {
			if 2*x*x-1 > p {
				t[x] = true
			}
		}
	}
	cnt := 0
	for n := int64(2); n <= N; n++ {
		if !t[n] {
			cnt++
		}
	}
	fmt.Printf("cnt=%d\n", cnt)
	return
}

func TonelliShanks(a, p int64) int64 {
	// 欧拉准则
	if !EulerCriterion(a, p) {
		// 解的范围在[0,p-1], 这里返回p表示无解
		return p
	}
	// S==1时，p-1 = 2Q
	// t = a^(p-1)/2 = 1, 可以直接返回R
	// R = a^(p+1)/4)
	// 分解p-1 = Q*2^S
	// p=3(mod 4)时，S=1
	// p = 4k+3, p-1=4k+2=2*(2k+1), 2k+1肯定是奇数Q，所以S=1
	if p%4 == 3 {
		return pow(a, (p+1)/4, p)
	}

	Q := p - 1
	S := 0
	for Q%2 == 0 {
		S++
		Q /= 2
	}
	R := pow(a, (Q+1)/2, p)
	t := pow(a, Q, p)
	// 查找z,非二次剩余
	z := int64(2)
	for i := int64(2); i <= p-1; i++ {
		if !EulerCriterion(i, p) {
			z = i
			break
		}
	}
	c := pow(z, Q, p)
	M := S
	for t != 1 {
		k := t
		m := 0
		for i := 0; i < M; i++ {
			if k == 1 {
				m = i
				break
			}
			k = mulMod(k, k, p)
		}
		b := pow(c, int64(1<<(M-m-1)), p)
		b2 := pow(b, 2, p)
		t = mulMod(t, b2, p) // t = t*b^2
		R = mulMod(R, b, p)
		c = b2
		M = m
	}
	return R
}

func EulerCriterion(a, p int64) bool {
	return pow(a, (p-1)/2, p) == 1
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
