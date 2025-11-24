package main

import "fmt"

func main() {
	for a := 0; a < 10; a++ {
		for b := a + 1; b < 10; b++ {
			for c := b + 1; c < 10; c++ {
				for d := c + 1; d < 10; d++ {
					m = make(map[int]int)
					v := check(a, b, c, d)
					fmt.Printf("%d%d%d%d = %d\n", a, b, c, d, v)
					if a == 1 && b == 2 && c == 5 && d == 8 {
						fmt.Printf("%v\n", m)
					}
				}
			}
		}
	}
}

var m = make(map[int]int)

func check(a, b, c, d int) int {
	f1 := NewFraction(a, 1)
	f2 := NewFraction(b, 1)
	f3 := NewFraction(c, 1)
	f4 := NewFraction(d, 1)
	list := []Fraction{f1, f2, f3, f4}
	dfs(list)
	i := 1
	for {
		_, ok := m[i]
		if ok {
			i++
		} else {
			break
		}
	}
	return i - 1
}

func dfs(list []Fraction) {
	// 随机选出两个数据，四则运算之后，继续递归
	// 递归到最后，就是一个数
	if len(list) == 1 {
		// 检查这个分数是否是整数，如果是的话，记录下来
		f := list[0]
		if f.num%f.den == 0 {
			k := f.num / f.den
			m[k] = 1
		}
		return
	}
	for i := 0; i < len(list); i++ {
		for j := i + 1; j < len(list); j++ {
			f1 := list[i]
			f2 := list[j]
			tmp := mkCopy(list, i, j)
			// +
			f := add(f1, f2)
			dfs(append(tmp, f))
			// -
			f = sub(f1, f2)
			dfs(append(tmp, f))
			f = sub(f2, f1)
			dfs(append(tmp, f))
			// *
			f = mul(f1, f2)
			dfs(append(tmp, f))
			// /
			if !f2.isZero() {
				f = div(f1, f2)
				dfs(append(tmp, f))
			}
			if !f1.isZero() {
				f = div(f2, f1)
				dfs(append(tmp, f))
			}
		}
	}
}

func mul(f1, f2 Fraction) Fraction {
	//	fmt.Printf("mul %v, %v\n", f1, f2)
	num := f1.num * f2.num
	den := f1.den * f2.den
	g := gcd(num, den)
	num /= g
	den /= g
	return NewFraction(num, den)
}

func div(f1, f2 Fraction) Fraction {
	return mul(f1, NewFraction(f2.den, f2.num))
}

func sub(f1, f2 Fraction) Fraction {
	num := f1.num*f2.den - f2.num*f1.den
	den := f1.den * f2.den
	g := gcd(num, den)
	num /= g
	den /= g
	return NewFraction(num, den)
}

func mkCopy(list []Fraction, i, j int) []Fraction {
	tmp := make([]Fraction, 0)
	for k := 0; k < len(list); k++ {
		if k != i && k != j {
			tmp = append(tmp, list[k])
		}
	}
	return tmp
}

type Fraction struct {
	num, den int
}

func (f Fraction) String() string {
	return fmt.Sprintf("%d/%d", f.num, f.den)
}

func (f Fraction) isZero() bool {
	return f.num == 0
}

func NewFraction(num, den int) Fraction {
	return Fraction{num, den}
}

func add(f1, f2 Fraction) Fraction {
	num := f1.num*f2.den + f2.num*f1.den
	den := f1.den * f2.den
	g := gcd(num, den)
	num /= g
	den /= g
	return NewFraction(num, den)
}

func gcd(a, b int) int {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}
