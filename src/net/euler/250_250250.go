package main

import (
	"fmt"
	"math/bits"
)

/*
S = {1^1,2^2,3^3,...,250^250,...,250250^250250}
求子集个数，子集和能被250整除

250 = 25*10 = 2^1 * 5^3

解法1:

将S化简为250的余数
然后是一个K重背包问题。


解法2：
多项式乘法。

余数为i，记为x^i, 一共有多少个记录为系数C
那么最后的多项式 P(x) = C0*x^0 + C1*x^1 + C2*x^2 + ... + C249*x^249

假设余数为i的数字一共有R[i]=k个
那么这个R[i]个数字，对最后的结果影响就是(1+x^i)^k

最后的多项式，就是将这250个多项式相乘。其中x^0的系数，就是和能够被250整数的子集的个数。
用 [250]int64来表示一个多项式，下标就是指数，数值就是系数。
一次乘法是250^2次计算。整体复杂度是 250^2*log(N)






*/

func main() {
	N := int64(250250)
	R := make([]int, 250)
	for i := int64(1); i <= N; i++ {
		if i%250 == 0 {
			R[0]++
			continue
		}
		r := pow(i, i, m)
		R[r]++
	}
	//	fmt.Printf("%v\n", R)
	// 假设子集和为K，那么这样的子集个数为dp[K]
	dp := make([]int64, 250)
	dp[0] = 1
	tmp := make([][]int64, 2)
	tmp[0] = make([]int64, 250)
	tmp[1] = make([]int64, 250)
	idx := 0
	for i := 1; i < 250; i++ {
		// 余数为i的数字一共有R[i]个
		if R[i] == 0 {
			continue
		}
		// i 一共可以使用k次
		for range R[i] {
			next := tmp[1-idx]
			for j := 0; j < 250; j++ {
				// (i+k)%250 = j
				k := (j - i + 250) % 250
				next[j] = addMod(dp[j], dp[k], M)
			}
			idx = 1 - idx
			dp = next
		}
	}
	p2 := pow(2, int64(R[0]), M)
	dp[0] = mulMod(dp[0], p2, M)
	dp[0] = addMod(dp[0], M-1, M)
	fmt.Printf("sum=%d\n", dp[0])
}

func addMod(a, b, c int64) int64 {
	return int64((uint64(a) + uint64(b)) % uint64(c))
}

var m = int64(250)
var M = int64(1e16)

func pow(a, p, m int64) int64 {
	if p == 0 {
		return 1
	}
	if p&1 == 0 {
		v := pow(a, p/2, m)
		return mulMod(v, v, m)
	}
	return mulMod(a, pow(a, p-1, m), m)
}

func mulMod(a, b, m int64) int64 {
	hi, lo := bits.Mul64(uint64(a), uint64(b))
	return int64(bits.Rem64(hi, lo, uint64(m)))
}
