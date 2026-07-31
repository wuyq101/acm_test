package main

import "fmt"

func main() {
	n := 9
	d := 4
	p := gen(n, d)
	fmt.Println(p)
	n = 6
	d = 6
	c := gen(n, d)
	fmt.Println(c)
	s := int64(0)
	for i := 0; i < len(p); i++ {
		for j := 0; j < i; j++ {
			s += int64(p[i] * c[j])
		}
	}
	fmt.Printf("sum=%d\n", sum)
	T := sum(p) * sum(c)
	fmt.Printf("Total=%d\n", T)
	fmt.Printf("%.7f\n", float64(s)/float64(T))
}

func sum(p []int) int {
	s := 0
	for _, v := range p {
		s += v
	}
	return s
}

func gen(n, d int) []int {
	p := make([][]int, n+1)
	for i := 0; i <= n; i++ {
		p[i] = make([]int, n*d+1)
	}
	p[0][0] = 1
	M := n * d
	for i := 1; i <= n; i++ {
		for j := 1; j <= M; j++ {
			for k := 1; k <= d; k++ {
				if j-k >= 0 && p[i-1][j-k] > 0 {
					p[i][j] += p[i-1][j-k]
				}
			}
		}
	}
	return p[n]
}
