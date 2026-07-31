package main

import "fmt"

/*
https://projecteuler.net/problem=155
*/

func main() {
	N := 18
	dp := make([][]Frac, N+1)
	dp[1] = []Frac{{1, 1}}

	for i := 2; i <= N; i++ {
		// 对上一层的每一个，进行并联或者串联
		next := make([]Frac, 0)
		m := make(map[Frac]bool)
		add := func(f Frac) {
			if !m[f] {
				m[f] = true
				next = append(next, f)
			}
		}
		// i = j + k
		for j := 1; j <= i/2; j++ {
			// j层的每一个和k层的每一个组合
			for _, f1 := range dp[j] {
				for _, f2 := range dp[i-j] {
					add(series(f1, f2))
					add(parallel(f1, f2))
				}
			}
		}
		dp[i] = next
		if i <= 10 {
			fmt.Printf("%d %v\n", i, dp[i])
		}
	}
	sum := 0
	m := make(map[Frac]bool)
	for i := 1; i <= N; i++ {
		for _, f := range dp[i] {
			if !m[f] {
				m[f] = true
				sum++
			}
		}
	}
	fmt.Printf("sum=%d\n", sum)
}

type Frac struct {
	num, den int64
}

func (f Frac) String() string {
	return fmt.Sprintf("%d/%d", f.num, f.den)
}

func (f Frac) add(a Frac) Frac {
	num := f.num*a.den + a.num*f.den
	den := f.den * a.den
	g := gcd(num, den)
	return Frac{num / g, den / g}
}

func (f Frac) inverse() Frac {
	return Frac{f.den, f.num}
}

// 1/c = 1/a + 1/b
// c = (a + b)/ab
func series(a, b Frac) Frac {
	return a.inverse().add(b.inverse()).inverse()
}

func parallel(a, b Frac) Frac {
	return a.add(b)
}

func gcd(a, b int64) int64 {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}
