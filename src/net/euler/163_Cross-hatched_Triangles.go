package main

import (
	"fmt"
	"math"
)

/*
https://projecteuler.net/problem=163
边长为1的正三角的高h = √3 / 2
A(0,0) B(1,0) C(0.5,h)

以∆ABC三个顶点为出发点，它的整数倍，可以构造出其他的三角形
然后每个三角形中可以构成6条直线，三条边+三条高，

求出所有的直线集合，
最后枚举直线，任意选3条，求交点，看是否可以构成三角形，并且在整体的边界内

直线方程 Ax+By+C=0
直线经过点P0(x0,y0), Ax0+By0+C=0
过点P1(x1,y1),       Ax1+By1+C=0

A = y0-y1
B = x1-x0
C = x0*y1-x1*y0



*/

func main() {
	h := 0.5 * math.Sqrt(3)
	A := Point{0, 0}
	B := Point{1, 0}
	C := Point{0.5, h}
	Points := make(map[string]bool)
	Lines := make(map[string]Line)
	//fmt.Printf("A=%v, B=%v, C=%v\n", A, B, C)
	size := 36
	// 以ABC为基础，通过平移可以找到左右顶点在上方的基础正三角形
	for i := 0; i < size; i++ {
		fmt.Printf("for i=%d\n", i)
		k := size - i
		fi := float64(i)
		As := Point{A.x + fi*0.5, A.y + fi*h}
		Bs := Point{B.x + fi*0.5, B.y + fi*h}
		Cs := Point{C.x + fi*0.5, C.y + fi*h}
		for j := 0; j < k; j++ {
			fj := float64(j)
			a := Point{As.x + fj*1, As.y}
			b := Point{Bs.x + fj*1, Bs.y}
			c := Point{Cs.x + fj*1, Cs.y}
			addTrianglePoints(Points, a, b, c)
			addTriangleLines(Lines, a, b, c)
			fmt.Printf("a=%v, b=%v, c=%v\n", a, b, c)
		}
	}
	// 构造一个基础倒的正三角，以此为基础，平移得到所有的基础倒三角形
	// B, C保持不变，D = (1.5, h)
	D := Point{1.5, h}
	//	fmt.Printf("B=%v, C=%v, D=%v\n", B, C, D)
	for i := 0; i < size; i++ {
		fmt.Printf("for i=%d\n", i)
		k := size - 1 - i
		fi := float64(i)
		Bs := Point{B.x + fi*0.5, B.y + fi*h}
		Cs := Point{C.x + fi*0.5, C.y + fi*h}
		Ds := Point{D.x + fi*0.5, D.y + fi*h}
		for j := 0; j < k; j++ {
			fj := float64(j)
			b := Point{Bs.x + fj*1, Bs.y}
			c := Point{Cs.x + fj*1, Cs.y}
			d := Point{Ds.x + fj*1, Ds.y}
			addTrianglePoints(Points, b, c, d)
			addTriangleLines(Lines, b, c, d)
			fmt.Printf("b=%v, c=%v, d=%v\n", b, c, d)
		}
	}
	fmt.Printf("points=%d, lines=%d\n", len(Points), len(Lines))
	lines := make([]Line, 0, len(Lines))
	for _, l := range Lines {
		lines = append(lines, l)
	}
	cnt := 0
	for i := 0; i < len(lines); i++ {
		//	fmt.Printf("line i=%d %v\n", i, lines[i])
		for j := i + 1; j < len(lines); j++ {
			//		fmt.Printf("line j=%d %v\n", j, lines[j])
			if isParallel(lines[i], lines[j]) {
				//			fmt.Printf("parallel: lines[%d]=%v, lines[%d]=%v\n", i, lines[i], j, lines[j])
				continue
			}
			a := intersect(lines[i], lines[j])
			//		fmt.Printf("line i and j intersect a=%v\n", a)
			if !Points[a.String()] {
				//			fmt.Printf("%v not in points\n", a)
				continue
			}
			for k := j + 1; k < len(lines); k++ {
				if isParallel(lines[i], lines[k]) || isParallel(lines[j], lines[k]) {
					continue
				}
				//			fmt.Printf("line k=%d %v\n", k, lines[k])
				// 求3个直线的交点
				b := intersect(lines[i], lines[k])
				c := intersect(lines[j], lines[k])
				if Points[b.String()] && Points[c.String()] {
					// 判断三个点各不相同
					if isSame(a, b) || isSame(a, c) || isSame(b, c) {
						continue
					}
					//	fmt.Printf("i=%d, j=%d, k=%d, a=%v, b=%v, c=%v\n", i, j, k, a, b, c)
					cnt++
				}
			}
		}
	}
	fmt.Printf("total triangle cnt=%d\n", cnt)
}

type Point struct {
	x, y float64
}

func (p Point) String() string {
	x := fmt.Sprintf("%f", p.x)
	if isZero(p.x) {
		x = "0"
	}
	y := fmt.Sprintf("%f", p.y)
	if isZero(p.y) {
		y = "0"
	}
	return fmt.Sprintf("(%s,%s)", x, y)
}

func isSame(p0, p1 Point) bool {
	return isZero(p0.x-p1.x) && isZero(p0.y-p1.y)
}

type Line struct {
	//Ax+By+C=0
	A, B, C float64
}

func (l Line) String() string {
	a := fmt.Sprintf("%f", l.A)
	if isZero(l.A) {
		a = "0"
	}
	b := fmt.Sprintf("%f", l.B)
	if isZero(l.B) {
		b = "0"
	}
	c := fmt.Sprintf("%f", l.C)
	if isZero(l.C) {
		c = "0"
	}
	return fmt.Sprintf("%s*x + %s*y + %s=0", a, b, c)
}

func isZero(f float64) bool {
	return math.Abs(f) < 1e-7
}

/*
Ax + By + C = 0
A = y0-y1
B = x1-x0
C = x0*y1-x1*y0
*/
func line(p0, p1 Point) Line {
	x0, y0 := p0.x, p0.y
	x1, y1 := p1.x, p1.y
	A := y0 - y1
	B := x1 - x0
	C := x0*y1 - x1*y0
	if A != 0 {
		B /= A
		C /= A
		A = 1.0
	} else if B != 0 {
		A /= B
		C /= B
		B = 1.0
	}
	return Line{A, B, C}
}

func addTriangleLines(m map[string]Line, A, B, C Point) {
	// line AB
	ab := line(A, B)
	m[ab.String()] = ab
	// line AC
	ac := line(A, C)
	m[ac.String()] = ac
	// line BC
	bc := line(B, C)
	m[bc.String()] = bc
	// mid point MA,MB,MC
	// line A-MA
	a1 := mid(B, C)
	ma := line(A, a1)
	m[ma.String()] = ma
	// line B-MB
	b1 := mid(A, C)
	mb := line(B, b1)
	m[mb.String()] = mb
	// line C-MC
	c1 := mid(A, B)
	mc := line(C, c1)
	m[mc.String()] = mc
}

func mid(A, B Point) Point {
	x := (A.x + B.x) / 2
	y := (A.y + B.y) / 2
	return Point{x, y}
}

func addTrianglePoints(m map[string]bool, A, B, C Point) {
	m[A.String()] = true
	m[B.String()] = true
	m[C.String()] = true
	ab := mid(A, B)
	m[ab.String()] = true
	bc := mid(B, C)
	m[bc.String()] = true
	ca := mid(C, A)
	m[ca.String()] = true
	// 三条中线的焦点
	l1 := line(A, bc)
	l2 := line(B, ca)
	mid := intersect(l1, l2)
	m[mid.String()] = true
}

func intersect(l1, l2 Line) Point {
	A1, B1, C1 := l1.A, l1.B, l1.C
	A2, B2, C2 := l2.A, l2.B, l2.C
	wx := B1*C2 - B2*C1
	wy := A2*C1 - A1*C2
	w := A1*B2 - A2*B1
	return Point{wx / w, wy / w}
}

func isParallel(l1, l2 Line) bool {
	d := l1.A*l2.B - l1.B*l2.A
	return isZero(d)
}
