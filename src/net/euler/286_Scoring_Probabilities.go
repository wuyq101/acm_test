package main

import "fmt"

/*
在距离x的地方，命中得1分的概率是 1-x/q, q>50, q是实数

从x=1到x=50, 共投篮50次。

得分20的概率是2%， 求q

求得分20的概率，然后求解q

得分20的概率是 扔了50次，进了20个。
考虑x=50的时候，如果在x=50的地方进球了，那么需要从前49次刚好得分19的地方过来。
要么x=50的地方，没有进球，但是前49次得分20.

P(50,20) = P(49,19)*(1-50/q) + P(49,20)*(50/q)

P(49,19) = P(48,18)*(1-49/q) + P(48,20)*(49/q)


P(1,1) = 1-1/q
P(1,0) = 1/q

P(i,j) = 0, 当j>i的时候




*/

func main() {
	L := 50.0
	R := 500.0
	alpha := 0.5
	Target := 0.02
	// grid search
	for i := L; i <= R; i += alpha {
		a, b := i, i+alpha
		pa, pb := P(a), P(b)
		if (pa-Target)*(pb-Target) < 0 {
			L, R = a, b
			fmt.Printf("find a=%f, b=%f, pa=%f, pb=%f\n", a, b, pa, pb)
			break
		}
	}
	// binary search
	cnt := 0
	for L < R {
		m := (L + R) / 2
		p := P(m)
		if p > Target {
			L = m
		} else {
			R = m
		}
		cnt++
		if cnt > 200 {
			break
		}
	}
	fmt.Printf("L=%.10f, R=%.10f, p=%.10f\n", L, R, P(L))
}

// 当q固定的时候，求得分20的概率
func P(q float64) float64 {
	// dp的方式来求 p(i,j)在i的时候，得分j的概率
	// hit = 1-i/q
	// miss = i/q
	// p(i,j) =  p(i-1,j-1)*hit + p(i-1,j)*miss
	dp := make([]float64, 51)
	dp[0] = 1.0
	for i := 1; i <= 50; i++ {
		miss := float64(i) / q
		hit := 1.0 - miss
		for j := 20; j >= 0; j-- {
			a := 0.0
			if j > 0 {
				a = dp[j-1]
			}
			dp[j] = a*hit + dp[j]*miss
		}
	}
	return dp[20]
}
