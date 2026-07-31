package main

import (
	"fmt"
)

func main() {
	t := make([]int, 20005)
	s := 290797
	M1 := 50515093
	M2 := 500
	t[0] = s % M2
	for i := 1; i <= 20000; i++ {
		s = (s * s) % M1
		t[i] = s % M2
	}
	for i := 0; i < 10; i++ {
		fmt.Printf("%d %d\n", i, t[i])
	}
	lines := make([]Line, 0)
	for i := 1; i <= 20000-3; i += 4 {
		L := line(t[i], t[i+1], t[i+2], t[i+3])
		lines = append(lines, L)
	}
	fmt.Printf("lines = %v\n", len(lines))
	m := make(map[string]bool)
	for i := 0; i < len(lines); i++ {
		for j := i + 1; j < len(lines); j++ {
			d := D(lines[i], lines[j])
			if d == 0 {
				continue
			}
			u := Nu(lines[i], lines[j])
			v := Nv(lines[i], lines[j])
			neg := false
			if d < 0 {
				d = -d
				u = -u
				v = -v
				neg = true
			}
			// 判断u,v是否在[0,1]之间
			if !(u > 0 && u < d) {
				continue
			}
			if !(v > 0 && v < d) {
				continue
			}
			if neg {
				d = -d
				u = -u
				v = -v
			}
			// 计算这个交点，并记录
			x, y := intersect(lines[i], d, u)
			str := fmt.Sprintf("%d %d %d %d", x.num, x.den, y.num, y.den)
			//fmt.Printf("----\n")
			//fmt.Printf("lines[%d] = %v\n", i, lines[i])
			//fmt.Printf("lines[%d] = %v\n", j, lines[j])
			//fmt.Printf("str = %s\n", str)
			//fmt.Printf("----\n")
			m[str] = true
		}
	}
	fmt.Printf("len(m) = %d\n", len(m))
}

// 直线参数方程
// P(u) = A+u(B-A) {u | u∈[0,1]}
type Line struct {
	x1, y1 int64
	x2, y2 int64
}

func D(l1, l2 Line) int64 {
	x1, y1, x2, y2 := l1.x1, l1.y1, l1.x2, l1.y2
	x3, y3, x4, y4 := l2.x1, l2.y1, l2.x2, l2.y2
	return (x2-x1)*(y4-y3) - (x4-x3)*(y2-y1)
}

func Nu(l1, l2 Line) int64 {
	x1, y1 := l1.x1, l1.y1
	x3, y3, x4, y4 := l2.x1, l2.y1, l2.x2, l2.y2
	return (x3-x1)*(y4-y3) - (x4-x3)*(y3-y1)
}

func Nv(l1, l2 Line) int64 {
	x1, y1, x2, y2 := l1.x1, l1.y1, l1.x2, l1.y2
	x3, y3 := l2.x1, l2.y1
	return (x3-x1)*(y2-y1) - (x2-x1)*(y3-y1)
}

func intersect(l Line, D, N int64) (Fraction, Fraction) {
	x1, y1, x2, y2 := l.x1, l.y1, l.x2, l.y2
	X := x1*D + (x2-x1)*N
	Y := y1*D + (y2-y1)*N
	return Fraction{X, D}.simp(), Fraction{Y, D}.simp()
}

type Fraction struct {
	num, den int64
}

func (f Fraction) simp() Fraction {
	g := gcd(f.num, f.den)
	return Fraction{f.num / g, f.den / g}
}

func gcd(a, b int64) int64 {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}

func line(x1, y1, x2, y2 int) Line {
	return Line{int64(x1), int64(y1), int64(x2), int64(y2)}
}
