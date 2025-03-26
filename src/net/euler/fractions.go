package main

import "fmt"

type Fraction struct {
	Numerator   string
	Denominator string
}

func FAdd(a, b Fraction) Fraction {
	//	fmt.Printf("FAdd(%v, %v)\n", a, b)
	// a/b + c/d
	n := Add(Mul(a.Numerator, b.Denominator), Mul(b.Numerator, a.Denominator))
	d := Mul(a.Denominator, b.Denominator)
	//	fmt.Printf("(n=%s, d=%s)\n", n, d)
	g := GCD(n, d)
	//fmt.Printf("GCD(%s, %s) = %s\n", n, d, g)
	f := Fraction{
		Numerator:   DIV(n, g),
		Denominator: DIV(d, g),
	}
	//fmt.Printf("%v +  %v = %v\n", a, b, f)
	return f
}

func FInv(a Fraction) Fraction {
	return Fraction{
		Numerator:   a.Denominator,
		Denominator: a.Numerator,
	}
}

func (f Fraction) String() string {
	return fmt.Sprintf("%s/%s", f.Numerator, f.Denominator)
}

// 求根号N的连分数表示形式
// r<=√N<=r+1, 先求的整数部分a0 = r
// √23 = 4 + √23 -4 确定a0 = 4
// 将√23-4取倒数得到 (√23+4)/7 = 1 + (√23-3)/7 确定 a1 = 1
// 将(√23-3)/7取倒数得到 (√23+3)/2 = 3 + (√23-3)/2 确定 a2 = 3
func Sqrt(n int) string {
	m = make(map[string]int)
	list = make([]int, 0)
	r := IntSqrt(n)
	// 完全平方数
	if r*r == n {
		return fmt.Sprintf("[%d]", r)
	}
	// 取整之后，剩余的部分 (x√N + y)/z = (1√n - r)/1, x=1, y=-r, z=1
	// 然后对剩余部分，取倒数，再取整，重复这个过程
	f(n, 1, -r, 1)

	result := fmt.Sprintf("[%d;(", r)
	for _, v := range list {
		result += fmt.Sprintf("%d,", v)
	}

	result = result[:len(result)-1]
	result += ")]"
	return result
}

var m = make(map[string]int)
var list = make([]int, 0)

// 求 (x√N + y)/z的连分数表达式
func f(n, x, y, z int) {
	//fmt.Printf("n=%d,x=%d,y=%d,z=%d\n", n, x, y, z)
	//	fmt.Printf("(%d√%d + %d) / %d\n", x, n, y, z)

	key := fmt.Sprintf("%d,%d,%d,%d", n, x, y, z)
	v, ok := m[key]
	if ok {
		return
	}
	// 取倒数,  分子分母同时乘以共轭 x√N - y
	// z/(x√N + y) = xz√N - zy / (x^2*N - y^2)

	a := x * z
	b := -z * y
	c := x*x*n - y*y

	g := gcd(a, c)
	g = gcd(g, b)
	a = a / g
	b = b / g
	c = c / g

	// 求整数部分
	v = (a*IntSqrt(n) + b) / c

	//	fmt.Printf("a=%d,b=%d,c=%d,v=%d\n", a, b, c, v)

	m[key] = v
	list = append(list, v)
	// 递归求余数
	// 剩余部分 (a√N + b)/c - v = (a√N + b-cv)/c
	f(n, a, b-c*v, c)
}

func IntSqrt(n int) int {
	j := 0
	for i := 1; i <= n; i++ {
		if i*i <= n {
			j = i
		} else {
			break
		}
	}
	return j
}
