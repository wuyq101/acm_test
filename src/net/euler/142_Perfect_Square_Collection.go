package main

import (
	"fmt"
	"sort"
)

/*
http://projecteuler.net/problem=142
x+y+z
x>y>z

x+y=a^2
x-y=b^2

x+z=c^2
x-z=d^2

y+z=e^2
y-z=f^2

设p = x+z = c^2
  q = x-z = d^2

  x = (p+q)/2
  z = (p-q)/2

  2x = (x+y)+(x-y) = a^2+b^2 = p+q

  p+q = a^2 + b^2   (1)

  2y = x+y - (x-y) = a^2-b^2  (2)
  2y = y+z + y-z = e^2+f^2    (3)

  2z = y+z - (y-z) = e^2-f^2 = p-q (4)

  联立2，3得:
  a^2 - b^2 = e^2 + f^2  (5)

  整理可得
  p+q = a^2 + b^2
  p-q = e^2 - f^2
  a^2 - b^2 = e^2 + f^2

  p，q本身也是平方数，因此问题变成寻找平方数，
  x + y = a^2 --> y = a^2 - x
  y + z = e^2
  a^2 - x + z = e^2
  又因为 x-z = q = d^2
  a^2 - d^2 = e^2

  a^2 = d^2 + e^2 (6)   (d,e,a)是一组勾股数

  x-y = b^2 --> y = x - b^2
  y+z = e^2
  x - b^2 + z = e^2
  c^2 - b^2 = e^2
  c^2 = b^2 + e^2 (7) (b,e,c)是一组勾股数
  同时 c^2 + d^2 = a^2 + b^2

  枚举勾股数, (b,e,c) (d,e,a), 当出现相同数字，当作e
  然后的到b,c,d,a, 检查c^2 + d^2 = a^2 + b^2，是否满足，如果满足
  则 p = √c q=√d, 计算 x,z, y =√a -x
  然后检查e，f这些是否都是平方数


  勾股数的枚举方法
  a = m^2 - n^2
  b = 2mn
  c = m^2 + n^2
  可得 a^2 + b^2 = c^2
  枚举m从1到1000，n从1到m-1，得到勾股数
  gcd(m,n) = 1, 并且m,n一奇一偶


  x=434657,y=420968,z=150568
  x+y = 925^2 a
  x-y = 117^2 b
  x+z = 765^2 c
  x-z = 533^2 d
  y+z = 756^2 e
  y-z = 520^2 f
*/

func main() {
	//	b := check(Gougu{115, 252, 277}, Gougu{252, 275, 373}, 252)
	//	fmt.Printf("b = %v\n", b)
	//	return

	gs := make(map[int][]Gougu)
	for m := 1; m < 1000; m++ {
		for n := m - 1; n > 0; n-- {
			if gcd(n, m) > 1 {
				continue
			}
			if (m%2 + n%2) != 1 {
				continue
			}
			a := m*m - n*n
			b := 2 * m * n
			c := m*m + n*n
			if a > b {
				a, b = b, a
			}
			tmp := Gougu{a, b, c}
			gs[a] = append(gs[a], tmp)
			gs[b] = append(gs[b], tmp)
			k := 2
			for {
				if a*k > 1000 {
					break
				}
				tmp := Gougu{a * k, b * k, c * k}
				key := a * k
				gs[key] = append(gs[key], tmp)
				key = b * k
				gs[key] = append(gs[key], tmp)
				k++
			}

		}
	}

	/*
		gp := gs[756]
		for _, v := range gp {
			fmt.Printf("%d %d %d\n", v.a, v.b, v.c)
		}
		return
	*/

	list := make([]int, 0, len(gs))
	for k, _ := range gs {
		list = append(list, k)
	}
	sort.Ints(list)
	for _, v := range list {
		gp := gs[v]
		if len(gp) == 1 {
			continue
		}
		//	fmt.Printf("len(%d) = %d\n", v, len(gp))
		for i := 0; i < len(gp); i++ {
			for j := i + 1; j < len(gp); j++ {
				if check(gp[i], gp[j], v) || check(gp[j], gp[i], v) {
					fmt.Printf("%d %d %d\n", gp[i].a, gp[i].b, gp[i].c)
					fmt.Printf("%d %d %d\n", gp[j].a, gp[j].b, gp[j].c)
					return
				}
			}
		}
	}

}

func check(A, B Gougu, e int) bool {
	// a>b c>d e>f

	// A (b, e, c)
	// B (d, e, a)

	b := A.a
	if b == e {
		b = A.b
	}
	c := A.c

	d := B.a
	if d == e {
		d = B.b
	}
	a := B.c

	if a < b || c < d {
		return false
	}

	// c^2 + d^2 = a^2 + b^2
	if a*a+b*b != c*c+d*d {
		return false
	}

	p := c * c
	q := d * d
	x := (p + q) / 2
	z := (p - q) / 2
	y := a*a - x
	if y < 0 {
		return false
	}
	// x+y checked
	// x-y = b^2
	if b*b != x-y {
		return false
	}
	if e*e != y+z {
		return false
	}
	// y-z = f^2
	ff := y - z
	if !isSquare(ff) {
		return false
	}

	fmt.Printf("find x,y,z = %d,%d,%d, sum=%d\n", x, y, z, x+y+z)

	return true

}

func isSquare(n int) bool {
	for i := 1; i*i <= n; i++ {
		if i*i == n {
			return true
		}
	}
	return false
}

func gcd(n, m int) int {
	if n == 0 {
		return m
	}
	if m == 0 {
		return n
	}
	return gcd(m, n%m)
}

type Gougu struct {
	a int
	b int
	c int
}
