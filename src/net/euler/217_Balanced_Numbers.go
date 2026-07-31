package main

import (
	"fmt"
	"math"
)

/*
平衡数
一个数字的前半部分数字和与后半部分数字和相等。

g(n), 长度为n的平衡数之和


cnt0[L][K] 长度为L，数字和是K的数字个数, 第一个可以为0
sum0[L][K], 上面这些所有数字的和

cnt[L][K], 长度为L，数字和是K的数字个数，第一个不为0
sum[L][K], 上面这些所有数字的和


规定每个数字，只能是在后面添加一个数字构造，避免重复。比如122， 只能是12后面添加2构造，不能是1添加在22之前构造。


*/

func main() {
	MAXL := 47
	// 10的幂次
	pow10 := make([]int64, MAXL+1)
	pow10[0] = 1
	for i := 1; i <= MAXL; i++ {
		pow10[i] = mul(pow10[i-1], 10)
	}
	cnt0, sum0 := mkArray(MAXL / 2)
	dp(cnt0, sum0, true)
	// 前导不为0
	cnt, sum := mkArray(MAXL / 2)
	dp(cnt, sum, false)
	T := int64(45)
	// 开始求解T(n)
	for L := 2; L <= MAXL; L++ {
		halfL := L / 2
		if L&1 == 0 {
			// 偶数的长度
			// 左边有X种，右边有Y种
			for k := 1; k <= halfL*9; k++ {
				X := cnt[halfL][k]
				Sx := sum[halfL][k]
				Y := cnt0[halfL][k]
				Sy := sum0[halfL][k]
				f := pow10[halfL]
				p1 := mul(Sx, mul(Y, f))
				p2 := mul(Sy, X)
				s := add(p1, p2)
				T = add(T, s)
			}
		} else {
			// 奇数长度
			for k := 1; k <= halfL*9; k++ {
				X := cnt[halfL][k]
				Sx := sum[halfL][k]
				Y := cnt0[halfL][k]
				Sy := sum0[halfL][k]
				f1 := pow10[halfL]
				f2 := pow10[halfL+1]
				// Sx*10^(halfL+1)*Y*10
				p1 := mul(mul(Sx, f2), mul(Y, 10))
				// Sy*X*10
				p2 := mul(Sy, mul(X, 10))
				// 45*f1*X*Y
				p3 := mul(mul(45, f1), mul(X, Y))
				s := add(p1, add(p2, p3))
				T = add(T, s)
			}
		}
	}
	fmt.Printf("T=%d\n", T)
}

func mkArray(L int) ([][]int64, [][]int64) {
	cnt := make([][]int64, L+1)
	sum := make([][]int64, L+1)
	for L := 0; L < len(cnt); L++ {
		cnt[L] = make([]int64, L*9+1)
		sum[L] = make([]int64, L*9+1)
	}
	return cnt, sum
}

func dp(cnt [][]int64, sum [][]int64, leadingZero bool) {
	// 长度为1，初始化
	if leadingZero {
		cnt[1][0] = 1
	}
	for j := 1; j <= 9; j++ {
		cnt[1][j] = 1
		sum[1][j] = int64(j)
	}
	for L := 2; L < len(cnt); L++ {
		for k := 0; k <= L*9; k++ {
			// 长度是L, 数字和是k的cnt和sum
			// 在前面数字的基础上添加一个数字
			for j := 0; j <= 9; j++ {
				if L-1 >= 1 && k-j >= 0 && k-j <= (L-1)*9 {
					cnt[L][k] = add(cnt[L][k], cnt[L-1][k-j])
					// 如果前面是0种，这里也会拦截，不会出现前导0
					s := add(mul(sum[L-1][k-j], 10), mul(int64(j), cnt[L-1][k-j]))
					sum[L][k] = add(sum[L][k], s)
				}
			}
		}
	}
}

func add(a, b int64) int64 {
	return (a%M + b%M) % M
}

func mul(a, b int64) int64 {
	return (a % M) * (b % M) % M
}

var M = int64(math.Pow(3, 15))
