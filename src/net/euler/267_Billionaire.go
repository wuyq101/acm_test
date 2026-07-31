package main

import (
	"fmt"
	"math"
)

/*
假设扔1000次之后，k次正面，1000-k次反面
最后总资产
W = (1+2f)^k * (1-f)^(1000-k) > 10^9
两边取对数

lnW = k*ln(1+2f)+(1000-k)*ln(1-f)
两边对f求导，当导数=0时，W取到极值

f' = 2k/(1+2f) - (1000-k)/(1-f)
f' = 0
2k/1+2f = (1000-k)/(1-f)

f = 3k-1000/2000
f>0, 所以 3k-1000>0, k>=334
*/

func main() {
	k := 334
	f := float64(3*k-1000) / 2000
	fmt.Printf("f=%.12f\n", f)
	for {
		if W(f, k) > 1e9 {
			break
		}
		k++
		f = float64(3*k-1000) / 2000
	}
	fmt.Printf("smallest k=%d, f=%.12f\n", k, f)
	// k至少为432, 计算扔1000次硬币，正面次数超过k次的概率
	// ∑C(1000,k) * 0.5^k * (0.5)^(1000-k)
	P := float64(0)
	for i := k; i <= 1000; i++ {
		P += C(1000, i)
	}
	fmt.Printf("P=%.12f\n", P)
}

// C(1000,k) * 0.5^k * (0.5)^(1000-k)
func C(n, k int) float64 {
	v := 1.0
	for i := 0; i < k; i++ {
		v *= float64(n-i) * 0.5 / float64(k-i)
	}
	for i := 0; i < 1000-k; i++ {
		v *= 0.5
	}
	return v
}

func W(f float64, k int) float64 {
	return math.Pow(1+2*f, float64(k)) * math.Pow(1-f, float64(1000-k))
}
