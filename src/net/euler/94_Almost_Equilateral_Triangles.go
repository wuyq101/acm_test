package main

import "fmt"

/*
列举所有的勾股数，然后判断
a = m^2 - n^2
b = 2mn
c = m^2 + n^2

// 斜边c
// a,b是两个直角边
// 构成一个等腰三角形： (2a,c,c),或者 (2b,c,c)
// (2a,c,c): 周长 = 2a + c + c = 2(a+c)
// 2a = c+1 或者 c-1
*/
func main() {
	M := 20000
	MAX := 1000000000
	sum := 0
	for m := 1; m <= M; m++ {
		for n := m - 1; n > 0; n-- {
			if gcd(m, n) != 1 || (m-n)%2 == 0 {
				continue
			}
			a := m*m - n*n
			b := 2 * m * n
			c := m*m + n*n
			v := check(a, b, c)
			if v > 0 {
				flag := v >= MAX
				if !flag {
					sum += v
				}
				fmt.Printf("for -------- find m=%d, n=%d %d %d %d %d %v\n", m, n, a, b, c, v, flag)
			}
		}
	}
	fmt.Printf("total %d\n", sum)
}

func check(a, b, c int) int {
	//
	if 2*a == c+1 || 2*a == c-1 {
		return 2 * (a + c)
	}
	if 2*b == c+1 || 2*b == c-1 {
		return 2 * (b + c)
	}
	return 0
}

func gcd(m, n int) int {
	for m%n != 0 {
		m, n = n, m%n
	}
	return n
}
