package main

import (
	"fmt"
	"math"
)

/*

f(x) = (N/x)^x
对x求导，分析f(x)的单调性

y = (N/x)^x, 两边取对数

ln(y) = x*(ln(N/x)) = x*(ln(N)-ln(x)) = x*ln(N) - x*ln(x)
两边求导

1/y * y' = ln(N) - (x * 1/x + ln(x)) = ln(N) - ln(x) - 1 = ln(N/x) - 1

y' = y * (ln(N/x) - 1) = (N/x)^x * (ln(N/x) - 1)

f(x) 在 y' = 0 取到最大值

y'=0 -->  ln(N/x) - 1 = 0

ln(N/x) = 1
N/x = e
x = N/e

g(x) = ln(f(x)) = xln(N/x)



x 在 (0,N/e)时 y'>0, f(x) 单调递增， 在 (N/e,N)时 y'<0, f(x) 单调递减
x为整数， 因此只要看 N/e, 两侧的整数

x在N/e的两侧，哪个值更大，只要判断g(x)，谁更大就可以

*/

func main() {
	sum := 0
	for n := 5; n <= 10000; n++ {
		sum += D(n)
	}
	fmt.Printf("sum=%d\n", sum)
}

func D(N int) int {
	a := math.Floor(float64(N) / math.E)
	b := math.Ceil(float64(N) / math.E)
	x := a
	if g(N, b) > g(N, a) {
		x = b
	}
	// 判断N/x是否是循环小数, 分母中只有2和5
	c := gcd(N, int(x))
	den := int(x) / c
	for den%2 == 0 {
		den /= 2
	}
	for den%5 == 0 {
		den /= 5
	}
	if den == 1 {
		return -N
	}
	return N
}

func g(N int, x float64) float64 {
	return x * math.Log(float64(N)/x)
}

func gcd(a, b int) int {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}
