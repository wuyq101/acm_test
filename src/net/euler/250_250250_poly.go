package main

import (
	"fmt"
	"math/bits"
)

func main() {
	R := make([]int, 250)
	for i := int64(1); i <= 250250; i++ {
		if i%m == 0 {
			R[0]++
			continue
		}
		r := pow(i, i, m)
		R[r]++
	}
	var P Poly
	P[0] = 1
	for i := 1; i < 250; i++ {
		if R[i] == 0 {
			continue
		}
		// k = R[i]
		// (1+x^i)^k
		var p Poly
		p[0] = 1
		p[i] = 1
		p = ppow(p, R[i])
		P = mulPoly(P, p)
	}
	p2 := pow(2, int64(R[0]), M)
	s := mulMod(p2, P[0], M)
	s -= 1
	fmt.Printf("%d\n", s)
}

func ppow(p Poly, k int) Poly {
	if k == 0 {
		var c Poly
		c[0] = 1
		return c
	}
	if k == 1 {
		return p
	}
	if k&1 == 0 {
		v := ppow(p, k/2)
		return mulPoly(v, v)
	}
	return mulPoly(p, ppow(p, k-1))
}

func mulPoly(a, b Poly) Poly {
	var c Poly
	for i := int64(0); i < m; i++ {
		for j := int64(0); j < m; j++ {
			k := (i + j) % m
			c[k] = addMod(c[k], mulMod(a[i], b[j], M), M)
		}
	}
	return c
}

func addMod(a, b, c int64) int64 {
	return int64((uint64(a) + uint64(b)) % uint64(c))
}

type Poly [250]int64

var m = int64(250)
var M = int64(1e16)

func pow(a, p, m int64) int64 {
	if p == 0 {
		return 1
	}
	if p&1 == 0 {
		v := pow(a, p/2, m)
		return mulMod(v, v, m)
	}
	return mulMod(a, pow(a, p-1, m), m)
}

func mulMod(a, b, m int64) int64 {
	hi, lo := bits.Mul64(uint64(a), uint64(b))
	return int64(bits.Rem64(hi, lo, uint64(m)))
}
