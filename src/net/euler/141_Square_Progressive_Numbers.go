package main

import "fmt"

/*
http://projecteuler.net/problem=141

n = d*q+r, 0<r<d

d^2 = r*q

n = d * d^2/r + r = d^3/r + r

r是d^3(立方数)的因子，且r < d

d^3/r + r < 10^12, r=1的时候，预估 d^3 < 10^12, d<10^4

q = d^2 / r
n = d^3 / r + r

g = gcd(d,r)
d = gx
r = gy
gcd(x,y)=1

d^2 = r * q --> q = d^2/r = g^2*x^2/(g*y) = g*x^2/y
由于q是整数，所以 y|g, 这 g = y*z
则 d = gx = xyz
r = gy = y^2*z
q = x^2*z

n = d*q + r = x^3*y*z^2 + y^2*z

y<x, gcd(x,y)=1
枚举y = 1,2,3,,,,
枚举x = y+1,y+2,,,,, gcd(x,y)=1
枚举z = 1,2,3,,,,, 用n<LIMIT约束






*/

var m map[int64]bool

func main() {
	M := int64(1e12)
	m = make(map[int64]bool)
	for i := int64(1); i*i < M; i++ {
		m[i*i] = true
	}

	sum := int64(0)
	for y := int64(1); y < 100; y++ {
		for x := y + 1; x < M; x++ {
			if gcd(x, y) != 1 {
				continue
			}
			if x*x*x*y+y*y > M {
				break
			}
			for z := int64(1); z < M; z++ {
				n := x*x*x*y*z*z + y*y*z
				if n < M && isSquare(n) {
					fmt.Printf("find n=%d, x=%d, y=%d, z=%d\n", n, x, y, z)
					sum += n
				}
				if n > M {
					break
				}
			}
		}
	}
	fmt.Printf("sum=%d\n", sum)
}

// 12, 6 --> 0,6
func gcd(x, y int64) int64 {
	for {
		if y == 0 {
			return x
		}
		x, y = y, x%y
	}
}

func isSquare(n int64) bool {
	return m[n]
}
