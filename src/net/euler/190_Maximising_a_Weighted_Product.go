package main

import (
	"fmt"
	"math"
)

/*
F(x) = x1 * x2^2 * ... * xi^i * ... * xm^m
∑xi = m

取对数，得 J(x) = ∑i*ln(xi)
最大化 J(x)
引入拉格朗日乘子 £(x,λ) = J(x) - λ * (∑xi - m)

∂£(x,λ) / ∂x = 0
i/xi - λ = 0
xi = i/λ
代入约束条件 ∑xi = m
1/λ * ∑i = m

λ = ∑i/m = (m+1)/2

--> xi = i/((m+1)/2) = 2i/(m+1)



*/

func main() {
	t := int64(0)
	for m := 2; m <= 15; m++ {
		fmt.Printf("m=%d, P(m)=%d\n", m, P(m))
		t += P(m)
	}
	fmt.Printf("total=%d\n", t)
}

func P(m int) int64 {
	// xi = 2i/(m+1)
	p := float64(1)
	for i := 1; i <= m; i++ {
		x := 2 * float64(i) / float64(m+1)
		p *= math.Pow(x, float64(i))
	}
	return int64(p)
}
