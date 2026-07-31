package main

import (
	"fmt"
	"math/bits"
)

/*
f(n) = n^2-3n-1

f(n) = 0 (mod p^2)
求n。

假设N是方程的一个解，那么
f(N) = N^2-3N-1 = 0 (mod p^2)

由于p^2|N^2-3N-1, 那么p也一定整除N^2-3N-1， p|N^2-3N-1.
可见N是方程 x^2-3x-1 = 0 (mod p) 的一个解.
通过配方法，
4x^2-12x-4 = 0 (mod p)
4x^2-12x+9-13 = 0 (mod p)
(2x-3)^2 = 13 (mod p)

设2x-3=t, t^2=13 (mod p), 通过Tonelli-Shanks算法计算t的值。

2x-3 = t (mod p)
x = (t+3)*2^-1 (mod p),
2^-1 = (p+1)/2

x = (t+3)*(p+1)/2 (mod p)

// 解出了x之后，记为n1，n2
N = n1+kp, 只要求出了k，也就求出了N

(n1+kp)^2-3(n1+kp)-1 = 0 (mod p^2)
n1^2+2n1kp+k^2p^-3n1-3kp-1 = 0 (mod p^2)
n1^2-3n1-1 = M*p, 因为n1是方程f(n) = 0 (mod p)的一个解。所以可以求出M
k^2*p^2 这一项一定整除，舍去。

M*p + kp(2n1-3) = 0 (mod p^2)
将式子整体除以p，问题重新降为到模p的空间。
M + k(2n1-3) = 0 (mod p)
k*(2n1-3) = -M (mod p)
k = -M * (2n1-3)^-1 (mod p)





*/

func main() {
	L := 1e7
	primes := genPrimes(int(L))
	fmt.Printf("len=%d\n", len(primes))
	sum := int64(0)
	for _, v := range primes {
		p := int64(v)
		if p == 2 {
			continue
		}
		// x^2-3x-1 = 0 (mod p)
		// (2x-3)^2 = 13 (mod p)
		n1 := TonelliShanks(13, p)
		if n1 == p {
			continue
		}
		n2 := p - n1
		i2 := (p + 1) / 2
		x1 := mulMod(n1+3, i2, p)
		x2 := mulMod(n2+3, i2, p)
		M := (x1*x1 - 3*x1 - 1) / p
		M = (M%p + p) % p
		i := inv(n1, p)
		k := mulMod(p-M, i, p)
		// N = n1+kp
		N1 := x1 + k*p
		M = (x2*x2 - 3*x2 - 1) / p
		M = (M%p + p) % p
		i = inv(n2, p)
		k = mulMod(p-M, i, p)
		N2 := x2 + k*p
		sum += min(N1, N2)
	}
	fmt.Printf("sum=%d\n", sum)

}

func min(a, b int64) int64 {
	if a < b {
		return a
	}
	return b
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

func TonelliShanks(a, p int64) int64 {
	a %= p
	// 欧拉准则
	if !EulerCriterion(a, p) {
		// 解的范围在[0,p-1], 这里返回p表示无解
		return p
	}
	// 分解p-1 = Q*2^S
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

func inv(a, p int64) int64 {
	// a^(p-1) = 1 (mod p)
	return pow(a, p-2, p)
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
