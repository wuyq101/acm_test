package main

import (
	"fmt"
	"math"
)

/*

https://projecteuler.net/problem=196

第n行，有n个元素

最大值N = 1+2+3+...+n = n(n+1)/2

最小值M = N-n+1 = n(n+1)/2 - n +1 = n(n-1)/2 + 1

n = 4
M = 7, N = 10


(i,j)的8个邻居：
A(i,j) := i*(i-1)/2 + 1 + (j-1) = i*(i-1)/2 + j
A(i,j-1) = i*(i-1)/2 + j-1
A(i,j+1) = i*(i-1)/2 + j+1
上一行
A(i-1,j) = (i-1)*(i-2)/2 + j
A(i-1,j-1) = (i-1)*(i-2)/2 + j-1
A(i-1,j+1) = (i-1)*(i-2)/2 + j+1
下一行
A(i+1,j) = (i+1)*(i)/2 + j
A(i+1,j-1) = (i+1)*(i)/2 + j-1
A(i+1,j+1) = (i+1)*(i)/2 + j+1

如果A(i,j)是质数，并且8个邻居中，至少有2个质数，构成一组Prime Triplets

A(i,j)-A(i-1,j) = i-1
A(i+1)-A(i,j) = i
所以A(i,j)与邻居的差:{
	-i,    -i+1,   -i+2
	-1,      X,     +1
	i-1,     i,     i+1
}

两个质数之间的差都是偶数
因此当i是偶数的时候
A(i,j), 只有左上 -i，右上-i+2，正下 i ，三个可能是质数

当i是奇数的时候，只有正上 -i+1，左下 i-1，右下i+1，三个可能是质数



*/

func main() {
	N := int64(7208785)
	Max := N * (N + 1) / 2
	M := int64(math.Sqrt(float64(Max)))
	fmt.Println("Max:", Max)
	fmt.Println("M:", M)
	composites := make([]bool, M+1)
	for i := int64(2); i <= M; i++ {
		if composites[i] {
			continue
		}
		for j := i + i; j <= M; j += i {
			composites[j] = true
		}
	}
	primes = make([]int64, 0)
	for i := int64(2); i <= M; i++ {
		if !composites[i] {
			primes = append(primes, i)
		}
	}
	fmt.Printf("len=%d\n", len(primes))

	//fmt.Printf("S(8)=%d\n", S(8))
	//fmt.Printf("S(9)=%d\n", S(9))

	//fmt.Printf("S(10000)=%d\n", S(10000))
	//return

	a := S(N)
	b := S(5678027)
	fmt.Printf("sum=%d\n", a+b)
}

var primes []int64

func S(n int64) int64 {
	// n-2
	// n-1
	// n 行
	// n+1
	// n+2
	// 需要考察前后各两行
	nums := make([][]bool, 5)
	for i := -2; i <= 2; i++ {
		nums[i+2] = s(n + int64(i))
	}
	L := make([]int64, 5)
	for i := -2; i <= 2; i++ {
		v := n + int64(i)
		L[i+2] = v*(v-1)/2 + 1
	}
	m := make(map[[3]int64]bool)
	var dfs func(Point, []Point)

	dfs = func(p Point, list []Point) {
		tmp := make([]Point, 0)
		tmp = append(tmp, list...)
		tmp = append(tmp, p)
		if len(tmp) == 3 {
			// find a triplets
			v := [3]int64{tmp[0].v, tmp[1].v, tmp[2].v}
			sort(v)
			m[v] = true
			return
		}
		// (i-1,j-1), (i-1,j), (i-1,j+1)
		// (i,j-1), (i,j), (i,j+1)
		// (i+1,j-1),(i+1,j),(i+1,j+1)
		for x := -1; x <= 1; x++ {
			for y := -1; y <= 1; y++ {
				if x == 0 && y == 0 {
					continue
				}
				r := p.i + x
				c := p.j + y
				if r >= 0 && r < 5 && c >= 0 && c < len(nums[r]) && nums[r][c] {
					// 找到一个邻居是质数
					if !contains(tmp, r, c) {
						q := Point{
							i: r,
							j: c,
							v: L[r] + int64(c),
						}
						dfs(q, tmp)
					}

				}
			}
		}

	}

	for i := 0; i < 5; i++ {
		for j := 0; j < len(nums[i]); j++ {
			if nums[i][j] {
				p := Point{
					i: i,
					j: j,
					v: L[i] + int64(j),
				}
				dfs(p, nil)
			}
		}
	}
	fmt.Printf("find %d triplets\n", len(m))

	// 考察第n行
	left := L[2]
	right := L[3] - 1
	cnt := make(map[int64]bool)
	total := int64(0)
	for v := range m {
		for _, p := range v {
			if p >= left && p <= right {
				if !cnt[p] {
					cnt[p] = true
					total += p
				}
			}
		}
	}
	return total
}

func contains(list []Point, r, c int) bool {
	for _, p := range list {
		if p.i == r && p.j == c {
			return true
		}
	}
	return false
}

func sort(list [3]int64) [3]int64 {
	if list[0] > list[1] {
		list[0], list[1] = list[1], list[0]
	}
	if list[0] > list[2] {
		list[0], list[2] = list[2], list[0]
	}
	if list[1] > list[2] {
		list[1], list[2] = list[2], list[1]
	}
	return list
}

type Point struct {
	i int
	j int
	v int64
}

func s(n int64) []bool {
	// 第n行
	nums := make([]bool, n)
	for i := int64(0); i < n; i++ {
		nums[i] = true
	}
	R := n * (n + 1) / 2
	L := R - n + 1
	cnt := 0
	for _, p := range primes {
		if p*p >= R {
			break
		}
		r := L % p
		k := L
		if r > 0 {
			k = L + (p - r)
		}
		//	fmt.Printf("p=%d k=%d\n", p, k)
		for i := k; i <= R; i += p {
			nums[i-L] = false
		}
	}
	for i := 0; i < len(nums); i++ {
		if nums[i] {
			cnt++
		}
	}
	fmt.Printf("%d [%d-%d] --> %d\n", n, L, R, cnt)
	return nums
}
