package main

import (
	"fmt"
	"strconv"
)

/**
 * @see https://projecteuler.net/problem=63
 * How many n-digit positive integers exist which are also an nth power?
 */
func main() {
	// n = 1
	// 1位数
	// 1 -- 9 9个

	// n = 2
	// 2位数，是平方
	// 16 25 36 49 64 81 6个

	// n = 3
	// 3位数
	// 100 --- 999
	// 其中是立方的有
	// 4^3 = 64, 5^3=126, 6^3=216

	// n
	// n位数
	// 10^(n-1) --- 10^n - 1
	// x ^ n, x < 10

	// x的范围一定是1到9
	// x=1
	// n=1, n=2不行

	cnt := 0
	// n
	for x := 1; x <= 10; x++ {
		n := 1
		for {
			// n位数的范围
			// 10^(n-1) --- 10^n - 1
			// x^n
			xs := strconv.Itoa(x)
			v := Pow(xs, n)
			if len(v) == n {
				fmt.Printf("x=%d,n=%d x^n=%s\n", x, n, v)
				n++
				cnt++
			} else {
				break
			}

		}
	}
	fmt.Printf("cnt=%v\n", cnt)

}
