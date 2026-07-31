package main

import (
	"fmt"
	"math/big"
)

/*
https://projecteuler.net/problem=180
经过计算值得得到

f(x,y,z) = (x+y+z)*(x^n+y^n-z^n) = 0
由于x,y,z>0, 所以 x^n+y^n-z^n = 0 ---> x^n+y^n = z^n
x,y,z都是有理数，根据费马大定理， x^n+y^n = z^n, 对任何n>=3都没有任何正有理数解
当n<=3的时候，相当于 x,y,z求倒数之后在乘方相加，也是不可能有解的。
因此 n = {-2, -1, 1, 2}, n=0显然不对，排除掉。

然后x,y,z属于 order 35: {a/b | 0<a<b<=35}

我们可以枚举出集合的每个元素，
在枚举x,y,n, 并计算z，检查z是否在集合中

n = 1:
x+y = z, 分数加法

n = 2:
x^2 + y^2 = z^2
平方之后在计算，不需要引入根号

负数，取倒数之后再计算





*/

func main() {
	fractions := make(map[Fraction]bool)
	for b := 1; b <= 35; b++ {
		for a := 1; a < b; a++ {
			f := Fraction{int64(a), int64(b)}.simp()
			fractions[f] = true
		}
	}
	f2 := make(map[Fraction]bool)
	for f := range fractions {
		f2[f.square()] = true
	}

	sumMap := make(map[Fraction]bool)
	checkAdd := func(x, y, z Fraction) {
		a := s(x, y, z)
		if !sumMap[a] {
			sumMap[a] = true
		}
	}
	for x := range fractions {
		for y := range fractions {
			// 只考虑 x<=y, 根据对称性，它们最后算出来的和都一样，只要计算一次就够
			if x.cmp(y) > 0 {
				continue
			}
			// n=1, x+y=z
			z := x.add(y)
			if fractions[z] {
				//	fmt.Printf("n=1 x=%s, y=%s, z=%s\n", x, y, z)
				checkAdd(x, y, z)
			}
			// n=2 x^2+y^2=z^2
			z2 := x.square().add(y.square())
			if f2[z2] {
				z := z2.sqrt()
				//	fmt.Printf("n=2 x=%s, y=%s, z=%s\n", x, y, z)
				checkAdd(x, y, z)
			}

			// n=-1 1/x + 1/y = 1/z
			z = x.inv().add(y.inv()).inv()
			if fractions[z] {
				//	fmt.Printf("n=-1 x=%s, y=%s, z=%s\n", x, y, z)
				checkAdd(x, y, z)
			}

			// n=-2 1/x^2 + 1/y^2 = 1/z^2
			z2 = x.inv().square().add(y.inv().square()).inv()
			if f2[z2] {
				z := z2.sqrt()
				//	fmt.Printf("n=-2 x=%s, y=%s, z=%s\n", x, y, z)
				checkAdd(x, y, z)
			}
		}
	}
	fmt.Printf("size of sum map=%d\n", len(sumMap))
	// n/d
	n := big.NewInt(0)
	d := big.NewInt(1)
	for f := range sumMap {
		// f = a/b
		// n/d + a/b = an +
		a, b := big.NewInt(f.Num), big.NewInt(f.Den)
		bn := big.NewInt(0)
		bn.Mul(b, n)

		ad := big.NewInt(0)
		ad.Mul(a, d)

		num := big.NewInt(0)
		num.Add(bn, ad)

		den := big.NewInt(0)
		den.Mul(d, b)

		g := big.NewInt(0)
		g.GCD(nil, nil, num, den)

		n.Div(num, g)
		d.Div(den, g)
	}
	fmt.Printf("n=%s, d=%s\n", n, d)
	fmt.Printf("n+d=%s\n", n.Add(n, d))
	/*
		for f := range sumMap {
			fmt.Printf("f=%s\n", f)
		}
	*/

}

func s(x, y, z Fraction) Fraction {
	return x.add(y).add(z)
}

type Fraction struct {
	Num int64
	Den int64
}

func (f Fraction) String() string {
	return fmt.Sprintf("%d/%d", f.Num, f.Den)
}

func (f Fraction) simp() Fraction {
	g := gcd(f.Num, f.Den)
	return Fraction{f.Num / g, f.Den / g}
}

func (f Fraction) inv() Fraction {
	return Fraction{f.Den, f.Num}
}

func (f Fraction) add(k Fraction) Fraction {
	num := f.Num*k.Den + f.Den*k.Num
	den := f.Den * k.Den
	g := gcd(num, den)
	return Fraction{num / g, den / g}
}

func (f Fraction) square() Fraction {
	return Fraction{f.Num * f.Num, f.Den * f.Den}
}

func (f Fraction) sqrt() Fraction {
	n := int64(1)
	for i := int64(1); i*i <= f.Num; i++ {
		if i*i == f.Num {
			n = i
		}
	}
	d := int64(1)
	for i := int64(1); i*i <= f.Den; i++ {
		if i*i == f.Den {
			d = i
		}
	}
	return Fraction{n, d}.simp()
}

func (f Fraction) cmp(k Fraction) int {
	fn := f.Num * k.Den
	kn := f.Den * k.Num
	if fn > kn {
		return 1
	}
	if fn < kn {
		return -1
	}
	return 0
}

func gcd(a, b int64) int64 {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}
