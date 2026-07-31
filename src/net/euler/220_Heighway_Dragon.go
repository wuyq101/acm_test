package main

import "fmt"

/*
http://projecteuler.net/problem=220

rewrite rules:

a -> aRbFR
b -> LFaLb

rule a -> aRbFR
一个字母变5个
rule b -> LFaLb
一个字母变5个


D0 = Fa				2	1
D1 = FaRbFR			6	2
D2 = FaRbFRRLFaLbFR		14	4
D3 = FaRbFRRLFaLbFRRLFaRbFRLLFaLbFR	30	8
D4 = ...................	62	16

Di 其中a,b的个数是 2^i
Di的长度 =  5*2^(i-1) + Di-1 - 2^(i-1)



Dn = F(a(n-1))
a(n-1) = a(n-2) R b(n-2) FR


*/

func main() {
	N := 50
	P(N)
	pos := int(1e12)
	D(N, pos)
	fmt.Printf("cur=%+v\n", cur)
	d := cur.disp
	fmt.Printf("%d,%d\n", d.x, d.y)
}

// 初始出发点 // 向上
var cur = E{complex{0, 0}, complex{0, 1}, 0}
var EA []E
var EB []E

// F{a(n)}
func D(n, pos int) {
	if pos == 0 {
		return
	}
	// D0 = F{a}, 处理最开头的F
	pos--
	cur = combine(cur, F)
	A(n, pos)
}

func A(n, pos int) {
	if pos == 0 {
		return
	}
	// a -> aRbFR
	e := EA[n-1]
	// n-1次a操作，一共需要的步数是e.steps
	if e.steps <= pos {
		pos -= e.steps
		cur = combine(cur, e)
	} else {
		A(n-1, pos)
		return
	}
	// R
	cur = combine(cur, R)
	// b
	e = EB[n-1]
	if e.steps <= pos {
		pos -= e.steps
		cur = combine(cur, e)
	} else {
		B(n-1, pos)
		return
	}
	// F
	if pos > 0 {
		pos--
		cur = combine(cur, F)
	}
	// R
	cur = combine(cur, R)
}

func B(n, pos int) {
	if pos == 0 {
		return
	}
	// b -> LFaLb
	// L
	cur = combine(cur, L)
	// F
	if pos > 0 {
		pos--
		cur = combine(cur, F)
	}
	// a
	e := EA[n-1]
	if e.steps <= pos {
		pos -= e.steps
		cur = combine(cur, e)
	} else {
		A(n-1, pos)
		return
	}
	// L
	cur = combine(cur, L)
	// b
	e = EB[n-1]
	if e.steps <= pos {
		pos -= e.steps
		cur = combine(cur, e)
	} else {
		B(n-1, pos)
	}
}

var right = complex{0, -1}
var left = complex{0, 1}
var same = complex{1, 0}

// turn right
var R = E{disp: complex{0, 0}, dir: right, steps: 0}

// turn left
var L = E{disp: complex{0, 0}, dir: left, steps: 0}

// forward, 沿着原来方向前进1
var F = E{disp: complex{1, 0}, dir: same, steps: 1}

func P(n int) {
	// 提前计算好n步a和b的累加效果
	EA = make([]E, n+1)
	EB = make([]E, n+1)
	EA[0] = E{disp: complex{0, 0}, dir: same, steps: 0}
	EB[0] = E{disp: complex{0, 0}, dir: same, steps: 0}
	for i := 1; i <= n; i++ {
		// a -> aRbFR
		ea := EA[i-1]
		ea = combine(ea, R)
		ea = combine(ea, EB[i-1])
		ea = combine(ea, F)
		ea = combine(ea, R)
		EA[i] = ea

		// b -> LFaLb
		eb := L
		eb = combine(eb, F)
		eb = combine(eb, EA[i-1])
		eb = combine(eb, L)
		eb = combine(eb, EB[i-1])
		EB[i] = eb
	}
}

// 将两段的效果合并在一起
// 每段都是假设从(0,0)出发，初识方向(1,0)(向右)
func combine(e1, e2 E) E {
	// 位移的合并
	// e2是从e1的终点和方向出发
	// e2.disp是按照向右的初始方向计算的，需要转到e1.dir方向上，所以需要 mul(e2.disp, e1.dir)
	// 同时e2.disp是从(0,0)出发的，这里需要叠加到e1.disp上,所以 加上e1.disp
	disp := add(e1.disp, mul(e2.disp, e1.dir))
	// 方向的合并
	// *i 左转90度
	// *-i 右转90度
	// e1.dir 走完e1之后，相对起步方向，转了多少度
	// e2.dir 走完e2之后，相对起步方向，转了多少度
	dir := mul(e1.dir, e2.dir)
	steps := e1.steps + e2.steps
	return E{disp, dir, steps}
}

type E struct {
	disp  complex // 相对位移
	dir   complex // 经过整体区域之后的方向
	steps int     // 这个区域，一共包含多少F
}

// x+y*i
type complex struct {
	x, y int
}

func mul(a, b complex) complex {
	return complex{a.x*b.x - a.y*b.y, a.x*b.y + a.y*b.x}
}

func add(a, b complex) complex {
	return complex{a.x + b.x, a.y + b.y}
}
