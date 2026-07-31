package main

import (
	"fmt"
	"math/bits"
)

/*
N(p,q) = ∑n=0..q, T(n)*p^n

S(0) = 290797
S(n+1) = S(n)^2 (mod 50515093)
T(n) = S(n) (mod p)


NF(p,q) 是 阶乘N(p,q)!中p的因子个数


分析，factorial(n)中p的因子个数，只和n有关，
n! = n*(n-1)*(n-2)*...*1
n!中p的因子个数
1---n中，p的倍数，p^2的倍数，p^3的倍数
fp(p,n)代表n!中p的因子个数


NF(3,10000) = 624955285 (mod 3^20)

N(3,10000) = ∑n=0..10000, T(n)*3^n = T(0)*1 + T(1)*3 + T(2)*9 + ... + T(10000)*3^10000

假设N(3,10000)=X

X! 这个巨大的数中，有多少个3的因子
X中包含多少3的倍数
X/3 = T(1) + T(2)*3 + ... + T(q)*3^(q-1)
X/9 =        T(2)   + T(3)*3 + ... + T(q)*3^(q-2)
X/27=                 T(3)   + T(4)*3 + ... + T(n)*3^(n-3)

重新整理：
T(1): 1
T(2): 1 + p
T(3): 1 + p + p^2
T(4): 1 + p + p^2 + p^3
...
T(q): 1 + p + p^2 + ... + p^(q-1)

NF(p,q) = ∑n=1..q, T(n)*(1+p+p^2+...+p^(n-1))


最后要求的结果R = NF(p,q) (mod p^k)
所以结果中那些p的次数>=k的，都可以忽略掉


*/

func main() {
	NF(3, 10000, 20)
	NF(61, 10000000, 10)
}

func NF(p, q, k int64) int64 {
	K := pow(p, k)
	S := int64(290797)
	MS := int64(50515093)
	R := int64(0)
	P := int64(1)
	for n := int64(1); n <= q; n++ {
		S = mulMod(S, S, MS)
		T := S % p
		R = addMod(R, mulMod(T, P, K), K)
		P = mulMod(P, p, K)
		P = addMod(P, 1, K)
	}
	fmt.Printf("NF = %v\n", R)
	return R
}

func pow(p, k int64) int64 {
	if k == 0 {
		return 1
	}
	if k&1 == 1 {
		return p * pow(p, k-1)
	}
	v := pow(p, k/2)
	return v * v
}

func mulMod(a, b, m int64) int64 {
	hi, lo := bits.Mul64(uint64(a), uint64(b))
	return int64(bits.Rem64(hi, lo, uint64(m)))
}

func addMod(a, b, m int64) int64 {
	return int64((uint64(a) + uint64(b)) % uint64(m))
}
