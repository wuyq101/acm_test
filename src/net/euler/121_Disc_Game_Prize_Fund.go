package main

import (
	"fmt"
	"math/big"
)

func main() {
	// dp[i][j] 代表第i轮，抽到j个蓝色的概率
	N := 15
	dp := make([][]Fraction, N+1)
	for i := 0; i <= N; i++ {
		dp[i] = make([]Fraction, N+1)
	}
	dp[0][0] = Fraction{1, 1}
	for i := 1; i <= N; i++ {
		for j := 0; j <= i; j++ {
			// 第i轮，一共有i+1个球，有i个红球，1个篮球，
			// 抽红色的概率: i / (i + 1), 抽蓝色的概率: 1 / (i + 1)
			// dp[i][j] = dp[i-1][j-1] * 1 / (i + 1) + dp[i-1][j] * i / (i + 1)
			red := Fraction{int64(i), int64(i + 1)}
			blue := Fraction{1, int64(i + 1)}
			if j >= 1 {
				dp[i][j] = Mul(dp[i-1][j-1], blue)
			}
			// 抽红球
			dp[i][j] = Add(dp[i][j], Mul(dp[i-1][j], red))
		}
	}
	for i := 1; i <= N; i++ {
		fmt.Printf("dp[%d]:\t", i)
		for j := 0; j <= i; j++ {
			fmt.Printf("%v\t", dp[i][j])
		}
		fmt.Println()
	}
	f := Fraction{0, 1}
	for i := 8; i <= N; i++ {
		f = Add(f, dp[N][i])
	}
	fmt.Println(f)
	result := int(float64(f.denominator) / float64(f.numerator))
	fmt.Printf("prize:%d\n", result)
}

type Fraction struct {
	numerator   int64
	denominator int64
}

func (f Fraction) String() string {
	if f.numerator == 0 {
		return "0"
	}
	if f.denominator == 1 {
		return fmt.Sprintf("%d", f.numerator)
	}
	return fmt.Sprintf("%d/%d", f.numerator, f.denominator)
}

func Add(a, b Fraction) Fraction {
	//	fmt.Printf("%v + %v\n", a, b)
	if a.numerator == 0 {
		return b
	}
	if b.numerator == 0 {
		return a
	}
	/*
		n := a.numerator*b.denominator + b.numerator*a.denominator
		d := a.denominator * b.denominator
		g := gcd(n, d)
	*/
	n1 := big.NewInt(a.numerator)
	n1.Mul(n1, big.NewInt(b.denominator))
	n2 := big.NewInt(b.numerator)
	n2.Mul(n2, big.NewInt(a.denominator))
	n := big.NewInt(0)
	n.Add(n1, n2)

	d := big.NewInt(a.denominator)
	d.Mul(d, big.NewInt(b.denominator))
	g := big.NewInt(0)
	g.GCD(nil, nil, n, d)
	n.Div(n, g)
	d.Div(d, g)

	return Fraction{n.Int64(), d.Int64()}
}

func Mul(a, b Fraction) Fraction {
	//	fmt.Printf("%v * %v\n", a, b)
	if a.numerator == 0 {
		return a
	}
	if b.numerator == 0 {
		return b
	}
	/*
		n := a.numerator * b.numerator
		d := a.denominator * b.denominator
		g := gcd(n, d)
	*/
	n := big.NewInt(a.numerator)
	n.Mul(n, big.NewInt(b.numerator))
	d := big.NewInt(a.denominator)
	d.Mul(d, big.NewInt(b.denominator))
	g := big.NewInt(0)
	g.GCD(nil, nil, n, d)
	n.Div(n, g)
	d.Div(d, g)

	return Fraction{n.Int64(), d.Int64()}
}

func gcd(a, b int64) int64 {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}
