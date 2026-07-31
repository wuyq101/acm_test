package main

import "fmt"

/*
根据电话流水，问经过多少步，可以让目标所在的集合人数超过99%
Disjoint Set Union
并查集


*/

func main() {
	Target := 524287
	M := 1000000
	N := 55
	S := make([]int, N)
	for i := 1; i <= 55; i++ {
		S[i-1] = (100003 - 200003*i + 300007*i*i*i) % M
	}
	idx := 0
	next := func() int {
		x := S[idx]
		S[idx] = (S[idx] + S[(idx+31)%N]) % M
		idx = (idx + 1) % N
		return x
	}

	cnt := 0
	d := NewDSU(M)
	for {
		x, y := next(), next()
		if x == y {
			continue
		}
		cnt++
		d.union(x, y)
		v := d.getSize(Target)
		if v >= 990000 {
			break
		}
	}

	fmt.Printf("cnt=%d\n", cnt)
}

type DSU struct {
	parent []int
	size   []int
}

func NewDSU(n int) *DSU {
	p := make([]int, n)
	size := make([]int, n)
	for i := 0; i < n; i++ {
		p[i] = i
		size[i] = 1
	}
	return &DSU{p, size}
}

// 查找，路径压缩
func (d *DSU) find(x int) int {
	if d.parent[x] != x {
		d.parent[x] = d.find(d.parent[x])
	}
	return d.parent[x]
}

func (d *DSU) union(x, y int) {
	rx := d.find(x)
	ry := d.find(y)
	if rx == ry {
		return
	}
	if d.size[rx] < d.size[ry] {
		d.parent[rx] = ry
		d.size[ry] += d.size[rx]
	} else {
		d.parent[ry] = rx
		d.size[rx] += d.size[ry]
	}
}

func (d *DSU) getSize(x int) int {
	return d.size[d.find(x)]
}
