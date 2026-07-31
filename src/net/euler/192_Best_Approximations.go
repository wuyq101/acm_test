package main

import (
	"fmt"
	"math/big"
)

func main() {
	sum := int64(0)
	for n := 2; n <= N; n++ {
		s, ok := isSquare(n)
		part[n] = s
		if ok {
			continue
		}
		a0, f := sqrt(n)
		//fmt.Printf("√%d = %d %v\n", n, a0, f)
		p, q := frac(n, a0, f)
		fmt.Printf("√%d = %d / %d\n", n, p, q)
		sum += q
	}
	fmt.Printf("sum=%d\n", sum)
}

var N = 100000

var M = int64(1e12)

// var M = int64(30)
var part = make([]int, N+1)

func frac(n, a0 int, f []int) (int64, int64) {
	p0 := int64(a0)
	q0 := int64(1)
	p1 := int64(f[0]*a0 + 1)
	q1 := int64(f[0])
	p, q := int64(0), int64(0)
	i := 1
	size := len(f)
	for {
		ak := int64(f[i%size])
		p = ak*p1 + p0
		q = ak*q1 + q0
		if q > M {
			// 半渐进分数
			// p = i*p1 + p0
			// q = i*q1 + q0
			// i*q1 + q0 <= M
			i := (M - q0) / q1
			p = i*p1 + p0
			q = i*q1 + q0
			return midTest(p, q, p1, q1, int64(n))
		}
		p0, p1 = p1, p
		q0, q1 = q1, q
		i++
	}
	return p, q
}

// 比较 A=a/b B=c/d和√n
// m = (A+B)/2
func midTest(a, b, c, d, n int64) (int64, int64) {
	// M = (A+B)/2 = ad+bc / 2bd
	ad := big.NewInt(0)
	ad.Mul(big.NewInt(a), big.NewInt(d))
	bc := big.NewInt(0)
	bc.Mul(big.NewInt(b), big.NewInt(c))
	num := big.NewInt(0)
	num.Add(ad, bc)

	bd := big.NewInt(0)
	bd.Mul(big.NewInt(b), big.NewInt(d))
	den := big.NewInt(2)
	den.Mul(den, bd)

	// num/den √n
	x := big.NewInt(0)
	x.Mul(num, num)

	y := big.NewInt(0)
	y.Mul(den, den)

	z := big.NewInt(n)
	z.Mul(z, y)

	if ad.Cmp(bc) < 0 {
		// a/b < c/d
		if x.Cmp(z) < 0 {
			return c, d
		}
		return a, b
	}

	// a/b > c/d
	if x.Cmp(z) < 0 {
		return a, b
	}
	return c, d
}

func isSquare(n int) (int, bool) {
	l, h := 1, n
	s := 0
	for l <= h {
		m := (l + h) / 2
		v := m * m
		if v == n {
			return m, true
		}
		if v < n {
			l = m + 1
			s = m
		} else {
			h = m - 1
		}
	}
	return s, false
}

// √n = [a0;a1,a2,...,ak] 展开成连分数的形式
func sqrt(n int) (int, []int) {
	a, b, c, d := 0, 1, 1, 0
	m := make(map[[4]int]bool)
	m[[4]int{a, b, c, d}] = true
	// 整数部分
	a0 := integer(a, b, c, n)
	frac := make([]int, 0)
	// 分数部分
	a = a - a0*c
	b = b - a0*d
	for {
		key := [4]int{a, b, c, d}
		if m[key] {
			break
		}
		m[key] = true
		// 倒数，取整数部分
		ta, tb := c, d
		c, d = a, b
		a, b = ta, tb
		a, b, c, d = rd(a, b, c, d, n)
		ai := integer(a, b, c, n)
		frac = append(frac, ai)
		a = a - ai*c
		b = b - ai*d
	}
	return a0, frac
}

/*
去掉分母中的√n

a + b*√n   (a+b√n)(c-d√n)   (ac-bd*n) + (bc-ad)*√n
-------- = -------------- = ----------------------
c + d*√n    c*c - d*d*n        c*c - d*d*n

*/

func rd(a, b, c, d, n int) (int, int, int, int) {
	if d == 0 {
		return a, b, c, 0
	}
	a1 := a*c - b*d*n
	b1 := b*c - a*d
	c1 := c*c - d*d*n
	// 分母保持正数
	if c1 < 0 {
		a1 = -a1
		b1 = -b1
		c1 = -c1
	}
	g := gcd(a1, c1)
	g = gcd(b1, g)
	if g > 1 {
		a1 /= g
		b1 /= g
		c1 /= g
	}
	return a1, b1, c1, 0
}

func gcd(a, b int) int {
	if a < 0 {
		a = -a
	}
	if b < 0 {
		b = -b
	}
	return int(pgcd(int64(a), int64(b)))
}

// positive
func pgcd(a, b int64) int64 {
	if b == 0 {
		return a
	}
	return pgcd(b, a%b)
}

/*
求整数部分

a + b*√n
-------- ≈ (a+b*i)/c
c + 0*√n
*/
func integer(a, b, c, n int) int {
	// 求整数部分
	i := part[n]
	if b < 0 {
		i++
	}
	v := float64(a+b*i) / float64(c)
	return int(v)
}
