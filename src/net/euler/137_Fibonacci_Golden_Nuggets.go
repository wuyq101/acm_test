package main

import "fmt"

/*
https://projecteuler.net/problem=136

x = (√(5n^2+2n+1)-n-1) / 2n
如果x是有理数，说明 5n^2+2n+1是完全平方数

设 5n^2+2n+1 = p^2
4n^2 + n^2+2n+1 = p^2
(2n)^2 + (n+1)^2 = p^2

// 枚举所有的勾股数字 a^2+b^2 = c^2
*/
func main() {
	ggs()
}

func isSquare(p int) bool {
	for i := 1; i*i <= p; i++ {
		if i*i == p {
			return true
		}
	}
	return false
}

func ggs() {
	/*
		a := m*m - n*n
		b := 2 * m * n
		c := m*m + n*n
		a^2 + b^2 = c^2
	*/
	MAX := 1000000
	for m := 2; m < MAX; m++ {
		for n := 1; n < m; n++ {
			if gcd(m, n) == 1 && (m-n)%2 == 1 {
				a := m*m - n*n
				b := 2 * m * n
				//				c := m*m + n*n
				if 2*(a-1) == b {
					v := a - 1
					fmt.Printf("%d %d %d\n", v, m, n)
				}
				if 2*(b-1) == a {
					v := a - 1
					fmt.Printf("%d %d %d\n", v, m, n)
				}
			}
		}
	}
}

func gcd(a, b int) int {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}
