package main

import (
	"fmt"
	"math/bits"
)

/*

https://projecteuler.net/problem=271

S(n) = ∑x, x^3=1 (mod n) 1<x<n

S(91) = 9+16+22+29+53+74+79+81


x^3=1 (mod n)

整除具有传递性
x^3-1 = 0 (mod n), x^3-1是n的倍数

n | (x^3-1)
设 x^3-1 = k*n

然后p是n的一个质因子， 设 p*m = n, 代入上式可得
x^3 - 1 = k * p * m = (k*m)*p
可见 x^3-1是p的倍数
p ｜(x^3-1)




对n进行因式分解，n=p1*p2*p3....*pk.
分别求解
x^3=1 (mod p1)
x^3=1 (mod p2)
x^3=1 (mod p3)
...
x^3=1 (mod pk)

得到
x = a1 (mod p1)
x = a2 (mod p2)
...
x = ak (mod pk)

然后用中国剩余定理求的整个方程组的解
*/

func main() {
	//N := int64(91)
	N := int64(13082761331670030)
	f := factors(N)
	fmt.Printf("factors = %v\n", f)
	A := make([][]int64, 0)
	for _, p := range f {
		list := cube(p)
		A = append(A, list)
	}

	a := make([]int64, 0)
	Sigma = 0
	dfs(A, f, a, 0)
	fmt.Printf("Sigma = %d\n", Sigma-1)
}

var Sigma = int64(0)

func dfs(A [][]int64, f, a []int64, idx int) {
	if idx == len(f) {
		x := crt(a, f)
		//fmt.Printf("a=%v, f=%v, x=%d\n", a, f, x)
		Sigma += x
		return
	}
	for i := 0; i < len(A[idx]); i++ {
		ai := A[idx][i]
		a = append(a, ai)
		dfs(A, f, a, idx+1)
		a = a[:len(a)-1]
	}
}

func crt(a, m []int64) int64 {
	// 中国剩余定理, 求整个方程组的解
	// fmt.Printf("CRT a=%v, m=%v\n", a, m)
	// 1. 计算M和Mi, 正交隔离
	M := int64(1)
	for _, mi := range m {
		M *= mi
	}
	Mi := make([]int64, 0)
	for _, mi := range m {
		Mi = append(Mi, M/mi)
	}

	// 2. 求逆元ti, Mi * ti = 1 (mod mi)
	ti := make([]int64, len(m))
	for i := 0; i < len(m); i++ {
		ti[i] = inv(Mi[i], m[i])
	}

	// 3. 线性叠加
	sum := int64(0)
	for i := 0; i < len(a); i++ {
		sum += mulmod(a[i]*ti[i], Mi[i], M)
		sum = sum % M
	}
	return sum
}

func mulmod(a, b, p int64) int64 {
	hi, lo := bits.Mul64(uint64(a), uint64(b))
	_, rem := bits.Div64(hi, lo, uint64(p))
	return int64(rem)
}

func inv(a, b int64) int64 {
	a = a % b
	// a*x + b*y = gcd(a,b) = 1
	_, x, _ := ExGCD(a, b)
	x = (x + b) % b
	return x
}

// 扩展欧几里得,返回g,x,y
// a*x + b*y = g
func ExGCD(a, b int64) (int64, int64, int64) {
	// 最后一步的时候
	// a*1 + 0*0 = g (g=a)
	if b == 0 {
		return a, 1, 0
	}
	g, x, y := ExGCD(b, a%b)
	// 上一层返回的内容
	// b*x + (a%b)*y = g
	// a%b = a - (a/b)*b
	// 要将这些参数整理乘 a*X + b*Y = g的形式
	// 将余数用a,b表示，代入，得到
	// b*x + (a - (a/b)*b)*y = g
	// b*x + a*y - (a/b)*b*y = g
	// a*y + b*x - (a/b)*b*y = g
	// a*y + b*(x-(a/b)*y) = g
	// a*X + b*Y = g
	// X = y, Y = x - (a/b)*y
	return g, y, x - (a/b)*y
}

// x^3=1 (mod p)
func cube(p int64) []int64 {
	list := make([]int64, 0)
	for x := int64(1); x < p; x++ {
		if x*x*x%p == 1 {
			list = append(list, x)
		}
	}
	return list
}

func factors(n int64) []int64 {
	p := int64(2)
	list := make([]int64, 0)
	for n > 1 {
		if n%p == 0 {
			q := int64(1)
			for n%p == 0 {
				q *= p
				n /= p
			}
			list = append(list, q)
		}
		p++
	}
	return list
}
