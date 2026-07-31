package main

import (
	"fmt"
)

/*

求 x^3 - 3*x + 4 的连乘 （x从0到p-1) 对 p 的模

p是质数,在模p的世界，有限域Fp中，所有的数字只有p个: 0,1,2,...,p-1

根据费马小定理：
x^(p-1) = 1 (mod p)
x^p = x (mod p)
把x移到等式左边,得到
x^p - x = 0 (mod p)
所以在模p的世界，有限域Fp中，多项式f(x) = x^p - x 的根刚好是全部的数字: 0,1,2,...,p-1

又根据因式定理，如果a是f(x)=0的一个根，那么（x-a)必定是f(x)的一个因式
所以
x-0
x-1
x-2
...
x-(p-1)
都是多项式f(x) = x^p - x 的因式
然后f(x)的最高次系数是1，所以前面每个因式的系数都是1,即

x^p - x = (x-0) * (x-1) * ... * (x-(p-1)) (mod p)       (恒等式1)

回到题目内容，假设 f(x) = x^3 - 3*x + 4 = 0 的三个根是A,B,C
所以 f(x) = (x-A) * (x-B) * (x-C)

我们需要求 P = (x-A) * (x-B) * (x-C) (mod p), {x|0<=x<=p-1}
逐项展开:
x=0, (0-A)*(0-B)*(0-C)
x=1, (1-A)*(1-B)*(1-C)
x=2, (2-A)*(2-B)*(2-C)
...
x=p-1, (p-1-A)*(p-1-B)*(p-1-C)

第一列和A有关的：(0-A)*(1-A)*(2-A)...*(p-1-A)
一共0到p-1的数字，共p个,p是质数，显然是奇数，每个括号提取-1,得到
(-1)^p * (A-0)*(A-1)*(A-2)...*(A-(p-1))
同理，第二列和B有关的，得到
(-1)^p * (B-0)*(B-1)*(B-2)...*(B-(p-1))
第三列和C有关的，得到
(-1)^p * (C-0)*(C-1)*(C-2)...*(C-(p-1))

现在看和A有关的内容, 根据恒等式1，即x=A
(-1)^p * (A-0)*(A-1)*(A-2)...*(A-(p-1)) = -(A^p - A) (mod p)
同理可得
(-1)^p * (B-0)*(B-1)*(B-2)...*(B-(p-1)) = -(B^p - B) (mod p)
(-1)^p * (C-0)*(C-1)*(C-2)...*(C-(p-1)) = -(C^p - C) (mod p)

原来的连乘 P = -(A^p - A) * (B^p - B) * (C^p - C) (mod p)

因为A,B,C是方程 x^3 - 3*x + 4=0 的三个根，所以
A^3 - 3*A + 4 = 0
A^3 = 3*A - 4

A^P可以通过上式降次得到 A^P = w*A^2 + v*A + u



*/

func main() {
	fmt.Printf("R(%d)=%d\n", 11, R(11))
	fmt.Printf("R(%d)=%d\n", 19, R(19))
	fmt.Printf("R(%d)=%d\n", 23, R(23))
	fmt.Printf("R(%d)=%d\n", 29, R(29))
	// return
	MAX := 1100000000
	composite := make([]bool, MAX)
	composite[2] = false
	composite[3] = false
	for i := 2; i < MAX; i++ {
		if composite[i] {
			continue
		}
		for j := i + i; j < MAX; j += i {
			composite[j] = true
		}
	}

	cnt := 0
	for i := 1000000001; i < 1100000000; i += 2 {
		if !composite[i] {
			cnt++
		}
	}
	fmt.Printf("prime cnt = %d\n", cnt)

	sum := int64(0)

	for i := 1000000001; i < 1100000000; i += 2 {
		if composite[i] {
			continue
		}
		if i%4 == 3 {
			// R(i)==0
			continue
		}
		sum += int64(R(i))
	}
	fmt.Printf("total = %d\n", sum)
}

func R(p int) int {
	//  重置环境
	M = p
	cache = map[int]Polynomial{
		0: Polynomial{{1, 0}},
		1: Polynomial{{1, 1}},
		2: Polynomial{{1, 2}},
		3: Polynomial{{3, 1}, {-4, 0}},
	}
	poly := Polynomial{{1, p}}
	poly = downP(poly)
	// w*A^2 + v*A + u
	w, v, u := 0, 0, 0
	for _, t := range poly {
		if t.Exponent == 2 {
			w = t.Coefficient
		}
		if t.Exponent == 1 {
			v = t.Coefficient
		}
		if t.Exponent == 0 {
			u = t.Coefficient
		}
	}
	//	fmt.Printf("p =%d w = %d, v = %d, u = %d\n", p, w, v, u)
	matrix := [3][3]int{
		{u, -4 * w, -4 * (v - 1)},
		{v - 1, u + 3*w, 3*(v-1) - 4*w},
		{w, v - 1, u + 3*w},
	}
	d := det(matrix, p)
	return (-d + p) % p
}

// 计算带有大素数 p 取模的 3x3 矩阵行列式，防止 int64 溢出
func det(m [3][3]int, p int) int {
	for i := 0; i < 3; i++ {
		for j := 0; j < 3; j++ {
			m[i][j] = mod(m[i][j])
		}
	}
	// 1. 先计算三个 2x2 子矩阵的行列式，并立即取模限制在 p 的范围内
	sub0 := (m[1][1]*m[2][2] - m[1][2]*m[2][1]) % p
	sub1 := (m[1][0]*m[2][2] - m[1][2]*m[2][0]) % p
	sub2 := (m[1][0]*m[2][1] - m[1][1]*m[2][0]) % p

	// 2. 乘上第一行的系数，再次取模
	term0 := (m[0][0] * sub0) % p
	term1 := (m[0][1] * sub1) % p
	term2 := (m[0][2] * sub2) % p

	// 3. 按照公式加减：a - b + c
	det := (term0 - term1 + term2) % p

	// 4. 兜底处理：Go 语言中负数取模结果可能为负，需要将其转为正余数
	return (det + p) % p
}

var M int

// 多项式的其中一项 系数和幂次
type Term struct {
	Coefficient int
	Exponent    int
}

func (t Term) String() string {
	if t.Exponent == 0 {
		return fmt.Sprintf("%d", t.Coefficient)
	}
	if t.Exponent == 1 {
		return fmt.Sprintf("%d*x", t.Coefficient)
	}
	return fmt.Sprintf("%d*x^%d", t.Coefficient, t.Exponent)
}

type Polynomial []Term

func (p Polynomial) String() string {
	str := ""
	for _, t := range p {
		str += fmt.Sprintf("%v + ", t)
	}
	if len(str) > 0 {
		// 去掉最后一个加号
		str = str[:len(str)-3]
	}
	return str
}

var cache = map[int]Polynomial{
	0: Polynomial{{1, 0}},
	1: Polynomial{{1, 1}},
	2: Polynomial{{1, 2}},
	3: Polynomial{{3, 1}, {-4, 0}},
}

func downT(t Term) Polynomial {
	//	fmt.Printf("down t (%v)\n", t)
	c, e := t.Coefficient, t.Exponent
	p, ok := cache[e]
	if ok {
		return mul(p, Polynomial{{c, 0}})
	}

	if e <= 20 {
		p := mul(downT(Term{1, e - 3}), cache[3])
		cache[e] = p
		return mul(p, Polynomial{{c, 0}})
	}

	if e%2 == 0 {
		A := downT(Term{1, e / 2})
		A = mul(A, A)
		A = downP(A)
		cache[e] = A
		return mul(A, Polynomial{{c, 0}})
	}

	A := downT(Term{1, e - 1})
	A = mul(A, Polynomial{{1, 1}})
	A = downP(A)
	cache[e] = A
	return mul(A, Polynomial{{c, 0}})
}

func downP(p Polynomial) Polynomial {
	//	fmt.Printf("down p (%v)\n", p)
	// check if finish
	finish := true
	for _, t := range p {
		if t.Exponent >= 3 {
			finish = false
			break
		}
	}
	if finish {
		return p
	}

	result := make(Polynomial, 0)
	for _, t := range p {
		if t.Coefficient == 0 {
			continue
		}
		if t.Exponent >= 3 {
			sub := downT(t)
			result = add(result, sub)
		} else {
			result = add(result, Polynomial{t})
		}
	}

	return downP(result)
}

func add(u, v Polynomial) Polynomial {
	// 合并同类项
	m := make(map[int]int)
	for _, t := range u {
		m[t.Exponent] += t.Coefficient
		m[t.Exponent] = mod(m[t.Exponent])
	}
	for _, t := range v {
		m[t.Exponent] += t.Coefficient
		m[t.Exponent] = mod(m[t.Exponent])
	}
	p := make(Polynomial, 0)
	for e, c := range m {
		if c != 0 {
			p = append(p, Term{c, e})
		}
	}
	return p
}

func mod(n int) int {
	return (n%M + M) % M
}

func mul(u, v Polynomial) Polynomial {
	p := make(Polynomial, 0)
	for _, t1 := range u {
		for _, t2 := range v {
			p = append(p, Term{
				Coefficient: ((t1.Coefficient % M) * (t2.Coefficient % M)) % M,
				Exponent:    t1.Exponent + t2.Exponent,
			})
		}
	}
	// 合并同类项
	m := map[int]int{}
	for _, t := range p {
		m[t.Exponent] += t.Coefficient
		m[t.Exponent] = mod(m[t.Exponent])
	}
	p = make(Polynomial, 0)
	for k, v := range m {
		if v != 0 {
			p = append(p, Term{v, k})
		}
	}
	return p
}
