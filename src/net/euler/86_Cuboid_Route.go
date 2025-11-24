package main

import "fmt"

// a = m^2 - n^2
// b = 2mn
// c = m^2 + n^2
// a^2 + b^2 = c^2
// (m^2 - n^2)^2 + (2mn)^2 = (m^2 + n^2)^2
// m>n, gcd(m,n)=1, (m-n)%2==1

// l1 l2 l3 3 5 6
// 6^2+(5+3)^2=100
// 3^2+(5+6)^2=130
// 5^2+(3+6)^2=106
var M = 1818

func main() {
	sum := 0
	for m := 1; m < M; m++ {
		for n := m - 1; n > 0; n-- {
			if gcd(m, n) != 1 || (m-n)%2 == 0 {
				continue
			}
			a := m*m - n*n
			b := 2 * m * n
			if a > M && b > M {
				continue
			}
			c := m*m + n*n
			//fmt.Printf("for -------- find m=%d, n=%d %d %d %d\n", m, n, a, b, c)
			sum += count(a, b, c)
			a1 := a
			b1 := b
			c1 := c
			for a1 <= M || b1 <= M {
				a1 += a
				b1 += b
				c1 += c
				//	fmt.Printf("for -------- find m=%d, n=%d %d %d %d\n", m, n, a1, b1, c1)
				sum += count(a1, b1, c1)
			}
		}
	}
	fmt.Printf("total %d\n", sum)
}

func gcd(m, n int) int {
	for m%n != 0 {
		m, n = n, m%n
	}
	return n
}

func count(a, b, c int) int {
	cnt := 0
	// a^2 + b^2 = c^2
	// c是长方体对角线，l1,l2,l3 为3个边长，一共有多少种方案
	// 当l1=a时，l2+l3=b
	if a <= M {
		l1 := 1
		l2 := b - l1
		for l1 <= l2 {
			if l1 <= M && l2 <= M && isShorest(a, l1, l2) {
				cnt++
				//	fmt.Printf("find %d %d %d\n", a, l1, l2)
			}
			l1++
			l2--
		}
	}

	// 当l1=b时，l2+l3=a
	if b <= M {
		l1 := 1
		l2 := a - l1
		for l1 <= l2 {
			if l1 <= M && l2 <= M && isShorest(b, l1, l2) {
				cnt++
				//	fmt.Printf("find %d %d %d\n", l1, l2, b)
			}
			l1++
			l2--
		}
	}
	return cnt
}

func isShorest(a, b, c int) bool {
	A := a*a + (b+c)*(b+c)
	B := b*b + (a+c)*(a+c)
	C := c*c + (a+b)*(a+b)
	return A <= B && A <= C
}
