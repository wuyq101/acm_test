package main

import (
	"fmt"
	"sort"
)

/*
http://projecteuler.net/problem=143

费马点，托里拆利特三角形
cos(120) = -1/2
根据余弦定理

BC^2 = a^2 = q^2 + qr + r^2

CA^2 = b^2 = p^2 + pr + r^2

AB^2 = c^2 = p^2 + pq + q^2

p,q,r都是正整数，且上面三个和都是平方数


x^2 + xy + y^2 = z^2
x = j^2 - k^2,
y = j(2k-j),
z = j^2 - jk + k^2.
通过枚举j,k可以得到符合要求的x,y,z，类似勾股数的枚举方式
x^2 = (j^2 - k^2)^2 = j^4 - 2*j^2*k^2 + k^4
xy = (j^2-k^2)*j*(2k-j) = (j^2-k^2)*(2jk-j^2) = 2*j^3*k - j^4 - 2*j*k^3 - j^2*k^2
y^2 = j^2*(4k^2 - 4*j*k + j^2) = 4*j^2*k^2 - 4*j^3*k + j^4

x^2 + xy + y^2 = j^4 - 2*j^2*k^2 + k^4 + 2*j^3*k - j^4 - 2*j*k^3 - j^2*k^2 + 4*j^2*k^2 - 4*j^3*k + j^4
               = j^4 + -2*j^3*k + 3*j^2*k^2 + -2*j*k^3 + k^4
	       = (j^2 -jk + k^2)^2 = z^2

	       j从1 ---> M,k从j-1 ----> M
	       2k-j>0 k>j/2 k (j/2, j-1)


*/

func main() {
	// 枚举"勾股"数对
	M := 120000

	gs := make(map[int][]Gougu)

	for j := 1; j < M; j++ {
		for k := j/2 + 1; k < j; k++ {
			if gcd(k, j) != 1 {
				continue
			}
			x := j*j - k*k
			y := j * (2*k - j)
			if x+y > M {
				break
			}

			z := j*j - j*k + k*k
			// 得到一组数 x^2 + xy + y^2 = z^2
			if x > y {
				x, y = y, x
			}
			g := Gougu{x, y, z}
			gs[x] = append(gs[x], g)
			gs[y] = append(gs[y], g)
			a, b, c := x+x, y+y, z+z
			for a+b < M {
				tmp := Gougu{a, b, c}
				gs[a] = append(gs[a], tmp)
				gs[b] = append(gs[b], tmp)
				a, b, c = a+x, b+y, c+z
			}
		}
	}
	keys := make([]int, 0, len(gs))
	for k := range gs {
		keys = append(keys, k)
	}
	sort.Ints(keys)
	sums := make(map[int]bool)
	sum := 0
	for p, list := range gs {
		// list := gs[p]
		for _, g := range list {
			// 这里的g(x,y,z) 看作 p,q,c
			q := g.x
			if q == p {
				q = g.y
			}
			// 已经知道q, 根据q, 找到 q, r, a三元组，
			listq := gs[q]
			if len(listq) == 0 {
				continue
			}
			for _, gq := range listq {
				r := gq.x
				if r == q {
					r = gq.y
				}
				// 现在已经找到了p,q,r, 需要验证 p,r,b是否也存在
				listr := gs[r]
				if len(listr) == 0 {
					continue
				}
				for _, gr := range listr {
					// 需要找到 p,r,b
					pp := gr.x
					if pp == r {
						pp = gr.y
					}
					if pp == p {
						// 找到一组
						//fmt.Printf("p,q,r = %d,%d,%d\n", p, q, r)
						s := p + q + r
						if s < M && !sums[s] {
							sums[s] = true
							sum += s
						}
					}
				}
			}

		}
	}

	fmt.Printf("sum=%d\n", sum)

}

type Gougu struct {
	x, y, z int
}

func gcd(a, b int) int {
	if b == 0 {
		return a
	}
	return gcd(b, a%b)
}

// 直接暴力枚举
/*
func main() {
	M := 120000
	m := make(map[int]int)
	for i := 1; i <= M; i++ {
		m[i*i] = i
	}
	flag := make(map[int]bool)
	sum := 0

	for p := 1; p < M; p++ {
		for q := 1; q < M; q++ {
			if p+q >= M {
				break
			}
			c := p*p + p*q + q*q
			if m[c] == 0 {
				continue
			}
			c = m[c]

			for r := 1; r < M; r++ {
				if p+q+r >= M {
					break
				}
				a := q*q + q*r + r*r
				if m[a] == 0 {
					continue
				}
				a = m[a]

				b := p*p + p*r + r*r
				if m[b] == 0 {
					continue
				}
				b = m[b]
				f := p + q + r
				if !flag[f] {
					fmt.Printf("p=%d q=%d r=%d a=%d b=%d c=%d\n", p, q, r, a, b, c)
					flag[f] = true
					sum += f
				}
			}
		}
	}

	fmt.Printf("sum=%d\n", sum)
}
*/
